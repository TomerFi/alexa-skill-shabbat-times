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
package info.tomfi.alexa.shabbattimes;

import static info.tomfi.alexa.shabbattimes.BundleKey.NOT_FOUND_IN_ISRAEL;
import static info.tomfi.alexa.shabbattimes.BundleKey.NOT_FOUND_IN_UK;
import static info.tomfi.alexa.shabbattimes.BundleKey.NOT_FOUND_IN_US;
import static info.tomfi.alexa.shabbattimes.assertions.BDDAssertions.then;
import static org.mockito.BDDMockito.given;

import java.util.List;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@Tag("unit-tests")
final class CountryBeansTest {
  @Mock private LoaderService loader;
  @InjectMocks private CountryBeans sut;

  @Test
  void verify_instantiation_of_israel(@Mock final City city1, @Mock final City city2) {
    // stub loader with mock cities for IL abbreviation
    given(loader.loadCities("IL")).willReturn(List.of(city1, city2));
    // verify returning country
    then(sut.getIsrael())
        .abbreviationIs("IL")
        .nameIs("Israel")
        .bundleKeyIs(NOT_FOUND_IN_ISRAEL)
        .utterancesAre(List.of("israel"))
        .citiesAre(List.of(city1, city2));
  }

  void verify_instantiation_of_the_united_states(@Mock final City city1, @Mock final City city2) {
    // stub loader with mock cities for IL abbreviation
    given(loader.loadCities("US")).willReturn(List.of(city1, city2));
    // verify returning country
    then(sut.getUnitedStates())
        .abbreviationIs("US")
        .nameIs("the United States")
        .bundleKeyIs(NOT_FOUND_IN_US)
        .utterancesAre(List.of("united states"))
        .citiesAre(List.of(city1, city2));
  }

  void verify_instantiation_of_great_britain(@Mock final City city1, @Mock final City city2) {
    // stub loader with mock cities for IL abbreviation
    given(loader.loadCities("GB")).willReturn(List.of(city1, city2));
    // verify returning country
    then(sut.getGreatBritain())
        .abbreviationIs("GB")
        .nameIs("the United Kingdom")
        .bundleKeyIs(NOT_FOUND_IN_UK)
        .utterancesAre(List.of("united kingdom", "great britain", "britain", "england"))
        .citiesAre(List.of(city1, city2));
  }
}
