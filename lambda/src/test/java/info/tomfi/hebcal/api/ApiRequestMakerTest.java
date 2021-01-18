/**
 * Copyright Tomer Figenblat
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package info.tomfi.hebcal.api;

import static info.tomfi.shabbattimes.skill.assertions.Assertions.assertThat;
import static info.tomfi.shabbattimes.skill.assertions.Assertions.assertThatExceptionOfType;
import static java.util.stream.Collectors.joining;

import info.tomfi.hebcal.api.response.ApiResponse;
import info.tomfi.hebcal.api.response.items.ResponseItem;
import info.tomfi.shabbattimes.skill.di.DiLocalApiConfiguration;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.stream.Stream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockserver.client.MockServerClient;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;
import org.mockserver.model.JsonBody;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public final class ApiRequestMakerTest {
  private AnnotationConfigApplicationContext context;
  private ApiRequestMaker requestMaker;

  @BeforeEach
  public void initialize() {
    context = new AnnotationConfigApplicationContext(DiLocalApiConfiguration.class);
    requestMaker = context.getBean(ApiRequestMaker.class);
  }

  @AfterEach
  public void cleanup() {
    context.close();
  }

  @Test
  @DisplayName("test exception thrown by setting illegal havdalah minutes")
  public void setHavdalahMinutes_illegalArgument_throwsExcpetion() {
    assertThatExceptionOfType(IllegalArgumentException.class)
        .isThrownBy(() -> requestMaker.setHavdalahMinutesAfterSundown(0));
  }

  @Test
  @DisplayName("test exception thrown by setting illegal candle lighting minutes")
  public void setCandleLightingMinutes_illegalArgument_throwsExcpetion() {
    assertThatExceptionOfType(IllegalArgumentException.class)
        .isThrownBy(() -> requestMaker.setCandleLightingMinutesBeforeSunset(-1));
  }

  @Test
  @DisplayName("test exception thrown by setting illegal geoid")
  public void setGeoId_illegalArgument_throwsExcpetion() {
    assertThatExceptionOfType(IllegalArgumentException.class)
        .isThrownBy(() -> requestMaker.setGeoId(0));
  }

  @Test
  @DisplayName("test exception thrown when trying to send request without setting the geo id")
  public void sendRequest_withoutGeoid_throwsExcpetion() {
    assertThatExceptionOfType(IllegalStateException.class).isThrownBy(() -> requestMaker.send());
  }

  @Test
  @DisplayName("test an api response with a correctly build request using a mock server and client")
  public void sendRequest_buildCorrectly_success()
      throws IllegalStateException, IOException, URISyntaxException {
    requestMaker.setHavdalahMinutesAfterSundown(20);
    requestMaker.setCandleLightingMinutesBeforeSunset(10);
    requestMaker.setGeoId(1);
    requestMaker.setSpecificDate(LocalDate.now());

    try (final Stream<String> lines =
        Files.lines(
            Paths.get(
                Thread.currentThread()
                    .getContextClassLoader()
                    .getResource("api-responses/response_full.json")
                    .toURI()))
    ) {
      final String responesText = lines.collect(joining(System.lineSeparator()));

      final ClientAndServer mockServer = ClientAndServer.startClientAndServer(1234);
      final MockServerClient mockClient = new MockServerClient("localhost", mockServer.getLocalPort());

      mockClient
          .when(HttpRequest.request().withMethod("GET").withPath("/shabbat"))
          .respond(HttpResponse.response().withStatusCode(200).withBody(JsonBody.json(responesText)));

      final ApiResponse response = requestMaker.send();

      assertThat(response).titleIs("testTitle").dateIs("testDate").linkIs("testLink");

      assertThat(response.getLocation())
          .latitudeIs(25.25)
          .longitudeIs(26.26)
          .geonameidIs(2526)
          .cityIs("testCity")
          .asciinameIs("testAsciiname")
          .titleIs("testTitle")
          .tzidIs("testTzid")
          .admin1Is("testAdmin1")
          .countryIs("testCountry")
          .geoIs("testGeo");

      for (final ResponseItem item : response.getItems()) {
        assertThat(item)
            .titleIs("testTitle")
            .categoryIs("testCategory")
            .dateIs("testDate")
            .hebrewIs("testHebrew");
      }

      mockClient.close();
      mockServer.stop();
    }
  }
}
