package info.tomfi.alexa.skills.shabbattimes.city;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.google.gson.GsonBuilder;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public final class CityTest
{
    @Test
    @DisplayName("test the creation of the city pojo from a json file")
    public void cityJsonToObject_testJsonFile_validateValues() throws IOException, URISyntaxException
    {
        City city;
        try (
            BufferedReader breader = Files.newBufferedReader(
                Paths.get(CityTest.class.getClassLoader().getResource("cities/TST_City1.json").toURI())
            )
        )
        {
            city = new GsonBuilder().create().fromJson(breader, City.class);
        }
        CityAssert.assertThat(city)
            .cityNameIs("testCity1")
            .geoNameIs("TST-testCity1")
            .geoIdIs(1234567)
            .countryAbbreviationIs("TST");

        assertThat(city.iterator()).toIterable().containsExactlyInAnyOrder("testCity1", "city1", "firstcity");
    }
}
