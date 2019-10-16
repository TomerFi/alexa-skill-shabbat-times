package info.tomfi.alexa.skills.shabbattimes.city;

import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;

public final class CityAssert extends AbstractAssert<CityAssert, City>
{
    public CityAssert(final City actual)
    {
        super(actual, CityAssert.class);
    }

    public static CityAssert assertThat(final City actual)
    {
        return new CityAssert(actual);
    }

    public CityAssert cityNameIs(final String testCityName)
    {
        isNotNull();
        Assertions.assertThat(actual.getCityName()).isEqualTo(testCityName);
        return this;
    }

    public CityAssert geoNameIs(final String testGeoName)
    {
        isNotNull();
        Assertions.assertThat(actual.getGeoName()).isEqualTo(testGeoName);
        return this;
    }

    public CityAssert geoIdIs(final int testGeoId)
    {
        isNotNull();
        Assertions.assertThat(actual.getGeoId()).isEqualTo(testGeoId);
        return this;
    }

    public CityAssert countryAbbreviationIs(final String testCountryAbbreviation)
    {
        isNotNull();
        Assertions.assertThat(actual.getCountryAbbreviation()).isEqualTo(testCountryAbbreviation);
        return this;
    }
}
