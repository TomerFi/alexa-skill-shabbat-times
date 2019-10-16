package info.tomfi.alexa.skills.shabbattimes.country;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import info.tomfi.alexa.skills.shabbattimes.enums.CountryInfo;
import info.tomfi.alexa.skills.shabbattimes.exception.NoJsonFileException;
import info.tomfi.alexa.skills.shabbattimes.exception.UnknownCountryException;

public final class CountryFactory
{
    private static Map<CountryInfo, Country> countryPool = new ConcurrentHashMap<>();

    private CountryFactory()
    {
    }

    public static Country getCountry(final String countryName) throws NoJsonFileException, UnknownCountryException
    {
        final String lowerCountry = countryName.toLowerCase();
        for (CountryInfo current : CountryInfo.values())
        {
            if (current.getUtterances().contains(lowerCountry))
            {
                return getCountryByMember(current);
            }
        }
        throw new UnknownCountryException(String.join(" ", "unknown country name", countryName));
    }

    public static Country getCountryByMember(final CountryInfo member)
    {
        return countryPool.computeIfAbsent(member, newMember -> new Country(newMember));
    }
}
