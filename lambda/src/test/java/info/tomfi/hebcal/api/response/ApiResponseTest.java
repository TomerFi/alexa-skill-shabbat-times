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
package info.tomfi.hebcal.api.response;

import static info.tomfi.shabbattimes.skill.assertions.Assertions.assertThat;

import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public final class ApiResponseTest {
  @Test
  @DisplayName("test the api json response with minimal values")
  public void responseJson_minimalValues_success() throws IOException, URISyntaxException {
    try (final BufferedReader breader =
        Files.newBufferedReader(
            Paths.get(
                Thread.currentThread()
                    .getContextClassLoader()
                    .getResource("api-responses/response_minimal.json")
                    .toURI())))
  {
    final ApiResponse response = new GsonBuilder().create().fromJson(breader, ApiResponse.class);

    assertThat(response).titleIs("testTitle").dateIs("testDate").linkIs("testLink");
  }
  }
}
