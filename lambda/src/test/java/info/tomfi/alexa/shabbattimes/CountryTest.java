/*
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

import static info.tomfi.alexa.shabbattimes.assertions.BDDAssertions.then;
import static nl.jqno.equalsverifier.Warning.INHERITED_DIRECTLY_FROM_OBJECT;
import static nl.jqno.equalsverifier.Warning.NULL_FIELDS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import com.github.javafaker.Faker;
import java.util.List;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/** Test cases for the Country implementation. */
@ExtendWith(MockitoExtension.class)
@Tag("unit-tests")
final class CountryTest {
  private Faker faker;

  @BeforeEach
  void initialize() {
    faker = new Faker();
  }

  @Test
  void verify_country_object_equals_and_hashcode_implementations() {
    // verify abstraction, not implementing equals and hashcode
    EqualsVerifier.forClass(City.class)
        .withRedefinedSubclass(AutoValue_City.class)
        .usingGetClass()
        .suppress(INHERITED_DIRECTLY_FROM_OBJECT)
        .verify();
    // verify implementation, not verifying nulls
    EqualsVerifier.forClass(AutoValue_City.class)
        .withRedefinedSuperclass()
        .suppress(NULL_FIELDS)
        .verify();
  }

  @Test
  void build_a_country_object_using_the_builder_and_verify_the_value(
      @Mock final City city1, @Mock final City city2) {
    // stub the mocked cities with a fake name and alias
    given(city1.cityName()).willReturn("city1");
    given(city1.aliases()).willReturn(List.of("city1alias"));
    given(city2.cityName()).willReturn("city2");
    // build the country using the builder
    var country =
        Country.builder()
            .abbreviation("AB")
            .cities(List.of(city1, city2))
            .name("name")
            .bundleKey(BundleKey.NOT_FOUND_IN_ISRAEL)
            .utterances(List.of("utter1", "utter2"))
            .build();
    // verify the pojo
    then(country)
        .abbreviationIs("AB")
        .nameIs("name")
        .stringCitiesIs("city1, city2")
        .bundleKeyIs(BundleKey.NOT_FOUND_IN_ISRAEL)
        .utterancesAre(List.of("utter1", "utter2"));
    // verify the getCity functionality
    assertThat(country.getCity("city1alias")).isNotEmpty().hasValue(city1);
    assertThat(country.getCity("city2alias")).isEmpty();
    // verify the hasUtterance functionality
    assertThat(country.hasUtterance("utter1")).isTrue();
    assertThat(country.hasUtterance("utter3")).isFalse();
  }

  @Test
  void value_country_to_string_implementation_should_yield_non_empty_string(
      @Mock final City city1, @Mock final City city2) {
    // create a country pojo
    var country =
        Country.builder()
            .abbreviation("AB")
            .cities(List.of(city1, city2))
            .name("name")
            .bundleKey(BundleKey.NOT_FOUND_IN_ISRAEL)
            .utterances(List.of("utter1", "utter2"))
            .build();
    // verify toString implementation
    assertThat(country.toString()).isNotBlank();
  }

  @Test
  void get_a_city_by_a_correct_name_value_should_return_the_city(@Mock final City city) {
    var cityName = faker.country().capital();
    // stub the mocked cities with a fake name and alias
    given(city.cityName()).willReturn(cityName);
    // build the country using the builder
    var country =
        Country.builder()
            .abbreviation("AB")
            .cities(List.of(city))
            .name("countryName")
            .bundleKey(BundleKey.NOT_FOUND_IN_ISRAEL)
            .utterances(List.of("utter1", "utter2"))
            .build();
    // verify retrieving the city by it's correct name from the constructed country
    assertThat(country.getCity(cityName)).hasValue(city);
  }

  @Test
  void get_a_city_by_a_correct_alias_value_should_return_the_city(@Mock final City city) {
    var cityAlias = faker.lorem().word();
    // stub the mocked cities with a fake name and alias
    given(city.cityName()).willReturn("cityName");
    given(city.aliases()).willReturn(List.of(cityAlias));
    // build the country using the builder
    var country =
        Country.builder()
            .abbreviation("AB")
            .cities(List.of(city))
            .name("countryName")
            .bundleKey(BundleKey.NOT_FOUND_IN_ISRAEL)
            .utterances(List.of("utter1", "utter2"))
            .build();
    // verify retrieving the city by it's correct name from the constructed country
    assertThat(country.getCity(cityAlias)).hasValue(city);
  }

  @Test
  void get_a_city_by_an_incorrect_value_should_return_empty(@Mock final City city) {
    // stub the mocked cities with a fake name and alias
    given(city.cityName()).willReturn("cityName");
    given(city.aliases()).willReturn(List.of("cityAlias"));
    // build the country using the builder
    var country =
        Country.builder()
            .abbreviation("AB")
            .cities(List.of(city))
            .name("countryName")
            .bundleKey(BundleKey.NOT_FOUND_IN_ISRAEL)
            .utterances(List.of("utter1", "utter2"))
            .build();
    // verify retrieving the city by an incorrect value returns empty
    assertThat(country.getCity(faker.lorem().word())).isEmpty();
  }

  @Test
  void querying_country_for_known_uterrance_yields_true(@Mock final City city) {
    // build the country using the builder
    var countryUtterance = faker.lorem().word();
    var country =
        Country.builder()
            .abbreviation("AB")
            .cities(List.of(city))
            .name("countryName")
            .bundleKey(faker.options().nextElement(BundleKey.values()))
            .utterances(List.of(countryUtterance))
            .build();
    // querying for known utterance should return true
    assertThat(country.hasUtterance(countryUtterance)).isTrue();
  }

  @Test
  void querying_country_for_unknown_uterrance_yields_false(@Mock final City city) {
    // build the country using the builder
    var country =
        Country.builder()
            .abbreviation("AB")
            .cities(List.of(city))
            .name("countryName")
            .bundleKey(faker.options().nextElement(BundleKey.values()))
            .utterances(List.of(faker.lorem().word()))
            .build();
    // querying for known utterance should return true
    assertThat(country.hasUtterance(faker.lorem().word())).isFalse();
  }
}
