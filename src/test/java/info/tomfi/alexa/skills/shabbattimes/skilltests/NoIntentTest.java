package info.tomfi.alexa.skills.shabbattimes.skilltests;

import static info.tomfi.alexa.skills.shabbattimes.assertions.Assertions.assertThat;

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
import info.tomfi.alexa.skills.shabbattimes.di.DiMockAPIConfiguration;

import lombok.Cleanup;
import lombok.val;

public final class NoIntentTest
{
    private static Skill skillInTest;

    @BeforeAll
    public static void initialize() throws BeansException, IllegalAccessException, InstantiationException
    {
        @Cleanup val context = new AnnotationConfigApplicationContext(DiMockAPIConfiguration.class);
        skillInTest = context.getBean(ShabbatTimesSkillCreator.class).getSkill();
    }

    @Test
    @DisplayName("customer reply 'no'")
    public void testNoIntent() throws IOException, URISyntaxException
    {
        val input = Files.readAllBytes(Paths.get(Thread.currentThread().getContextClassLoader()
                .getResource("skill-tests/no_intent.json").toURI())
        );
        val response = skillInTest.execute(new BaseSkillRequest(input));

        assertThat(response)
            .isPresent()
            .sessionIsOver()
            .cardIsAbsent()
            .sessionAttributesAreAbsent()
            .repromptIsAbsent()
            .outputSpeechIs("Ok.");
    }

    @Test
    @DisplayName("customer reply 'no' after selecting israel as the country to list the cities from")
    public void testNoIntentAfterCountrySelectedIntentIL() throws IOException, URISyntaxException
    {
        val input = Files.readAllBytes(Paths.get(Thread.currentThread().getContextClassLoader()
                .getResource("skill-tests/no_intent_after_country_selected_il.json").toURI())
        );
        val response = skillInTest.execute(new BaseSkillRequest(input));

        assertThat(response)
            .isPresent()
            .sessionIsOver()
            .cardIsAbsent()
            .repromptIsAbsent()
            .sessionAttributesHasKeyWithValue("country", "IL")
            .sessionAttributesHasKeyWithValue("lastIntent", "CountrySelected")
            .outputSpeechIs("I'm sorry. Those are all the city names I know in Israel! Please try again later, Goodbye!");
    }

    @Test
    @DisplayName("customer reply 'no' after selecting the united states as the country to list the cities from")
    public void testNoIntentAfterCountrySelectedIntentUS() throws IOException, URISyntaxException
    {
        val input = Files.readAllBytes(Paths.get(Thread.currentThread().getContextClassLoader()
                .getResource("skill-tests/no_intent_after_country_selected_us.json").toURI())
        );
        val response = skillInTest.execute(new BaseSkillRequest(input));

        assertThat(response)
            .isPresent()
            .sessionIsOver()
            .cardIsAbsent()
            .repromptIsAbsent()
            .sessionAttributesHasKeyWithValue("country", "US")
            .sessionAttributesHasKeyWithValue("lastIntent", "CountrySelected")
            .outputSpeechIs("I'm sorry. Those are all the city names I know in the United States! Please try again later, Goodbye!");
    }

    @Test
    @DisplayName("customer reply 'no' after selecting england as the country to list the cities from")
    public void testNoIntentAfterCountrySelectedIntentGB() throws IOException, URISyntaxException
    {
        val input = Files.readAllBytes(Paths.get(Thread.currentThread().getContextClassLoader()
                .getResource("skill-tests/no_intent_after_country_selected_gb.json").toURI())
        );
        val response = skillInTest.execute(new BaseSkillRequest(input));

        assertThat(response)
            .isPresent()
            .sessionIsOver()
            .cardIsAbsent()
            .repromptIsAbsent()
            .sessionAttributesHasKeyWithValue("country", "GB")
            .sessionAttributesHasKeyWithValue("lastIntent", "CountrySelected")
            .outputSpeechIs("I'm sorry. Those are all the city names I know in the United Kingdom! Please try again later, Goodbye!");
    }

    @Test
    @DisplayName("customer reply 'no' after selecting an unknown as the country to list the cities from")
    public void testNoIntentAfterCountrySelectedIntentUknown() throws IOException, URISyntaxException
    {
        val input = Files.readAllBytes(Paths.get(Thread.currentThread().getContextClassLoader()
                .getResource("skill-tests/no_intent_after_country_selected_er.json").toURI())
        );
        val response = skillInTest.execute(new BaseSkillRequest(input));

        assertThat(response)
            .isPresent()
            .sessionIsOver()
            .cardIsAbsent()
            .repromptIsAbsent()
            .sessionAttributesHasKeyWithValue("country", "unknown")
            .sessionAttributesHasKeyWithValue("lastIntent", "CountrySelected")
            .outputSpeechIs("I'm sorry. Those are all the city names I know ! Please try again later, Goodbye!");
    }
}
