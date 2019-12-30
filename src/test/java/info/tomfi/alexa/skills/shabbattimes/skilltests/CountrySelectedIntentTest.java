/**
 * Copyright 2019 Tomer Figenblat
 *
 * <p>Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of the License at
 *
 * <p>http://www.apache.org/licenses/LICENSE-2.0
 *
 * <p>Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing permissions and
 * limitations under the License.
 */
package info.tomfi.alexa.skills.shabbattimes.skilltests;

import static info.tomfi.alexa.skills.shabbattimes.assertions.Assertions.assertThat;

import com.amazon.ask.Skill;
import com.amazon.ask.request.impl.BaseSkillRequest;
import info.tomfi.alexa.skills.shabbattimes.ShabbatTimesSkillCreator;
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

public final class CountrySelectedIntentTest {
  private static Skill skillInTest;

  @BeforeAll
  public static void initialize()
      throws BeansException, IllegalAccessException, InstantiationException {
    @Cleanup val context = new AnnotationConfigApplicationContext(DiMockApiConfiguration.class);
    skillInTest = context.getBean(ShabbatTimesSkillCreator.class).getSkill();
  }

  @Test
  @DisplayName("customer requests 'israel' as the country for listing the supported cities from")
  public void testCountrySelectedIntentIsrael() throws IOException, URISyntaxException {
    val input =
        Files.readAllBytes(
            Paths.get(
                Thread.currentThread()
                    .getContextClassLoader()
                    .getResource("skill-tests/country_selected_intent_israel.json")
                    .toURI()));
    val response = skillInTest.execute(new BaseSkillRequest(input));

    assertThat(response)
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
  public void testCountrySelectedIntentEngland() throws IOException, URISyntaxException {
    val input =
        Files.readAllBytes(
            Paths.get(
                Thread.currentThread()
                    .getContextClassLoader()
                    .getResource("skill-tests/country_selected_intent_england.json")
                    .toURI()));
    val response = skillInTest.execute(new BaseSkillRequest(input));

    assertThat(response)
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
  @DisplayName(
      "customer requests 'the united states' as the country for listing the supported cities from")
  public void testCountrySelectedIntentUnitesStates() throws IOException, URISyntaxException {
    val input =
        Files.readAllBytes(
            Paths.get(
                Thread.currentThread()
                    .getContextClassLoader()
                    .getResource("skill-tests/country_selected_intent_united_states.json")
                    .toURI()));
    val response = skillInTest.execute(new BaseSkillRequest(input));

    assertThat(response)
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
  @DisplayName("test exception handling when no country value was provided with the request")
  public void testCountrySelectedIntent_nullValue() throws IOException, URISyntaxException {
    val input =
        Files.readAllBytes(
            Paths.get(
                Thread.currentThread()
                    .getContextClassLoader()
                    .getResource("skill-tests/country_selected_intent_null_country.json")
                    .toURI()));
    val response = skillInTest.execute(new BaseSkillRequest(input));

    assertThat(response)
        .isPresent()
        .sessionIsStillOn()
        .cardIsAbsent()
        .sessionAttributesAreAbsent()
        .outputSpeechIs(
            "I'm sorry. The only countries I know are the United States, the United Kingdom, and Israel. Please repeat the country name. For a list of all the possible city names, just ask me for help.")
        .repromptSpeechIs(
            "Please tell me the requested city name. For a list of all the possible city names, just ask me for help.");
  }

  @Test
  @DisplayName("test exception handling when unknown country value was provided with the request")
  public void testCountrySelectedIntent_unknownCountry() throws IOException, URISyntaxException {
    val input =
        Files.readAllBytes(
            Paths.get(
                Thread.currentThread()
                    .getContextClassLoader()
                    .getResource("skill-tests/country_selected_intent_unknown_country.json")
                    .toURI()));
    val response = skillInTest.execute(new BaseSkillRequest(input));

    assertThat(response)
        .isPresent()
        .sessionIsStillOn()
        .cardIsAbsent()
        .sessionAttributesAreAbsent()
        .outputSpeechIs("I'm sorry. I haven't heard of Fakeland. Please try again.")
        .repromptSpeechIs("Please try again, if you want, you can ask me for help.");
  }
}
