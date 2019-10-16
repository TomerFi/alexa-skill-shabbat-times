package info.tomfi.alexa.skills.shabbattimes.api.response.items;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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
        ResponseItem item;
        try (
            BufferedReader breader = Files.newBufferedReader(
                Paths.get(loader.getResource("api-responses/response_item_mandatory.json").toURI())
            )
        )
        {
            item = gson.fromJson(breader, ResponseItem.class);
        }
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
        ResponseItem item;
        try (
            BufferedReader breader = Files.newBufferedReader(
                Paths.get(loader.getResource("api-responses/response_item_optional.json").toURI())
            )
        )
        {
            item = gson.fromJson(breader, ResponseItem.class);
        }
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
        ResponseItem item;
        try (
            BufferedReader breader = Files.newBufferedReader(
                Paths.get(loader.getResource("api-responses/response_item_full.json").toURI())
            )
        )
        {
            item = gson.fromJson(breader, ResponseItem.class);
        }
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
