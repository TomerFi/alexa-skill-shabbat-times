package info.tomfi.alexa.skills.shabbattimes.api.response.items;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.google.gson.GsonBuilder;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public final class ResponseLocationTest
{
    @Test
    @DisplayName("test response location json to object")
    public void jsonToObject_location_success() throws IOException, URISyntaxException
    {
        ResponseLocation location;
        try (
            BufferedReader breader = Files.newBufferedReader(
                Paths.get(ResponseLocationTest.class.getClassLoader().getResource("response_location.json").toURI())
            )
        )
        {
            location = new GsonBuilder().create().fromJson(breader, ResponseLocation.class);
        }
        ResponseLocationAssert.assertThat(location)
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
