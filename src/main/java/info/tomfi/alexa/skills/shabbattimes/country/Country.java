package info.tomfi.alexa.skills.shabbattimes.country;

import static java.util.stream.Collectors.joining;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import com.google.gson.GsonBuilder;

import info.tomfi.alexa.skills.shabbattimes.city.City;
import info.tomfi.alexa.skills.shabbattimes.enums.CountryInfo;
import info.tomfi.alexa.skills.shabbattimes.exception.NoJsonFileException;
import info.tomfi.alexa.skills.shabbattimes.tools.DynTypeIterator;
import lombok.Cleanup;
import lombok.Getter;
import lombok.val;

public final class Country implements Iterable<City>
{
    @Getter private final String abbreviation;
    @Getter private final String name;

    private final List<City> citiesList;

    protected Country(final CountryInfo country) throws NoJsonFileException {
        abbreviation = country.getAbbreviation();
        name = country.getName();
        citiesList = cityListFromJsonFile(String.format("cities/%s_Cities.json", abbreviation));
    }

    public Iterator<City> iterator() {
        return new DynTypeIterator<City>(citiesList);
    }

    public String getPrettyCityNames() {
        return citiesList.stream().map(cityObj -> cityObj.getCityName()).collect(joining(", "));
    }

    private List<City> cityListFromJsonFile(final String jsonFileName) throws NoJsonFileException {
        try
        {
            @Cleanup val breader =
                Files.newBufferedReader(Paths.get(Country.class.getClassLoader().getResource(jsonFileName).toURI())
            );
            val cityArray = new GsonBuilder().create().fromJson(breader, City[].class);
            return Arrays.asList(cityArray);
        } catch (IOException | NullPointerException | URISyntaxException exc)
        {
            throw new NoJsonFileException("No json file found", exc);
        }
    }
}
