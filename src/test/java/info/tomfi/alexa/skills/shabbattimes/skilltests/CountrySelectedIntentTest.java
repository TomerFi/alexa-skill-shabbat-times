package info.tomfi.alexa.skills.shabbattimes.skilltests;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.amazon.ask.Skill;
import com.amazon.ask.request.impl.BaseSkillRequest;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeansException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import info.tomfi.alexa.skills.shabbattimes.ShabbatTimesSkillCreator;
import info.tomfi.alexa.skills.shabbattimes.tools.DITestingConfiguration;
import lombok.Cleanup;
import lombok.val;

public final class CountrySelectedIntentTest
{
    private static Skill skillInTest;

    @BeforeAll
    public static void initialize() throws BeansException, IllegalAccessException, InstantiationException
    {
        @Cleanup val context = new AnnotationConfigApplicationContext(DITestingConfiguration.class);
        skillInTest = context.getBean(ShabbatTimesSkillCreator.class).getSkill();
    }

    @Test
    @DisplayName("customer requests 'israel' as the country for listing the supported cities from")
    public void testCountrySelectedIntentIsrael() throws IOException, URISyntaxException
    {
        val input = Files.readAllBytes(Paths.get(CountrySelectedIntentTest.class.getClassLoader()
                .getResource("skill-tests/country_selected_intent_israel.json").toURI())
        );
        val response = skillInTest.execute(new BaseSkillRequest(input));

        SkillResponseAssert.assertThat(response)
            .isPresent()
            .sessionIsStillOn()
            .cardIsAbsent()
            .sessionAttributesHasKeyWithValue("country", "IL")
            .sessionAttributesHasKeyWithValue("lastIntent", "CountrySelected")
            .outputSpeechStartsWith("These are the city names I know in Israel:")
            .outputSpeechEndsWith("Was your city on this list?")
            .repromptSpeechIs("Please tell me the requested city name.");
    }

    @Test
    @DisplayName("customer requests 'england' as the country for listing the supported cities from")
    public void testCountrySelectedIntentEngland() throws IOException, URISyntaxException
    {
        val input = Files.readAllBytes(Paths.get(CountrySelectedIntentTest.class.getClassLoader()
                .getResource("skill-tests/country_selected_intent_england.json").toURI())
        );
        val response = skillInTest.execute(new BaseSkillRequest(input));

        SkillResponseAssert.assertThat(response)
            .isPresent()
            .sessionIsStillOn()
            .cardIsAbsent()
            .sessionAttributesHasKeyWithValue("country", "GB")
            .sessionAttributesHasKeyWithValue("lastIntent", "CountrySelected")
            .outputSpeechStartsWith("These are the city names I know in the United Kingdom:")
            .outputSpeechEndsWith("Was your city on this list?")
            .repromptSpeechIs("Please tell me the requested city name.");
    }

    @Test
    @DisplayName("customer requests 'the united states' as the country for listing the supported cities from")
    public void testCountrySelectedIntentUnitesStates() throws IOException, URISyntaxException
    {
        val input = Files.readAllBytes(Paths.get(CountrySelectedIntentTest.class.getClassLoader()
                .getResource("skill-tests/country_selected_intent_united_states.json").toURI())
        );
        val response = skillInTest.execute(new BaseSkillRequest(input));

        SkillResponseAssert.assertThat(response)
            .isPresent()
            .sessionIsStillOn()
            .cardIsAbsent()
            .sessionAttributesHasKeyWithValue("country", "US")
            .sessionAttributesHasKeyWithValue("lastIntent", "CountrySelected")
            .outputSpeechStartsWith("These are the city names I know in the United States:")
            .outputSpeechEndsWith("Was your city on this list?")
            .repromptSpeechIs("Please tell me the requested city name.");
    }

    @Test
    @DisplayName("test error case when not country value was supplied")
    public void testCountrySelectedIntentNullValue() throws IOException, URISyntaxException
    {
        val input = Files.readAllBytes(Paths.get(CountrySelectedIntentTest.class.getClassLoader()
                .getResource("skill-tests/country_selected_intent_null_value.json").toURI())
        );
        val response = skillInTest.execute(new BaseSkillRequest(input));

        SkillResponseAssert.assertThat(response)
            .isPresent()
            .sessionIsStillOn()
            .cardIsAbsent()
            .sessionAttributesAreAbsent()
            .outputSpeechIs("I'm sorry. The only countries I know are the United States, the United Kingdom, and Israel. Please repeat the country name. For a list of all the possible city names, just ask me for help.")
            .repromptSpeechIs("Please tell me the requested city name. For a list of all the possible city names, just ask me for help.");
    }
}
