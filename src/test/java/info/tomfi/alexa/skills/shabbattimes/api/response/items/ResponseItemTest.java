package info.tomfi.alexa.skills.shabbattimes.api.response.items;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import lombok.Cleanup;
import lombok.val;

public final class ResponseItemTest
{
    private static Gson gson;
    private static ClassLoader loader;

    @BeforeAll
    public static void initialize()
    {
        gson = new GsonBuilder().create();
        loader = ResponseItemTest.class.getClassLoader();
    }

    @Test
    @DisplayName("test response item json to object with mandatory values only")
    public void itemJsonToObject_mandatoryKeys_success() throws IOException, URISyntaxException
    {
        @Cleanup val breader = Files.newBufferedReader(
            Paths.get(loader.getResource("api-responses/response_item_mandatory.json").toURI())
        );

        val item = gson.fromJson(breader, ResponseItem.class);

        ResponseItemAssert.assertThat(item)
            .titleIs("testTitle")
            .categoryIs("testCategory")
            .dateIs("testDate")
            .hebrewIs("testHebrew")
            .linkIsEmpty()
            .memoIsEmpty()
            .subcatIsEmpty()
            .isNotYomtov();
    }

    @Test
    @DisplayName("test response item json to object with mandatory and optional values")
    public void itemJsonToObject_optionalKeys_success() throws IOException, URISyntaxException
    {
        @Cleanup val breader = Files.newBufferedReader(
            Paths.get(loader.getResource("api-responses/response_item_optional.json").toURI())
        );

        val item = gson.fromJson(breader, ResponseItem.class);

        ResponseItemAssert.assertThat(item)
            .titleIs("testTitle")
            .categoryIs("testCategory")
            .dateIs("testDate")
            .hebrewIs("testHebrew")
            .linkIs("testLink")
            .memoIs("testMemo")
            .subcatIs("testSubcat")
            .isNotYomtov();
    }

    @Test
    @DisplayName("test response item json to object with mandatory, optional and yomtov values")
    public void itemJsonToObject_fullKeys_success() throws IOException, URISyntaxException
    {
        @Cleanup val breader = Files.newBufferedReader(
            Paths.get(loader.getResource("api-responses/response_item_full.json").toURI())
        );

        val item = gson.fromJson(breader, ResponseItem.class);

        ResponseItemAssert.assertThat(item)
            .titleIs("testTitle")
            .categoryIs("testCategory")
            .dateIs("testDate")
            .hebrewIs("testHebrew")
            .linkIs("testLink")
            .memoIs("testMemo")
            .subcatIs("testSubcat")
            .isYomtov();
    }
}
