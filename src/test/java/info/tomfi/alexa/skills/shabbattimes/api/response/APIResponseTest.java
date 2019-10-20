package info.tomfi.alexa.skills.shabbattimes.api.response;

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

public final class APIResponseTest {
    @Test
    @DisplayName("test the api json response with minimal values")
    public void responseJson_minimalValues_success() throws IOException, URISyntaxException
    {
        @Cleanup val breader = Files.newBufferedReader(
            Paths.get(APIResponse.class.getClassLoader().getResource("api-responses/response_minimal.json").toURI())
        );

        val response = new GsonBuilder().create().fromJson(breader, APIResponse.class);

        assertThat(response)
            .titleIs("testTitle")
            .dateIs("testDate")
            .linkIs("testLink");
    }
}
