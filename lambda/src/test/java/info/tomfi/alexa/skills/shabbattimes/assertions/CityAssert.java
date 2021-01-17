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
package info.tomfi.alexa.skills.shabbattimes.assertions;

import info.tomfi.alexa.skills.shabbattimes.city.City;
import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;

public final class CityAssert extends AbstractAssert<CityAssert, City> {
  protected CityAssert(final City actual) {
    super(actual, CityAssert.class);
  }

  public CityAssert cityNameIs(final String testCityName) {
    isNotNull();
    Assertions.assertThat(actual.getCityName()).isEqualTo(testCityName);
    return this;
  }

  public CityAssert geoNameIs(final String testGeoName) {
    isNotNull();
    Assertions.assertThat(actual.getGeoName()).isEqualTo(testGeoName);
    return this;
  }

  public CityAssert geoIdIs(final int testGeoId) {
    isNotNull();
    Assertions.assertThat(actual.getGeoId()).isEqualTo(testGeoId);
    return this;
  }

  public CityAssert countryAbbreviationIs(final String testCountryAbbreviation) {
    isNotNull();
    Assertions.assertThat(actual.getCountryAbbreviation()).isEqualTo(testCountryAbbreviation);
    return this;
  }
}
