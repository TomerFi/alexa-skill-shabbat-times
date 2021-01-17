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
package info.tomfi.alexa.skills.shabbattimes.api.response.items;

import static info.tomfi.alexa.skills.shabbattimes.assertions.Assertions.assertThat;

import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public final class ResponseLocationTest {
  @Test
  @DisplayName("test response location json to object")
  public void jsonToObject_location_success() throws IOException, URISyntaxException {
    try (final BufferedReader breader =
        Files.newBufferedReader(
            Paths.get(
                Thread.currentThread()
                    .getContextClassLoader()
                    .getResource("api-responses/response_location.json")
                    .toURI()))) {
                      final ResponseLocation location = new GsonBuilder().create().fromJson(breader, ResponseLocation.class);

                      assertThat(location)
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
                    }
  }
}
