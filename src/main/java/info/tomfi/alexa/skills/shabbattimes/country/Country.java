package info.tomfi.alexa.skills.shabbattimes.country;

import static java.util.stream.Collectors.joining;

import static info.tomfi.alexa.skills.shabbattimes.city.CitiesFactory.getCities;

import java.util.Iterator;
import java.util.List;

import info.tomfi.alexa.skills.shabbattimes.city.City;
import info.tomfi.alexa.skills.shabbattimes.enums.CountryInfo;
import info.tomfi.alexa.skills.shabbattimes.exception.NoJsonFileException;
import info.tomfi.alexa.skills.shabbattimes.tools.DynTypeIterator;
import lombok.Getter;

public final class Country implements Iterable<City>
{
    @Getter private final String abbreviation;
    @Getter private final String name;

    private final List<City> citiesList;

    protected Country(final CountryInfo country) throws NoJsonFileException {
        abbreviation = country.getAbbreviation();
        name = country.getName();
        citiesList = getCities(this, String.format("cities/%s_Cities.json", abbreviation));
    }

    public Iterator<City> iterator() {
        return new DynTypeIterator<City>(citiesList);
    }

    public String getPrettyCityNames() {
        return citiesList.stream().map(cityObj -> cityObj.getCityName()).collect(joining(", "));
    }
}
