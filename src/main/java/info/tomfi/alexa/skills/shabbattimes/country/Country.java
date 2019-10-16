package info.tomfi.alexa.skills.shabbattimes.country;

import static java.util.stream.Collectors.joining;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import info.tomfi.alexa.skills.shabbattimes.city.City;
import info.tomfi.alexa.skills.shabbattimes.enums.CountryInfo;
import info.tomfi.alexa.skills.shabbattimes.exception.NoJsonFileException;
import info.tomfi.alexa.skills.shabbattimes.tools.DynTypeIterator;

public final class Country implements Iterable<City>
{
    public final String abbreviation;
    public final String name;
    private final List<City> citiesList;

    protected Country(final CountryInfo country) throws NoJsonFileException
    {
        abbreviation = country.getAbbreviation();
        name = country.getName();
        citiesList = cityListFromJsonFile(String.format("cities/%s_Cities.json", abbreviation));
    }

    public String getAbbreviation()
    {
        return abbreviation;
    }

    public String getName()
    {
        return name;
    }

    public Iterator<City> iterator()
    {
        return new DynTypeIterator<City>(citiesList);
    }

    public String getPrettyCityNames()
    {
        return citiesList.stream().map(cityObj -> cityObj.getCityName()).collect(joining(", "));
    }

    private List<City> cityListFromJsonFile(final String jsonFileName) throws NoJsonFileException
    {
        try (
            BufferedReader breader = Files.newBufferedReader(
                Paths.get(Country.class.getClassLoader().getResource(jsonFileName).toURI())
            )
        )
        {
            final Gson gson = new GsonBuilder().create();
            City[] cityArray = gson.fromJson(breader, City[].class);
            return Arrays.asList(cityArray);
        } catch (IOException | URISyntaxException exc)
        {
            throw new NoJsonFileException("No json file found", exc);
        }
    }
}
