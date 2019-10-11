package info.tomfi.alexa.skills.shabbattimes.api.response;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.google.gson.GsonBuilder;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public final class APIResponseTest {
    @Test
    @DisplayName("test the api json response with minimal values")
    public void responseJson_minimalValues_success() throws IOException, URISyntaxException
    {
        APIResponse response;
        try
        (
            BufferedReader breader = Files.newBufferedReader(
                Paths.get(APIResponse.class.getClassLoader().getResource("response_minimal.json").toURI())
            )
        )
        {
            response = new GsonBuilder().create().fromJson(breader, APIResponse.class);
        }

        APIResponseAssert.assertThat(response)
            .titleIs("testTitle")
            .dateIs("testDate")
            .linkIs("testLink");
    }
}
