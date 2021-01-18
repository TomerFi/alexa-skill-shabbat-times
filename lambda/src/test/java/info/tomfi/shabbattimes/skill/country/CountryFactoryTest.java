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
package info.tomfi.shabbattimes.skill.country;

import static info.tomfi.shabbattimes.skill.assertions.Assertions.assertThat;
import static info.tomfi.shabbattimes.skill.assertions.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import info.tomfi.shabbattimes.skill.enums.CountryInfo;
import info.tomfi.shabbattimes.skill.exception.NoJsonFileException;
import info.tomfi.shabbattimes.skill.exception.UnknownCountryException;
import info.tomfi.shabbattimes.skill.tools.DynTypeIterator;
import java.util.Iterator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public final class CountryFactoryTest {
  @Test
  @DisplayName("test country object with a test json file represnting the cities")
  public void countryJsonToObject_testJsonFile_validateValues() {
    final CountryInfo mockedMember = mock(CountryInfo.class);
    when(mockedMember.getAbbreviation()).thenReturn("TST");
    when(mockedMember.getName()).thenReturn("test country");

    final Country countryTest = CountryFactory.getCountry(mockedMember);
    assertThat(countryTest)
        .abbreviationIs("TST")
        .nameIs("test country")
        .prettyCityNamesIs("testCity1, testCity2");

    assertThat(countryTest.iterator())
        .toIterable()
        .extractingResultOf("getCityName", String.class)
        .containsExactly("testCity1", "testCity2");

    assertThat(countryTest.iterator())
        .toIterable()
        .extractingResultOf("getGeoName", String.class)
        .containsExactly("TST-testCity1", "TST-testCity2");

    assertThat(countryTest.iterator())
        .toIterable()
        .extractingResultOf("getGeoId", Integer.class)
        .containsExactly(1234567, 7654321);

    assertThat(countryTest.iterator())
        .toIterable()
        .extractingResultOf("getCountryAbbreviation", String.class)
        .containsExactly("TST", "TST");

    assertThat(countryTest.iterator())
        .toIterable()
        .extractingResultOf("iterator", Iterator.class)
        .allMatch(obj -> obj instanceof DynTypeIterator);
  }

  @Test
  @DisplayName("test exception throwing when attempting to create an unknown country")
  public void getCountry_unknownCountryName_throwsExceptions() {
    assertThatExceptionOfType(UnknownCountryException.class)
        .isThrownBy(() -> CountryFactory.getCountry("fake country"));
  }

  @Test
  @DisplayName("test exception thrown when no country json found")
  public void getCountry_noJsonFile_throwsExcetption() {
    final CountryInfo mockedMember = mock(CountryInfo.class);
    when(mockedMember.getAbbreviation()).thenReturn("NonExisting");
    when(mockedMember.getName()).thenReturn("someValue");

    assertThatExceptionOfType(NoJsonFileException.class)
        .isThrownBy(() -> CountryFactory.getCountry(mockedMember));
  }
}
