package info.tomfi.alexa.skills.shabbattimes.assertions;

import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;

import info.tomfi.alexa.skills.shabbattimes.country.Country;

public final class CountryAssert extends AbstractAssert<CountryAssert, Country>
{
    protected CountryAssert(final Country actual)
    {
        super(actual, CountryAssert.class);
    }

    public CountryAssert abbreviationIs(final String testAbbreviation)
    {
        isNotNull();
        Assertions.assertThat(actual.getAbbreviation()).isEqualTo(testAbbreviation);
        return this;
    }

    public CountryAssert nameIs(final String testName)
    {
        isNotNull();
        Assertions.assertThat(actual.getName()).isEqualTo(testName);
        return this;
    }

    public CountryAssert prettyCityNamesIs(final String testPrettyCityNames)
    {
        isNotNull();
        Assertions.assertThat(actual.getPrettyCityNames()).isEqualTo(testPrettyCityNames);
        return this;
    }
}
