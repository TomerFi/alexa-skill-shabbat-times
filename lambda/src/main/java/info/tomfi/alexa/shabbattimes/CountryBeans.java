/**
 * Copyright Tomer Figenblat. Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License
 * at http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in
 * writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific
 * language governing permissions and limitations under the License.
 */
package info.tomfi.alexa.shabbattimes;

import static info.tomfi.alexa.shabbattimes.internal.SkillTools.getCityListFromJsonFile;

import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CountryBeans {
  public CountryBeans() {
    //
  }

  @Bean("IL")
  public Country getIsrael() {
    return Country.builder()
        .abbreviation("IL")
        .name("Israel")
        .bundleKey(BundleKey.NOT_FOUND_IN_ISRAEL)
        .utterances(List.of("israel"))
        .cities(getCityListFromJsonFile("IL"))
        .build();
  }

  @Bean("US")
  public Country getUnitedStates() {
    return Country.builder()
        .abbreviation("US")
        .name("the United States")
        .bundleKey(BundleKey.NOT_FOUND_IN_US)
        .utterances(List.of("united states"))
        .cities(getCityListFromJsonFile("US"))
        .build();
  }

  @Bean("GB")
  public Country getGreatBritain() {
    return Country.builder()
        .abbreviation("GB")
        .name("the United Kingdom")
        .bundleKey(BundleKey.NOT_FOUND_IN_UK)
        .utterances(List.of("united kingdom", "great britain", "britain", "england"))
        .cities(getCityListFromJsonFile("GB"))
        .build();
  }
}
