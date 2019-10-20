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
import info.tomfi.alexa.skills.shabbattimes.di.DIBreakAPIConfiguration;
import info.tomfi.alexa.skills.shabbattimes.di.DIMockAPIConfiguration;

import lombok.Cleanup;
import lombok.val;

public final class GetCityIntentTest
{
    private static Skill skillInTest;

    @BeforeAll
    public static void initialize() throws BeansException, IllegalAccessException, InstantiationException
    {
        @Cleanup val context = new AnnotationConfigApplicationContext(DIMockAPIConfiguration.class);
        skillInTest = context.getBean(ShabbatTimesSkillCreator.class).getSkill();
    }

    @Test
    @DisplayName("customer select israel city 'rishon lezion' on Tuesday without stating the origin country, start on $ end on $")
    public void testGetCityIntent_Tuesday_IsraelCity_NoCountry() throws IOException, URISyntaxException
    {
        val input = Files.readAllBytes(Paths.get(GetCityIntentTest.class.getClassLoader()
            .getResource("skill-tests/get_city_intent_tuesday_israel_city_no_country.json").toURI())
        );

        val response = skillInTest.execute(new BaseSkillRequest(input));
        assertThat(response)
            .isPresent()
            .sessionIsStillOn()
            .cardTitleIs("Shabbat times: IL-Rishon LeZion")
            .cardTextIs("Starts on 2019-10-04, at 18:04. And ends on 2019-10-05, at 19:11.")
            .sessionAttributesHasKeyWithValue("country", "IL")
            .sessionAttributesHasKeyWithValue("city", "rishon lezion")
            .sessionAttributesHasKeyWithValue("lastIntent", "GetCityIntent")
            .outputSpeechIs("Shabbat times in Rishon LeẔiyyon: starts on friday. 2019-10-04, at 18:04. and ends on saturday. 2019-10-05, at 19:11.Would you like me to get the shabbat times in another city?")
            .repromptSpeechIs("If you're interested in another city, please tell me the city name. For a list of all the possible city names, just ask me for help.");
    }

    @Test
    @DisplayName("customer select city on Tuesday while stating the country 'rishon lezion, israel', start on $ end on $")
    public void testGetCityIntent_Tuesday_IsraelCity_WithCountry() throws IOException, URISyntaxException
    {
        val input = Files.readAllBytes(Paths.get(GetCityIntentTest.class.getClassLoader()
            .getResource("skill-tests/get_city_intent_tuesday_israel_city_with_country.json").toURI())
        );

        val response = skillInTest.execute(new BaseSkillRequest(input));
        assertThat(response)
            .isPresent()
            .sessionIsStillOn()
            .cardTitleIs("Shabbat times: IL-Rishon LeZion")
            .cardTextIs("Starts on 2019-10-04, at 18:04. And ends on 2019-10-05, at 19:11.")
            .sessionAttributesHasKeyWithValue("country", "IL")
            .sessionAttributesHasKeyWithValue("city", "rishon lezion")
            .sessionAttributesHasKeyWithValue("lastIntent", "GetCityIntent")
            .outputSpeechIs("Shabbat times in Rishon LeẔiyyon: starts on friday. 2019-10-04, at 18:04. and ends on saturday. 2019-10-05, at 19:11.Would you like me to get the shabbat times in another city?")
            .repromptSpeechIs("If you're interested in another city, please tell me the city name. For a list of all the possible city names, just ask me for help.");
    }

    @Test
    @DisplayName("customer select israel city 'rishon lezion' on Thursday without stating the origin country, start tommorow end on $")
    public void testGetCityIntent_Thursday_IsraelCity_NoCountry() throws IOException, URISyntaxException
    {
        val input = Files.readAllBytes(Paths.get(GetCityIntentTest.class.getClassLoader()
            .getResource("skill-tests/get_city_intent_thursday_israel_city_no_country.json").toURI())
        );

        val response = skillInTest.execute(new BaseSkillRequest(input));
        assertThat(response)
            .isPresent()
            .sessionIsStillOn()
            .cardTitleIs("Shabbat times: IL-Rishon LeZion")
            .cardTextIs("Starts on 2019-10-04, at 18:04. And ends on 2019-10-05, at 19:11.")
            .sessionAttributesHasKeyWithValue("country", "IL")
            .sessionAttributesHasKeyWithValue("city", "rishon lezion")
            .sessionAttributesHasKeyWithValue("lastIntent", "GetCityIntent")
            .outputSpeechIs("Shabbat times in Rishon LeẔiyyon: starts tomorrow. 2019-10-04, at 18:04. and ends on saturday. 2019-10-05, at 19:11.Would you like me to get the shabbat times in another city?")
            .repromptSpeechIs("If you're interested in another city, please tell me the city name. For a list of all the possible city names, just ask me for help.");
    }

