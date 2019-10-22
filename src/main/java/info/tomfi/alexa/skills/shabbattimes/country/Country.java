package info.tomfi.alexa.skills.shabbattimes.country;

import static info.tomfi.alexa.skills.shabbattimes.tools.SkillTools.getCityListFromJsonFile;
import static java.util.stream.Collectors.joining;

import info.tomfi.alexa.skills.shabbattimes.city.City;
import info.tomfi.alexa.skills.shabbattimes.enums.CountryInfo;
import info.tomfi.alexa.skills.shabbattimes.exception.NoJsonFileException;
import info.tomfi.alexa.skills.shabbattimes.tools.DynTypeIterator;

import java.util.Iterator;
import java.util.List;

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

    private final transient List<City> citiesList;

    /**
     * Main and only constructor, call for the static tool creating the list of cities.
     * @param country the {@link info.tomfi.alexa.skills.shabbattimes.enums.CountryInfo} member
     *     describing the country.
     * @throws NoJsonFileException when a country corresponding backend json file was not found.
     */
    protected Country(final CountryInfo country) throws NoJsonFileException
    {
        abbreviation = country.getAbbreviation();
        name = country.getName();
        citiesList = getCityListFromJsonFile(abbreviation);
    }

    @Override
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
