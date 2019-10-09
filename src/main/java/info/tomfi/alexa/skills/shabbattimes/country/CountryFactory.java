package info.tomfi.alexa.skills.shabbattimes.country;

import static info.tomfi.alexa.skills.shabbattimes.tools.GlobalEnums.CountryInfo;

import java.util.Arrays;
import java.util.List;

import info.tomfi.alexa.skills.shabbattimes.exception.NoJsonFileException;
import info.tomfi.alexa.skills.shabbattimes.exception.UnknownCountryException;

public final class CountryFactory
{
    private static final List<String> israelNames = Arrays.asList("israel");
    private static final List<String> usNames = Arrays.asList("united states");
    private static final List<String> gbNames = Arrays.asList("united kingdom", "great britain", "britain", "england");

    public static Country getCountry(final String countryName) throws NoJsonFileException, UnknownCountryException
    {
        if (israelNames.contains(countryName))
        {
            return new Country(CountryInfo.ISRAEL);
        }
        else if (usNames.contains(countryName))
        {
            return new Country(CountryInfo.UNITED_STATES);
        }
        else if (gbNames.contains(countryName))
        {
            return new Country(CountryInfo.UNITED_KINGDOM);
        }
        else
        {
            throw new UnknownCountryException(String.join(" ", "unknown country name", countryName));
        }
    }
}
