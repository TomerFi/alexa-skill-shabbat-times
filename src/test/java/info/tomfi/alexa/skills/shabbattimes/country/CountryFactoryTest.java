package info.tomfi.alexa.skills.shabbattimes.country;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Iterator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import info.tomfi.alexa.skills.shabbattimes.enums.CountryInfo;
import info.tomfi.alexa.skills.shabbattimes.exception.UnknownCountryException;
import info.tomfi.alexa.skills.shabbattimes.tools.DynTypeIterator;

public final class CountryFactoryTest
{
    @Test
    @DisplayName("test country object with a test json file represnting the cities")
    public void countryJsonToObject_testJsonFile_validateValues()
    {
        final CountryInfo mockedMember = mock(CountryInfo.class);
        when(mockedMember.getAbbreviation()).thenReturn("TST");
        when(mockedMember.getName()).thenReturn("test country");

        final Country countryTest = CountryFactory.getCountry(mockedMember);
        CountryAssert.assertThat(countryTest)
            .abbreviationIs("TST")
            .nameIs("test country")
            .prettyCityNamesIs("testCity1, testCity2");

        assertThat(countryTest.iterator()).toIterable()
            .extractingResultOf("getCityName", String.class).containsExactly("testCity1", "testCity2");

        assertThat(countryTest.iterator()).toIterable()
            .extractingResultOf("getGeoName", String.class).containsExactly("TST-testCity1", "TST-testCity2");

        assertThat(countryTest.iterator()).toIterable()
            .extractingResultOf("getGeoId", Integer.class).containsExactly(1234567, 7654321);

        assertThat(countryTest.iterator()).toIterable()
            .extractingResultOf("getCountryAbbreviation", String.class).containsExactly("TST", "TST");

        assertThat(countryTest.iterator()).toIterable()
            .extractingResultOf("iterator", Iterator.class).allMatch(obj -> obj instanceof DynTypeIterator);
    }

    @Test
    @DisplayName("test exception throwing when attempting to create an unknown country")
    public void getCountry_unknownCountryName_throwsExceptions()
    {
        assertThatExceptionOfType(UnknownCountryException.class).isThrownBy(() -> CountryFactory.getCountry("fake country"));
    }
}
