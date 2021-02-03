/**
 * Copyright Tomer Figenblat.
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
package info.tomfi.alexa.shabbattimes.services;

import static info.tomfi.alexa.shabbattimes.assertions.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.assertThatExceptionOfType;
import static org.assertj.core.api.BDDAssertions.then;

import info.tomfi.alexa.shabbattimes.exceptions.NoJsonFileException;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/** Loader service provider test cases. */
@Tag("unit-tests")
final class LoaderServiceImplTest {
  private LoaderServiceImpl loader;

  @BeforeEach
  void initialize() {
    loader = new LoaderServiceImpl();
  }

  @Test
  void test_deserialization_of_json_to_city_list_by_loader_service() {
    // load cities from predefined cities/TST_Cities.json file
    var cities = loader.loadCities("TST");
    // then city list should contain 2 cities
    then(cities).hasSize(2);
    // the first city should be
    then(cities.get(0))
        .aliasesAre(List.of("city1", "firstcity"))
        .cityNameIs("testCity1")
        .countryAbbreviationIs("TST")
        .geoIdIs(1234567)
        .geoNameIs("TST-testCity1");
    // the second city should be
    then(cities.get(1))
        .aliasesAre(List.of("city2", "secondcity"))
        .cityNameIs("testCity2")
        .countryAbbreviationIs("TST")
        .geoIdIs(7654321)
        .geoNameIs("TST-testCity2");
  }

  @Test
  void trying_to_load_a_non_existing_file_throws_no_json_file_exception() {
    assertThatExceptionOfType(NoJsonFileException.class).isThrownBy(() -> loader.loadCities(null));
  }
}
