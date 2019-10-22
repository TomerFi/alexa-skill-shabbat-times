package info.tomfi.alexa.skills.shabbattimes.city;

import static info.tomfi.alexa.skills.shabbattimes.assertions.Assertions.assertThat;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.google.gson.GsonBuilder;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import lombok.Cleanup;
import lombok.val;

public final class CityTest
{
    @Test
    @DisplayName("test the creation of the city pojo from a json file")
    public void cityJsonToObject_testJsonFile_validateValues() throws IOException, URISyntaxException
    {
        @Cleanup val breader = Files.newBufferedReader(
            Paths.get(Thread.currentThread().getContextClassLoader().getResource("cities/TST_City1.json").toURI())
        );

        val city = new GsonBuilder().create().fromJson(breader, City.class);

        assertThat(city)
            .cityNameIs("testCity1")
            .geoNameIs("TST-testCity1")
            .geoIdIs(1234567)
            .countryAbbreviationIs("TST");

        assertThat(city.iterator()).toIterable().containsExactlyInAnyOrder("testCity1", "city1", "firstcity");
    }
}
