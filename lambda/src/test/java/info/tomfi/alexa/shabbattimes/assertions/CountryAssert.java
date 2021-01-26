/**
 * Copyright Tomer Figenblat Licensed under the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in
 * writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific
 * language governing permissions and limitations under the License.
 */
package info.tomfi.alexa.shabbattimes.assertions;

import static java.util.stream.Collectors.joining;

import info.tomfi.alexa.shabbattimes.BundleKey;
import info.tomfi.alexa.shabbattimes.City;
import info.tomfi.alexa.shabbattimes.Country;
import java.util.List;
import org.assertj.core.api.AbstractAssert;

public final class CountryAssert extends AbstractAssert<CountryAssert, Country> {
  protected CountryAssert(final Country actual) {
    super(actual, CountryAssert.class);
  }

  public CountryAssert abbreviationIs(final String testAbbreviation) {
    isNotNull();
    if (!actual.abbreviation().equals(testAbbreviation)) {
      failWithMessage(
          "Expected abbreviation to be <%s> but was <%s>", testAbbreviation, actual.abbreviation());
    }
    return this;
  }

  public CountryAssert nameIs(final String testName) {
    isNotNull();
    if (!actual.name().equals(testName)) {
      failWithMessage("Expected name to be <%s> but was <%s>", testName, actual.name());
    }
    return this;
  }

  public CountryAssert stringCitiesIs(final String testStringCitiesNames) {
    isNotNull();
    if (!actual.stringCities().equals(testStringCitiesNames)) {
      failWithMessage(
          "Expected stringCities to be <%s> but was <%s>",
          testStringCitiesNames, actual.stringCities());
    }
    return this;
  }

  public CountryAssert citiesAre(final List<City> testCities) {
    isNotNull();
    if (actual.cities().size() != testCities.size()
        || !actual.cities().stream().allMatch(testCities::contains)) {
      failWithMessage(
          "Expected list of cities to be <%s> but was <%s>",
          actual.cities().stream().map(c -> c.cityName()).collect(joining(",")),
          testCities.stream().map(c -> c.cityName()).collect(joining(",")));
    }
    return this;
  }

  public CountryAssert utterancesAre(final List<String> testUtterances) {
    isNotNull();
    if (actual.utterances().size() != testUtterances.size()
        || !actual.utterances().stream().allMatch(testUtterances::contains)) {
      failWithMessage(
          "Expected list of utterances to be <%s> but was <%s>",
          actual.utterances().stream().collect(joining(",")),
          testUtterances.stream().collect(joining(",")));
    }
    return this;
  }

  public CountryAssert bundleKeyIs(final BundleKey testBundleKey) {
    isNotNull();
    if (!actual.bundleKey().equals(testBundleKey)) {
      failWithMessage(
          "Expected bundleKey to be <%s> but was <%s>", testBundleKey, actual.bundleKey());
    }
    return this;
  }
}
