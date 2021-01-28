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
package info.tomfi.alexa.shabbattimes.assertions;

import static java.util.stream.Collectors.joining;

import info.tomfi.alexa.shabbattimes.City;
import java.util.List;
import org.assertj.core.api.AbstractAssert;

public final class CityAssert extends AbstractAssert<CityAssert, City> {
  protected CityAssert(final City actual) {
    super(actual, CityAssert.class);
  }

  public CityAssert cityNameIs(final String testCityName) {
    isNotNull();
    if (!actual.cityName().equals(testCityName)) {
      failWithMessage("Expected cityName to be <%s> but was <%s>", testCityName, actual.cityName());
    }
    return this;
  }

  public CityAssert geoNameIs(final String testGeoName) {
    isNotNull();
    if (!actual.geoName().equals(testGeoName)) {
      failWithMessage("Expected geoName to be <%s> but was <%s>", testGeoName, actual.geoName());
    }
    return this;
  }

  public CityAssert geoIdIs(final int testGeoId) {
    isNotNull();
    if (actual.geoId() != testGeoId) {
      failWithMessage("Expected geoId to be <%s> but was <%s>", testGeoId, actual.geoId());
    }
    return this;
  }

  public CityAssert countryAbbreviationIs(final String testCountryAbbreviation) {
    isNotNull();
    if (!actual.countryAbbreviation().equals(testCountryAbbreviation)) {
      failWithMessage(
          "Expected countryAbbreviation to be <%s> but was <%s>",
          testCountryAbbreviation, actual.countryAbbreviation());
    }
    return this;
  }

  public CityAssert aliasesAre(final List<String> testAliases) {
    isNotNull();
    if (actual.aliases().size() != testAliases.size()
        || !actual.aliases().stream().allMatch(testAliases::contains)) {
      failWithMessage(
          "Expected list of aliases to be <%s> but was <%s>",
          actual.aliases().stream().collect(joining(",")),
          testAliases.stream().collect(joining(",")));
    }
    return this;
  }
}
