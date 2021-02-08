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

import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/** Secondary dependency context configuration class, used for creating Country beans. */
@Configuration
public class CountryBeans {
  private final LoaderService loader;

  public CountryBeans(final LoaderService setLoader) {
    loader = setLoader;
  }

  /**
   * Build a Country object representing Israel, IL.
   *
   * @return the Country instance.
   */
  @Bean("IL")
  Country getIsrael() {
    return Country.builder()
        .abbreviation("IL")
        .name("Israel")
        .bundleKey(NOT_FOUND_IN_ISRAEL)
        .utterances(List.of("israel"))
        .cities(loader.loadCities("IL"))
        .build();
  }

  /**
   * Build a Country object representing the United States, US.
   *
   * @return the Country instance.
   */
  @Bean("US")
  Country getUnitedStates() {
    return Country.builder()
        .abbreviation("US")
        .name("the United States")
        .bundleKey(NOT_FOUND_IN_US)
        .utterances(List.of("united states"))
        .cities(loader.loadCities("US"))
        .build();
  }

  /**
   * Build a Country object representing Great Britain, GB.
   *
   * @return the Country instance.
   */
  @Bean("GB")
  Country getGreatBritain() {
    return Country.builder()
        .abbreviation("GB")
        .name("the United Kingdom")
        .bundleKey(NOT_FOUND_IN_UK)
        .utterances(List.of("united kingdom", "great britain", "britain", "england"))
        .cities(loader.loadCities("GB"))
        .build();
  }
}
