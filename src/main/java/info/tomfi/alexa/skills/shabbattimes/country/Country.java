package info.tomfi.alexa.skills.shabbattimes.country;

import static info.tomfi.alexa.skills.shabbattimes.tools.SkillTools.getCityListFromJsonFile;
import static java.util.stream.Collectors.joining;

import java.util.Iterator;
import java.util.List;

import info.tomfi.alexa.skills.shabbattimes.city.City;
import info.tomfi.alexa.skills.shabbattimes.enums.CountryInfo;
import info.tomfi.alexa.skills.shabbattimes.exception.NoJsonFileException;
import info.tomfi.alexa.skills.shabbattimes.tools.DynTypeIterator;
import lombok.Getter;

/**
 * Pojo for constructing the Country object from the backend json files.
 *
 * @author Tomer Figenblat {@literal <tomer.figenblat@gmail.com>}
 */
public final class Country implements Iterable<City>
{
    @Getter private final String abbreviation;
    @Getter private final String name;

    private final List<City> citiesList;

    protected Country(final CountryInfo country) throws NoJsonFileException
    {
        abbreviation = country.getAbbreviation();
        name = country.getName();
        citiesList = getCityListFromJsonFile(abbreviation);
    }

    /**
     * Get an iterator containing all the {@link info.tomfi.alexa.skills.shabbattimes.city.City} objects for this country object.
     */
    public Iterator<City> iterator()
    {
        return new DynTypeIterator<City>(citiesList);
    }

    /**
     * Get a string represnting all the city names in to this country.
     * @return a string containg the cities list joined by a comma.
     */
    public String getPrettyCityNames()
    {
        return citiesList.stream().map(cityObj -> cityObj.getCityName()).collect(joining(", "));
    }
}
