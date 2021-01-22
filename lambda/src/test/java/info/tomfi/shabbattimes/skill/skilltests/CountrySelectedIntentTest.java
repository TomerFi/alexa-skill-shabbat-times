/**
 * Copyright Tomer Figenblat
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package info.tomfi.shabbattimes.skill.skilltests;

import static info.tomfi.shabbattimes.skill.assertions.Assertions.assertThat;

import com.amazon.ask.Skill;
import com.amazon.ask.model.ResponseEnvelope;
import com.amazon.ask.request.impl.BaseSkillRequest;
import com.amazon.ask.response.SkillResponse;
import info.tomfi.shabbattimes.skill.ShabbatTimesSkillCreator;
import info.tomfi.shabbattimes.skill.di.DiMockApiConfiguration;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeansException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public final class CountrySelectedIntentTest {
  private static Skill skillInTest;
  private static AnnotationConfigApplicationContext context;


  @BeforeAll
  public static void initialize()
      throws BeansException, IllegalAccessException, InstantiationException {
    context = new AnnotationConfigApplicationContext(DiMockApiConfiguration.class);
    skillInTest = context.getBean(ShabbatTimesSkillCreator.class).getSkill();
  }

  @AfterAll
  public static void cleanup() {
    context.close();
  }

  @Test
  @DisplayName("customer requests 'israel' as the country for listing the supported cities from")
  public void testCountrySelectedIntentIsrael() throws IOException, URISyntaxException {
    final byte[] input =
        Files.readAllBytes(
            Paths.get(
                Thread.currentThread()
                    .getContextClassLoader()
                    .getResource("skill-tests/country_selected_intent_israel.json")
                    .toURI()));
    final SkillResponse<ResponseEnvelope> response = skillInTest.execute(new BaseSkillRequest(input));

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
    final byte[] input =
        Files.readAllBytes(
            Paths.get(
                Thread.currentThread()
                    .getContextClassLoader()
                    .getResource("skill-tests/country_selected_intent_england.json")
                    .toURI()));
    final SkillResponse<ResponseEnvelope> response = skillInTest.execute(new BaseSkillRequest(input));

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
    final byte[] input =
        Files.readAllBytes(
            Paths.get(
                Thread.currentThread()
                    .getContextClassLoader()
                    .getResource("skill-tests/country_selected_intent_united_states.json")
                    .toURI()));
    final SkillResponse<ResponseEnvelope> response = skillInTest.execute(new BaseSkillRequest(input));

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
    final byte[] input =
        Files.readAllBytes(
            Paths.get(
                Thread.currentThread()
                    .getContextClassLoader()
                    .getResource("skill-tests/country_selected_intent_null_country.json")
                    .toURI()));
    final SkillResponse<ResponseEnvelope> response = skillInTest.execute(new BaseSkillRequest(input));

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
    final byte[] input =
        Files.readAllBytes(
            Paths.get(
                Thread.currentThread()
                    .getContextClassLoader()
                    .getResource("skill-tests/country_selected_intent_unknown_country.json")
                    .toURI()));
    final SkillResponse<ResponseEnvelope> response = skillInTest.execute(new BaseSkillRequest(input));

    assertThat(response)
        .isPresent()
        .sessionIsStillOn()
        .cardIsAbsent()
        .sessionAttributesAreAbsent()
        .outputSpeechIs("I'm sorry. I haven't heard of Fakeland. Please try again.")
        .repromptSpeechIs("Please try again, if you want, you can ask me for help.");
  }
}