package info.tomfi.alexa.skills.shabbattimes.api.response.items;

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

public final class ResponseLocationTest
{
    @Test
    @DisplayName("test response location json to object")
    public void jsonToObject_location_success() throws IOException, URISyntaxException
    {
        @Cleanup val breader = Files.newBufferedReader(
            Paths.get(Thread.currentThread().getContextClassLoader().getResource("api-responses/response_location.json").toURI())
        );

        val location = new GsonBuilder().create().fromJson(breader, ResponseLocation.class);

        assertThat(location)
            .latitudeIs(25.25)
            .longitudeIs(26.26)
            .geonameidIs(2526)
            .cityIs("testCity")
            .asciinameIs("testAsciiname")
            .titleIs("testTitle")
            .tzidIs("testTzid")
            .admin1Is("testAdmin1")
            .countryIs("testCountry")
            .geoIs("testGeo");
    }
}
