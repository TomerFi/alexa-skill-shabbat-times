package info.tomfi.alexa.skills.shabbattimes.country;

import static lombok.AccessLevel.PRIVATE;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import info.tomfi.alexa.skills.shabbattimes.enums.CountryInfo;
import info.tomfi.alexa.skills.shabbattimes.exception.NoJsonFileException;
import info.tomfi.alexa.skills.shabbattimes.exception.UnknownCountryException;

import lombok.NoArgsConstructor;
import lombok.Synchronized;
import lombok.val;

@NoArgsConstructor(access = PRIVATE)
public final class CountryFactory
{
    private static final Map<CountryInfo, Country> countryPool = new ConcurrentHashMap<>();

    @Synchronized
    public static Country getCountry(final String countryName) throws NoJsonFileException, UnknownCountryException
    {
        val lowerCountry = countryName.toLowerCase();
        for (val current : CountryInfo.values())
        {
            if (current.getUtterances().contains(lowerCountry))
            {
                return getCountry(current);
            }
        }
        throw new UnknownCountryException(String.join(" ", "unknown country name", countryName));
    }

    public static Country getCountry(final CountryInfo member) throws NoJsonFileException
    {
        return countryPool.computeIfAbsent(member, newMember -> new Country(newMember));
    }
}
