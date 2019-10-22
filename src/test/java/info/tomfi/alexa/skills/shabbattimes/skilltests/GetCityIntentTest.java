/**
 * Copyright 2019 Tomer Figenblat
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package info.tomfi.alexa.skills.shabbattimes.skilltests;

import static info.tomfi.alexa.skills.shabbattimes.assertions.Assertions.assertThat;

import com.amazon.ask.Skill;
import com.amazon.ask.request.impl.BaseSkillRequest;

import info.tomfi.alexa.skills.shabbattimes.ShabbatTimesSkillCreator;
import info.tomfi.alexa.skills.shabbattimes.di.DiBreakApiConfiguration;
import info.tomfi.alexa.skills.shabbattimes.di.DiMockApiConfiguration;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

import lombok.Cleanup;
import lombok.val;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.BeansException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public final class GetCityIntentTest
{
    private static Skill skillInTest;

    @BeforeAll
    public static void initialize() throws BeansException, IllegalAccessException, InstantiationException
    {
        @Cleanup val context = new AnnotationConfigApplicationContext(DiMockApiConfiguration.class);
        skillInTest = context.getBean(ShabbatTimesSkillCreator.class).getSkill();
    }

    @Test
    @DisplayName("customer select israel city 'rishon lezion' on Tuesday without stating the origin country, start on $ end on $")
    public void testGetCityIntent_Tuesday_IsraelCity_NoCountry() throws IOException, URISyntaxException
    {
        val input = Files.readAllBytes(Paths.get(Thread.currentThread().getContextClassLoader()
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
            .outputSpeechIs("Shabbat times in Rishon LeẔiyyon: starts on friday. 2019-10-04, at 18:04. and ends on saturday. 2019-10-05, at 19:11.Would you like me to get the Shabbat times in another city?")
            .repromptSpeechIs("If you're interested in another city, please tell me the city name. For a list of all the possible city names, just ask me for help.");
    }

    @Test
    @DisplayName("customer select city on Tuesday while stating the country 'rishon lezion, israel', start on $ end on $")
    public void testGetCityIntent_Tuesday_IsraelCity_WithCountry() throws IOException, URISyntaxException
    {
        val input = Files.readAllBytes(Paths.get(Thread.currentThread().getContextClassLoader()
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
            .outputSpeechIs("Shabbat times in Rishon LeẔiyyon: starts on friday. 2019-10-04, at 18:04. and ends on saturday. 2019-10-05, at 19:11.Would you like me to get the Shabbat times in another city?")
            .repromptSpeechIs("If you're interested in another city, please tell me the city name. For a list of all the possible city names, just ask me for help.");
    }

    @Test
    @DisplayName("customer select israel city 'rishon lezion' on Thursday without stating the origin country, start tommorow end on $")
    public void testGetCityIntent_Thursday_IsraelCity_NoCountry() throws IOException, URISyntaxException
    {
        val input = Files.readAllBytes(Paths.get(Thread.currentThread().getContextClassLoader()
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
            .outputSpeechIs("Shabbat times in Rishon LeẔiyyon: starts tomorrow. 2019-10-04, at 18:04. and ends on saturday. 2019-10-05, at 19:11.Would you like me to get the Shabbat times in another city?")
            .repromptSpeechIs("If you're interested in another city, please tell me the city name. For a list of all the possible city names, just ask me for help.");
    }

    @Test
    @DisplayName("customer select israel city 'rishon lezion' on Friday without stating the origin country, start today end tomorrow")
    public void testGetCityIntent_Friday_IsraelCity_NoCountry() throws IOException, URISyntaxException
    {
        val input = Files.readAllBytes(Paths.get(Thread.currentThread().getContextClassLoader()
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
            .outputSpeechIs("Shabbat times in Rishon LeẔiyyon: starts today. 2019-10-04, at 18:04. and ends tomorrow. 2019-10-05, at 19:11.Would you like me to get the Shabbat times in another city?")
            .repromptSpeechIs("If you're interested in another city, please tell me the city name. For a list of all the possible city names, just ask me for help.");
    }

    @Test
    @DisplayName("customer select israel city 'rishon lezion' on Saturday without stating the origin country, start yesterday end today")
    public void testGetCityIntent_Saturday_IsraelCity_NoCountry() throws IOException, URISyntaxException
    {
        val input = Files.readAllBytes(Paths.get(Thread.currentThread().getContextClassLoader()
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
            .outputSpeechIs("Shabbat times in Rishon LeẔiyyon: started yesterday. 2019-10-04, at 18:04. and ends today. 2019-10-05, at 19:11.Would you like me to get the Shabbat times in another city?")
            .repromptSpeechIs("If you're interested in another city, please tell me the city name. For a list of all the possible city names, just ask me for help.");
    }

    @Test
    @DisplayName("test exception handling with a requested date that is later then the date in the mocked response")
    public void testGetCityIntent_requesteDate_after_responseDate() throws IOException, URISyntaxException
    {
        val input = Files.readAllBytes(Paths.get(Thread.currentThread().getContextClassLoader()
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
            .outputSpeechIs("I'm sorry. Something went wrong. I'm doing my best to resolve this issue. Please try again later. Goodbye.");
    }

    @Test
    @DisplayName("test exception handling when unknown city and no country selcted")
    public void testGetCityIntent_unknownCityNoCountry() throws IOException, URISyntaxException
    {
        val input = Files.readAllBytes(Paths.get(Thread.currentThread().getContextClassLoader()
            .getResource("skill-tests/get_city_intent_tuesday_unknown_city_no_country.json").toURI())
        );

        val response = skillInTest.execute(new BaseSkillRequest(input));
        assertThat(response)
            .isPresent()
            .sessionIsStillOn()
            .cardIsAbsent()
            .sessionAttributesAreAbsent()
            .outputSpeechIs("I'm sorry. I can't seem to find your requested city. Please repeat the city name. For a list of all the possible city names, just ask me for help.")
            .repromptSpeechIs("Please tell me the requested city name. For a list of all the possible city names, just ask me for help.");
    }

    @Test
    @DisplayName("test exception handling when unknown city and  a selected country")
    public void testGetCityIntent_unknownCityWithCountry() throws IOException, URISyntaxException
    {
        val input = Files.readAllBytes(Paths.get(Thread.currentThread().getContextClassLoader()
            .getResource("skill-tests/get_city_intent_tuesday_unknown_city_with_country.json").toURI())
        );

        val response = skillInTest.execute(new BaseSkillRequest(input));
        assertThat(response)
            .isPresent()
            .sessionIsStillOn()
            .cardIsAbsent()
            .sessionAttributesAreAbsent()
            .outputSpeechIs("I'm sorry. I can't seem to find your requested city. Please repeat the city name. For a list of all the possible city names, just ask me for help.")
            .repromptSpeechIs("Please tell me the requested city name. For a list of all the possible city names, just ask me for help.");
    }

    @Test
    @DisplayName("test exception handling when no city slot was provided with the request")
    public void testGetCityIntent_noCitySlot() throws IOException, URISyntaxException
    {
        val input = Files.readAllBytes(Paths.get(Thread.currentThread().getContextClassLoader()
            .getResource("skill-tests/get_city_intent_tuesday_no_city_slot.json").toURI())
        );

        val response = skillInTest.execute(new BaseSkillRequest(input));
        assertThat(response)
            .isPresent()
            .sessionIsStillOn()
            .cardIsAbsent()
            .sessionAttributesAreAbsent()
            .outputSpeechIs("I'm sorry. I can't seem to find your requested city. Please repeat the city name. For a list of all the possible city names, just ask me for help.")
            .repromptSpeechIs("Please tell me the requested city name. For a list of all the possible city names, just ask me for help.");
    }

    @Test
    @DisplayName("test exception handling when hebcal api is not responding")
    public void testGetCityIntent_hebcalApi_notResponding()
        throws BeansException, IllegalAccessException, InstantiationException, IOException, URISyntaxException
    {
        @Cleanup val breakApiContext = new AnnotationConfigApplicationContext(DiBreakApiConfiguration.class);
        val breakApiSkill = breakApiContext.getBean(ShabbatTimesSkillCreator.class).getSkill();

        val input = Files.readAllBytes(Paths.get(Thread.currentThread().getContextClassLoader()
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
            .outputSpeechIs("I'm sorry. Something went wrong. I'm doing my best to resolve this issue. Please try again later. Goodbye.");
    }
}
