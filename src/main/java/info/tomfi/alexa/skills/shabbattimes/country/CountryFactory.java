package info.tomfi.alexa.skills.shabbattimes.country;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import info.tomfi.alexa.skills.shabbattimes.enums.CountryInfo;
import info.tomfi.alexa.skills.shabbattimes.exception.NoJsonFileException;
import info.tomfi.alexa.skills.shabbattimes.exception.UnknownCountryException;

public final class CountryFactory
{
    private static final List<String> israelNames = Arrays.asList("israel");
    private static final List<String> usNames = Arrays.asList("united states");
    private static final List<String> gbNames = Arrays.asList("united kingdom", "great britain", "britain", "england");

    private static Map<CountryInfo, Country> countryPool = new ConcurrentHashMap<>();

    private CountryFactory()
    {
    }

    public static Country getCountry(final String countryName) throws NoJsonFileException, UnknownCountryException
    {
        final String lowerCountry = countryName.toLowerCase();
        if (israelNames.contains(lowerCountry))
        {
            return getCountryByMember(CountryInfo.ISRAEL);
        }
        else if (usNames.contains(lowerCountry))
        {
            return getCountryByMember(CountryInfo.UNITED_STATES);
        }
        else if (gbNames.contains(lowerCountry))
        {
            return getCountryByMember(CountryInfo.UNITED_KINGDOM);
        }
        else
        {
            throw new UnknownCountryException(String.join(" ", "unknown country name", countryName));
        }
    }

    public static Country getCountryByMember(final CountryInfo member)
    {
        return countryPool.computeIfAbsent(member, newMember -> new Country(newMember));
    }
}
