package info.tomfi.alexa.skills.shabbattimes.city;

import static lombok.AccessLevel.PRIVATE;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import info.tomfi.alexa.skills.shabbattimes.country.Country;
import info.tomfi.alexa.skills.shabbattimes.exception.NoJsonFileException;
import lombok.Cleanup;
import lombok.NoArgsConstructor;
import lombok.val;

@NoArgsConstructor(access = PRIVATE)
public final class CitiesFactory
{
    private static final Gson gsonParser = new GsonBuilder().create();
    private static final Map<Country, List<City>> citiesPool = new ConcurrentHashMap<>();

    public static List<City> getCities(final Country country, final String jsonFileName)
    {
        return citiesPool.computeIfAbsent(
            country, setCountry ->
            {
                return cityListFromJsonFile(jsonFileName);
            }
        );
    }

    private static List<City> cityListFromJsonFile(final String jsonFileName) throws NoJsonFileException {
        try
        {
            @Cleanup val breader =
                Files.newBufferedReader(Paths.get(Country.class.getClassLoader().getResource(jsonFileName).toURI())
            );
            val cityArray = gsonParser.fromJson(breader, City[].class);
            return Arrays.asList(cityArray);
        } catch (IOException | NullPointerException | URISyntaxException exc)
        {
            throw new NoJsonFileException("No json file found", exc);
        }
    }
}
