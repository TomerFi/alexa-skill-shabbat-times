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

import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenExceptionOfType;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.when;
import static org.mockito.quality.Strictness.LENIENT;

import com.amazon.ask.model.Slot;
import com.github.javafaker.Faker;
import info.tomfi.alexa.shabbattimes.City;
import info.tomfi.alexa.shabbattimes.Country;
import info.tomfi.alexa.shabbattimes.exceptions.NoCityFoundException;
import info.tomfi.alexa.shabbattimes.exceptions.NoCityInCountryException;
import info.tomfi.alexa.shabbattimes.exceptions.NoCountryFoundException;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;

/** Locator service provider test cases. */
@MockitoSettings(strictness = LENIENT)
@Tag("unit-tests")
final class LocatorServiceImplTest {
  @Mock private Slot countrySlot;
  @Mock private Slot citySlot;
  @Mock private Country country1;
  @Mock private Country country2;
  @Mock private City city1;
  @Mock private City city2;

  private Faker faker;
  private String reqCityAlias;

  private LocatorServiceImpl sut;

  @BeforeEach
  void initialize() {
    faker = new Faker();
    // configure city slot to return a fake city alias
    reqCityAlias = faker.lorem().word();
    when(citySlot.getValue()).thenReturn(reqCityAlias);
    // stub country 1
    when(country1.cities()).thenReturn(List.of(city1));
    when(country1.getCity(anyString())).thenCallRealMethod();
    // stub country 2
    when(country2.cities()).thenReturn(List.of(city2));
    when(country2.getCity(anyString())).thenCallRealMethod();
    // instantiate the sut
    sut = new LocatorServiceImpl(List.of(country1, country2));
  }

  @Test
  void locate_city_without_country_when_two_cities_has_same_name_in_two_countries_returns_first() {
    // stub the country slot as null to locate without stating a country
    given(countrySlot.getValue()).willReturn(null);
    // stub two different cities (from different countries) with the same name
    given(city1.cityName()).willReturn(reqCityAlias);
    given(city2.cityName()).willReturn(reqCityAlias);
    // when locating the city from the predefined country list
    var city = sut.locate(citySlot);
    // then the city from the first country should be picked up
    then(city).isEqualTo(city1);
  }

  @Test
  void
      locate_city_with_null_country_when_two_cities_has_same_name_in_two_countries_returns_first() {
    // stub the country slot as null to locate without stating a country
    given(countrySlot.getValue()).willReturn(null);
    // stub two different cities (from different countries) with the same name
    given(city1.cityName()).willReturn(reqCityAlias);
    given(city2.cityName()).willReturn(reqCityAlias);
    // when locating the city from the predefined country list
    var city = sut.locate(countrySlot, citySlot);
    // then the city from the first country should be picked up
    then(city).isEqualTo(city1);
  }

  @Test
  void locate_city_without_country_when_no_matching_city_found_throws_no_city_found_exception() {
    // stub the country slot as null to locate without stating a country
    given(countrySlot.getValue()).willReturn(null);
    // stub cities with name other then the alias requested
    given(city1.cityName()).willReturn(faker.lorem().word());
    given(city2.cityName()).willReturn(faker.lorem().word());
    // when trying to locate the city NoCityFoundException is thrown
    thenExceptionOfType(NoCityFoundException.class).isThrownBy(() -> sut.locate(citySlot));
  }

  @Test
  void locate_city_with_null_country_when_no_matching_city_found_throws_no_city_found_exception() {
    // stub the country slot as null to locate without stating a country
    given(countrySlot.getValue()).willReturn(null);
    // stub cities with name other then the alias requested
    given(city1.cityName()).willReturn(faker.lorem().word());
    given(city2.cityName()).willReturn(faker.lorem().word());
    // when trying to locate the city NoCityFoundException is thrown
    thenExceptionOfType(NoCityFoundException.class)
        .isThrownBy(() -> sut.locate(countrySlot, citySlot));
  }

  @Test
  void locate_city_in_country_returns_city() {
    // stub country with a fixed utterence
    var country1Utterence = faker.lorem().word();
    given(country1.utterances()).willReturn(List.of(country1Utterence));
    given(country1.hasUtterance(anyString())).willCallRealMethod();
    // stub the country slot with a correct country utterence
    given(countrySlot.getValue()).willReturn(country1Utterence);
    // stub the city name exactly like the one requested
    given(city1.cityName()).willReturn(reqCityAlias);
    // when locating the city from the predefined country list
    var city = sut.locate(countrySlot, citySlot);
    // then the city should be returned
    then(city).isEqualTo(city1);
  }

  @Test
  void locate_city_returns_city() {
    // stub country with a fixed utterence
    var country1Utterence = faker.lorem().word();
    given(country1.utterances()).willReturn(List.of(country1Utterence));
    given(country1.hasUtterance(anyString())).willCallRealMethod();
    // stub the country slot with a correct country utterence
    given(countrySlot.getValue()).willReturn(country1Utterence);
    // stub the city name exactly like the one requested
    given(city1.cityName()).willReturn(reqCityAlias);
    // when locating the city from the predefined country list
    var city = sut.locate(citySlot);
    // then the city should be returned
    then(city).isEqualTo(city1);
  }

  @Test
  void locate_non_existing_city_in_country_throws_no_city_in_country_exception() {
    // stub country with a fixed utterence
    var country1Utterence = faker.lorem().word();
    given(country1.utterances()).willReturn(List.of(country1Utterence));
    given(country1.hasUtterance(anyString())).willCallRealMethod();
    // stub the country slot with a correct country utterence
    given(countrySlot.getValue()).willReturn(country1Utterence);
    // stub the city name with a diffrent city name then the one requested
    given(city1.cityName()).willReturn(faker.lorem().word());
    // when trying to locate the city NoCityInCountryException is thrown
    thenExceptionOfType(NoCityInCountryException.class)
        .isThrownBy(() -> sut.locate(countrySlot, citySlot));
  }

  @Test
  void locate_city_in_non_existing_country_throws_no_country_found_exception() {
    // stub country with a fixed utterence
    given(country1.utterances()).willReturn(List.of(faker.lorem().word()));
    given(country1.hasUtterance(anyString())).willCallRealMethod();
    // stub the country slot with a different country utterence
    given(countrySlot.getValue()).willReturn(faker.lorem().word());
    // when trying to locate the city NoCountryFoundException is thrown
    thenExceptionOfType(NoCountryFoundException.class)
        .isThrownBy(() -> sut.locate(countrySlot, citySlot));
  }
}
