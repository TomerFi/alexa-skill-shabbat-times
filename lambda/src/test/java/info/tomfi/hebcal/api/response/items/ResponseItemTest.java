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
package info.tomfi.hebcal.api.response.items;

import static info.tomfi.shabbattimes.skill.assertions.Assertions.assertThat;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public final class ResponseItemTest {
  private static Gson gson;
  private static ClassLoader loader;

  @BeforeAll
  public static void initialize() {
    gson = new GsonBuilder().create();
    loader = Thread.currentThread().getContextClassLoader();
  }

  @Test
  @DisplayName("test response item json to object with mandatory values only")
  public void itemJsonToObject_mandatoryKeys_success() throws IOException, URISyntaxException {
    try (final BufferedReader breader =
        Files.newBufferedReader(
            Paths.get(loader.getResource("api-responses/response_item_mandatory.json").toURI())))
            {
              final ResponseItem item = gson.fromJson(breader, ResponseItem.class);

              assertThat(item)
                  .titleIs("testTitle")
                  .categoryIs("testCategory")
                  .dateIs("testDate")
                  .hebrewIs("testHebrew")
                  .linkIsEmpty()
                  .memoIsEmpty()
                  .subcatIsEmpty()
                  .isNotYomtov();
            }
  }

  @Test
  @DisplayName("test response item json to object with mandatory and optional values")
  public void itemJsonToObject_optionalKeys_success() throws IOException, URISyntaxException {
    try (final BufferedReader breader =
        Files.newBufferedReader(
            Paths.get(loader.getResource("api-responses/response_item_optional.json").toURI())))
            {
              final ResponseItem item = gson.fromJson(breader, ResponseItem.class);

              assertThat(item)
                  .titleIs("testTitle")
                  .categoryIs("testCategory")
                  .dateIs("testDate")
                  .hebrewIs("testHebrew")
                  .linkIs("testLink")
                  .memoIs("testMemo")
                  .subcatIs("testSubcat")
                  .isNotYomtov();
            }
  }

  @Test
  @DisplayName("test response item json to object with mandatory, optional and yomtov values")
  public void itemJsonToObject_fullKeys_success() throws IOException, URISyntaxException {
    try (final BufferedReader breader =
        Files.newBufferedReader(
            Paths.get(loader.getResource("api-responses/response_item_full.json").toURI()))) {
              final ResponseItem item = gson.fromJson(breader, ResponseItem.class);

              assertThat(item)
                  .titleIs("testTitle")
                  .categoryIs("testCategory")
                  .dateIs("testDate")
                  .hebrewIs("testHebrew")
                  .linkIs("testLink")
                  .memoIs("testMemo")
                  .subcatIs("testSubcat")
                  .isYomtov();
            }
  }
}
