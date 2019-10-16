package info.tomfi.alexa.skills.shabbattimes.tools;

import java.util.Iterator;
import java.util.Optional;

import com.amazon.ask.model.Slot;

import info.tomfi.alexa.skills.shabbattimes.city.City;
import info.tomfi.alexa.skills.shabbattimes.country.Country;
import info.tomfi.alexa.skills.shabbattimes.country.CountryFactory;
import info.tomfi.alexa.skills.shabbattimes.enums.CountryInfo;
import info.tomfi.alexa.skills.shabbattimes.exception.NoCityFoundException;
import info.tomfi.alexa.skills.shabbattimes.exception.NoCityInCountryException;

public final class CityLocator
{
    private CityLocator()
    {
    }

    public static City getByCityAndCountry(final Slot countrySlot, final Slot citySlot) throws NoCityFoundException, NoCityInCountryException
    {
        if (countrySlot.getValue() == null)
        {
            return getByCity(citySlot);
        }
        final Country country = CountryFactory.getCountry(countrySlot.getValue());
        final Optional<City> cityOpt = findCityInCountry(country, citySlot.getValue());
        if (cityOpt.isPresent())
        {
            return cityOpt.get();
        }
        throw new NoCityInCountryException(String.format("no city %s in %s.", citySlot.getValue(), countrySlot.getValue()));
    }

    private static City getByCity(final Slot citySlot)
    {
        for (CountryInfo member : CountryInfo.values())
        {
            final Country country = CountryFactory.getCountry(member);
            final Optional<City> cityOpt = findCityInCountry(country, citySlot.getValue());
            if (cityOpt.isPresent())
            {
                return cityOpt.get();
            }
        }
        throw new NoCityFoundException(String.format("city %s not found", citySlot.getValue()));
    }

    private static Optional<City> findCityInCountry(final Country country, final String cityName)
    {
        final Iterator<City> cities = country.iterator();
        while (cities.hasNext())
        {
            final City currentCity = cities.next();
            final Iterator<String> aliases = currentCity.iterator();
            while (aliases.hasNext())
            {
                final String currentAlias = aliases.next();
                if (currentAlias.equalsIgnoreCase(cityName))
                {
                    return Optional.of(currentCity);
                }
            }
        }
        return Optional.empty();
    }
}