    @Test
    @DisplayName("customer select israel city 'rishon lezion' on Friday without stating the origin country, start today end tomorrow")
    public void testGetCityIntent_Friday_IsraelCity_NoCountry() throws IOException, URISyntaxException
    {
        val input = Files.readAllBytes(Paths.get(GetCityIntentTest.class.getClassLoader()
            .getResource("skill-tests/get_city_intent_friday_israel_city_no_country.json").toURI())
        );

        val response = skillInTest.execute(new BaseSkillRequest(input));
        assertThat(response)
            .isPresent()
            .sessionIsStillOn()
            .cardTitleIs("Shabbat times: IL-Rishon LeZion")
            .cardTextIs("Starts on 2019-10-04, at 18:04. And ends on 2019-10-05, at 19:11.")
            .sessionAttributesHasKeyWithValue("country", "IL")
            .sessionAttributesHasKeyWithValue("city", "rishon lezion")
            .sessionAttributesHasKeyWithValue("lastIntent", "GetCityIntent")
            .outputSpeechIs("Shabbat times in Rishon LeẔiyyon: starts today. 2019-10-04, at 18:04. and ends tomorrow. 2019-10-05, at 19:11.Would you like me to get the shabbat times in another city?")
            .repromptSpeechIs("If you're interested in another city, please tell me the city name. For a list of all the possible city names, just ask me for help.");
    }

    @Test
    @DisplayName("customer select israel city 'rishon lezion' on Saturday without stating the origin country, start yesterday end today")
    public void testGetCityIntent_Saturday_IsraelCity_NoCountry() throws IOException, URISyntaxException
    {
        val input = Files.readAllBytes(Paths.get(GetCityIntentTest.class.getClassLoader()
            .getResource("skill-tests/get_city_intent_saturday_israel_city_no_country.json").toURI())
        );

        val response = skillInTest.execute(new BaseSkillRequest(input));
        assertThat(response)
            .isPresent()
            .sessionIsStillOn()
            .cardTitleIs("Shabbat times: IL-Rishon LeZion")
            .cardTextIs("Starts on 2019-10-04, at 18:04. And ends on 2019-10-05, at 19:11.")
            .sessionAttributesHasKeyWithValue("country", "IL")
            .sessionAttributesHasKeyWithValue("city", "rishon lezion")
            .sessionAttributesHasKeyWithValue("lastIntent", "GetCityIntent")
            .outputSpeechIs("Shabbat times in Rishon LeẔiyyon: started yesterday. 2019-10-04, at 18:04. and ends today. 2019-10-05, at 19:11.Would you like me to get the shabbat times in another city?")
            .repromptSpeechIs("If you're interested in another city, please tell me the city name. For a list of all the possible city names, just ask me for help.");
    }

    @Test
    @DisplayName("test exception handling with a requested date that is later then the date in the mocked response")
    public void testGetCityIntent_requesteDate_after_responseDate() throws IOException, URISyntaxException
    {
        val input = Files.readAllBytes(Paths.get(GetCityIntentTest.class.getClassLoader()
            .getResource("skill-tests/get_city_intent_request_date_after_response_date.json").toURI())
        );

        val response = skillInTest.execute(new BaseSkillRequest(input));
        assertThat(response)
            .isPresent()
            .sessionIsOver()
            .cardIsAbsent()
            .repromptIsAbsent()
            .sessionAttributesHasKeyWithValue("country", "IL")
            .sessionAttributesHasKeyWithValue("city", "rishon lezion")
            .outputSpeechIs("I'm sorry. Something went wrong. I'm doing my best to resolve this issue. Please try again later. goodbye.");
    }

    @Test
    @DisplayName("test exception handling when hebcal api is not responding")
    public void testGetCityIntent_hebcalApi_notResponding()
        throws BeansException, IllegalAccessException, InstantiationException, IOException, URISyntaxException
    {
        @Cleanup val breakApiContext = new AnnotationConfigApplicationContext(DIBreakAPIConfiguration.class);
        val breakApiSkill = breakApiContext.getBean(ShabbatTimesSkillCreator.class).getSkill();

        val input = Files.readAllBytes(Paths.get(GetCityIntentTest.class.getClassLoader()
            .getResource("skill-tests/get_city_intent_tuesday_israel_city_no_country.json").toURI())
        );

        val response = breakApiSkill.execute(new BaseSkillRequest(input));
        assertThat(response)
            .isPresent()
            .sessionIsOver()
            .cardIsAbsent()
            .repromptIsAbsent()
            .sessionAttributesHasKeyWithValue("country", "IL")
            .sessionAttributesHasKeyWithValue("city", "rishon lezion")
            .outputSpeechIs("I'm sorry. Something went wrong. I'm doing my best to resolve this issue. Please try again later. goodbye.");
    }
}
