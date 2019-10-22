/**
 * Copyright 2019 Tomer Figenblat
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package info.tomfi.alexa.skills.shabbattimes.api;

import static java.util.stream.Collectors.joining;

import static info.tomfi.alexa.skills.shabbattimes.assertions.Assertions.assertThat;
import static info.tomfi.alexa.skills.shabbattimes.assertions.Assertions.assertThatExceptionOfType;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.mockserver.client.MockServerClient;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;
import org.mockserver.model.JsonBody;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import info.tomfi.alexa.skills.shabbattimes.di.DiLocalAPIConfiguration;

import lombok.Cleanup;
import lombok.val;

public final class ApiRequestMakerTest
{
    private ApiRequestMaker requestMaker;

    @BeforeEach
    public void initialize()
    {
        @Cleanup val context = new AnnotationConfigApplicationContext(DiLocalAPIConfiguration.class);
        requestMaker = context.getBean(ApiRequestMaker.class);
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
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> requestMaker.setGeoId(0));
    }

    @Test
    @DisplayName("test exception thrown when trying to send request without setting the geo id")
    public void sendRequest_withoutGeoid_throwsExcpetion() {
        assertThatExceptionOfType(IllegalStateException.class).isThrownBy(() -> requestMaker.send());
    }

    @Test
    @DisplayName("test an api response with a correctly build request using a mock server and client")
    public void sendRequest_buildCorrectly_success() throws IllegalStateException, IOException, URISyntaxException
    {
        requestMaker.setHavdalahMinutesAfterSundown(20);
        requestMaker.setCandleLightingMinutesBeforeSunset(10);
        requestMaker.setGeoId(1);
        requestMaker.setSpecificDate(LocalDate.now());

        @Cleanup val lines =
            Files.lines(Paths.get(Thread.currentThread().getContextClassLoader().getResource("api-responses/response_full.json").toURI())
        );
        val responesText = lines.collect(joining(System.lineSeparator()));

        val mockServer = ClientAndServer.startClientAndServer(1234);
        @Cleanup val mockClient = new MockServerClient("localhost", mockServer.getLocalPort());

        mockClient
        .when(
            HttpRequest.request()
                .withMethod("GET")
                .withPath("/shabbat")
        )
        .respond(
            HttpResponse.response()
                .withStatusCode(200)
                .withBody(JsonBody.json(responesText))
        );

        val response = requestMaker.send();

        assertThat(response)
            .titleIs("testTitle")
            .dateIs("testDate")
            .linkIs("testLink");

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

        for (val item : response.getItems())
        {
            assertThat(item)
                .titleIs("testTitle")
                .categoryIs("testCategory")
                .dateIs("testDate")
                .hebrewIs("testHebrew");
        }

        mockServer.stop();
    }
}
