package info.tomfi.alexa.skills.shabbattimes.tools;

import static info.tomfi.alexa.skills.shabbattimes.assertions.Assertions.assertThat;
import static info.tomfi.alexa.skills.shabbattimes.assertions.Assertions.assertThatExceptionOfType;

import static org.mockito.Mockito.when;

import com.amazon.ask.model.Slot;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import info.tomfi.alexa.skills.shabbattimes.exception.NoCityFoundException;
import info.tomfi.alexa.skills.shabbattimes.exception.NoCityInCountryException;
import lombok.val;

@ExtendWith(MockitoExtension.class)
public final class CityLocatorTest
{
    @Mock private Slot countrySlot;
    @Mock private Slot citySlot;

    @Test
    @DisplayName("test exception thrown when trying to locate a non-existing city with a correct country")
    public void getByCityAndCountry_getUnknownCityWithCountry_throwsExcption()
    {
        when(countrySlot.getValue()).thenReturn("israel");
        when(citySlot.getValue()).thenReturn("unknown city");
        assertThatExceptionOfType(NoCityInCountryException.class).isThrownBy(() -> CityLocator.getByCityAndCountry(countrySlot, citySlot));
    }

    @Test
    @DisplayName("test exception thrown when trying to locate a non-existing city with a correct country")
    public void getByCityAndCountry_getUnknownCityWithoutCountry_throwsExcption()
    {
        when(citySlot.getValue()).thenReturn("unknown city");
        assertThatExceptionOfType(NoCityFoundException.class).isThrownBy(() -> CityLocator.getByCityAndCountry(countrySlot, citySlot));
    }

    @Test
    @DisplayName("test the retrieval of a city in israel with selecting israel as the country")
    public void getByCityAndCountry_getIsraelCityWithCountry_validateReturn()
    {
        when(countrySlot.getValue()).thenReturn("israel");
        when(citySlot.getValue()).thenReturn("ashdod");

        val city = CityLocator.getByCityAndCountry(countrySlot, citySlot);
        assertThat(city)
            .cityNameIs("ashdod")
            .geoNameIs("IL-Ashdod")
            .geoIdIs(295629)
            .countryAbbreviationIs("IL");
    }

    @Test
    @DisplayName("test the retrieval of a city in israel without selecting a country")
    public void getByCityAndCountry_getIsraelCityWithoutCountry_validateReturn()
    {
        when(citySlot.getValue()).thenReturn("ashdod");

        val city = CityLocator.getByCityAndCountry(countrySlot, citySlot);
        assertThat(city)
            .cityNameIs("ashdod")
            .geoNameIs("IL-Ashdod")
            .geoIdIs(295629)
            .countryAbbreviationIs("IL");
    }

    @Test
    @DisplayName("test the retrieval of a city in united states with selecting the united states as the country")
    public void getByCityAndCountry_getUSCityWithCountry_validateReturn()
    {
        when(countrySlot.getValue()).thenReturn("united states");
        when(citySlot.getValue()).thenReturn("atlanta");

        val city = CityLocator.getByCityAndCountry(countrySlot, citySlot);
        assertThat(city)
            .cityNameIs("atlanta")
            .geoNameIs("US-Atlanta-GA")
            .geoIdIs(4180439)
            .countryAbbreviationIs("US");
    }

    @Test
    @DisplayName("test the retrieval of a city in the united states without selecting a country")
    public void getByCityAndCountry_getUSCityWithoutCountry_validateReturn()
    {
        when(citySlot.getValue()).thenReturn("atlanta");

        val city = CityLocator.getByCityAndCountry(countrySlot, citySlot);
        assertThat(city)
            .cityNameIs("atlanta")
            .geoNameIs("US-Atlanta-GA")
            .geoIdIs(4180439)
            .countryAbbreviationIs("US");
    }

    @Test
    @DisplayName("test the retrieval of a city in united kingdom with selecting the united kingdom as the country")
    public void getByCityAndCountry_getUKCityWithCountry_validateReturn()
    {
        when(countrySlot.getValue()).thenReturn("england");
        when(citySlot.getValue()).thenReturn("belfast");

        val city = CityLocator.getByCityAndCountry(countrySlot, citySlot);
        assertThat(city)
            .cityNameIs("belfast")
            .geoNameIs("GB-Belfast")
            .geoIdIs(2655984)
            .countryAbbreviationIs("GB");
    }

    @Test
    @DisplayName("test the retrieval of a city in the united kingdom without selecting a country")
    public void getByCityAndCountry_getUKCityWithoutCountry_validateReturn()
    {
        when(citySlot.getValue()).thenReturn("belfast");

        val city = CityLocator.getByCityAndCountry(countrySlot, citySlot);
        assertThat(city)
            .cityNameIs("belfast")
            .geoNameIs("GB-Belfast")
            .geoIdIs(2655984)
            .countryAbbreviationIs("GB");
    }
}
