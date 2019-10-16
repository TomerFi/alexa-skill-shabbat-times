package info.tomfi.alexa.skills.shabbattimes.tools;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.amazon.ask.model.Slot;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import info.tomfi.alexa.skills.shabbattimes.city.City;
import info.tomfi.alexa.skills.shabbattimes.city.CityAssert;
import info.tomfi.alexa.skills.shabbattimes.exception.NoCityFoundException;
import info.tomfi.alexa.skills.shabbattimes.exception.NoCityInCountryException;

public final class CityLocatorTest
{
    @Test
    @DisplayName("test exception thrown when trying to locate a non-existing city with a correct country")
    public void getByCityAndCountry_getUnknownCityWithCountry_throwsExcption()
    {
        final Slot countrySlot = mock(Slot.class);
        when(countrySlot.getValue()).thenReturn("israel");
        final Slot citySlot = mock(Slot.class);
        when(citySlot.getValue()).thenReturn("unknown city");
        assertThatExceptionOfType(NoCityInCountryException.class).isThrownBy(() -> CityLocator.getByCityAndCountry(countrySlot, citySlot));
    }

    @Test
    @DisplayName("test exception thrown when trying to locate a non-existing city with a correct country")
    public void getByCityAndCountry_getUnknownCityWithoutCountry_throwsExcption()
    {
        final Slot countrySlot = mock(Slot.class);
        final Slot citySlot = mock(Slot.class);
        when(citySlot.getValue()).thenReturn("unknown city");
        assertThatExceptionOfType(NoCityFoundException.class).isThrownBy(() -> CityLocator.getByCityAndCountry(countrySlot, citySlot));
    }

    @Test
    @DisplayName("test the retrieval of a city in israel with selecting israel as the country")
    public void getByCityAndCountry_getIsraelCityWithCountry_validateReturn()
    {
        final Slot countrySlot = mock(Slot.class);
        when(countrySlot.getValue()).thenReturn("israel");
        final Slot citySlot = mock(Slot.class);
        when(citySlot.getValue()).thenReturn("ashdod");

        final City city = CityLocator.getByCityAndCountry(countrySlot, citySlot);
        CityAssert.assertThat(city)
            .cityNameIs("ashdod")
            .geoNameIs("IL-Ashdod")
            .geoIdIs(295629)
            .countryAbbreviationIs("IL");
    }

    @Test
    @DisplayName("test the retrieval of a city in israel without selecting a country")
    public void getByCityAndCountry_getIsraelCityWithoutCountry_validateReturn()
    {
        final Slot countrySlot = mock(Slot.class);
        final Slot citySlot = mock(Slot.class);
        when(citySlot.getValue()).thenReturn("ashdod");

        final City city = CityLocator.getByCityAndCountry(countrySlot, citySlot);
        CityAssert.assertThat(city)
            .cityNameIs("ashdod")
            .geoNameIs("IL-Ashdod")
            .geoIdIs(295629)
            .countryAbbreviationIs("IL");
    }

    @Test
    @DisplayName("test the retrieval of a city in united states with selecting the united states as the country")
    public void getByCityAndCountry_getUSCityWithCountry_validateReturn()
    {
        final Slot countrySlot = mock(Slot.class);
        when(countrySlot.getValue()).thenReturn("united states");
        final Slot citySlot = mock(Slot.class);
        when(citySlot.getValue()).thenReturn("atlanta");

        final City city = CityLocator.getByCityAndCountry(countrySlot, citySlot);
        CityAssert.assertThat(city)
            .cityNameIs("atlanta")
            .geoNameIs("US-Atlanta-GA")
            .geoIdIs(4180439)
            .countryAbbreviationIs("US");
    }

    @Test
    @DisplayName("test the retrieval of a city in the united states without selecting a country")
    public void getByCityAndCountry_getUSCityWithoutCountry_validateReturn()
    {
        final Slot countrySlot = mock(Slot.class);
        final Slot citySlot = mock(Slot.class);
        when(citySlot.getValue()).thenReturn("atlanta");

        final City city = CityLocator.getByCityAndCountry(countrySlot, citySlot);
        CityAssert.assertThat(city)
            .cityNameIs("atlanta")
            .geoNameIs("US-Atlanta-GA")
            .geoIdIs(4180439)
            .countryAbbreviationIs("US");
    }

    @Test
    @DisplayName("test the retrieval of a city in united kingdom with selecting the united kingdom as the country")
    public void getByCityAndCountry_getUKCityWithCountry_validateReturn()
    {
        final Slot countrySlot = mock(Slot.class);
        when(countrySlot.getValue()).thenReturn("england");
        final Slot citySlot = mock(Slot.class);
        when(citySlot.getValue()).thenReturn("belfast");

        final City city = CityLocator.getByCityAndCountry(countrySlot, citySlot);
        CityAssert.assertThat(city)
            .cityNameIs("belfast")
            .geoNameIs("GB-Belfast")
            .geoIdIs(2655984)
            .countryAbbreviationIs("GB");
    }

    @Test
    @DisplayName("test the retrieval of a city in the united kingdom without selecting a country")
    public void getByCityAndCountry_getUKCityWithoutCountry_validateReturn()
    {
        final Slot countrySlot = mock(Slot.class);
        final Slot citySlot = mock(Slot.class);
        when(citySlot.getValue()).thenReturn("belfast");

        final City city = CityLocator.getByCityAndCountry(countrySlot, citySlot);
        CityAssert.assertThat(city)
            .cityNameIs("belfast")
            .geoNameIs("GB-Belfast")
            .geoIdIs(2655984)
            .countryAbbreviationIs("GB");
    }
}
