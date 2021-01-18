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

public final class NoIntentTest {
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
  @DisplayName("customer reply 'no'")
  public void testNoIntent() throws IOException, URISyntaxException {
    final byte[] input =
        Files.readAllBytes(
            Paths.get(
                Thread.currentThread()
                    .getContextClassLoader()
                    .getResource("skill-tests/no_intent.json")
                    .toURI()));
    final SkillResponse<ResponseEnvelope> response = skillInTest.execute(new BaseSkillRequest(input));

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
  public void testNoIntentAfterCountrySelectedIntentIL() throws IOException, URISyntaxException {
    final byte[] input =
        Files.readAllBytes(
            Paths.get(
                Thread.currentThread()
                    .getContextClassLoader()
                    .getResource("skill-tests/no_intent_after_country_selected_il.json")
                    .toURI()));
    final SkillResponse<ResponseEnvelope> response = skillInTest.execute(new BaseSkillRequest(input));

    assertThat(response)
        .isPresent()
        .sessionIsOver()
        .cardIsAbsent()
        .repromptIsAbsent()
        .sessionAttributesHasKeyWithValue("country", "IL")
        .sessionAttributesHasKeyWithValue("lastIntent", "CountrySelected")
        .outputSpeechIs(
            "I'm sorry. Those are all the city names I know in Israel! Please try again later, Goodbye!");
  }

  @Test
  @DisplayName(
      "customer reply 'no' after selecting the united states as the country to list the cities from")
  public void testNoIntentAfterCountrySelectedIntentUS() throws IOException, URISyntaxException {
    final byte[] input =
        Files.readAllBytes(
            Paths.get(
                Thread.currentThread()
                    .getContextClassLoader()
                    .getResource("skill-tests/no_intent_after_country_selected_us.json")
                    .toURI()));
    final SkillResponse<ResponseEnvelope> response = skillInTest.execute(new BaseSkillRequest(input));

    assertThat(response)
        .isPresent()
        .sessionIsOver()
        .cardIsAbsent()
        .repromptIsAbsent()
        .sessionAttributesHasKeyWithValue("country", "US")
        .sessionAttributesHasKeyWithValue("lastIntent", "CountrySelected")
        .outputSpeechIs(
            "I'm sorry. Those are all the city names I know in the United States! Please try again later, Goodbye!");
  }

  @Test
  @DisplayName("customer reply 'no' after selecting england as the country to list the cities from")
  public void testNoIntentAfterCountrySelectedIntentGB() throws IOException, URISyntaxException {
    final byte[] input =
        Files.readAllBytes(
            Paths.get(
                Thread.currentThread()
                    .getContextClassLoader()
                    .getResource("skill-tests/no_intent_after_country_selected_gb.json")
                    .toURI()));
    final SkillResponse<ResponseEnvelope> response = skillInTest.execute(new BaseSkillRequest(input));

    assertThat(response)
        .isPresent()
        .sessionIsOver()
        .cardIsAbsent()
        .repromptIsAbsent()
        .sessionAttributesHasKeyWithValue("country", "GB")
        .sessionAttributesHasKeyWithValue("lastIntent", "CountrySelected")
        .outputSpeechIs(
            "I'm sorry. Those are all the city names I know in the United Kingdom! Please try again later, Goodbye!");
  }

  @Test
  @DisplayName(
      "customer reply 'no' after selecting an unknown as the country to list the cities from")
  public void testNoIntentAfterCountrySelectedIntentUknown()
      throws IOException, URISyntaxException {
    final byte[] input =
        Files.readAllBytes(
            Paths.get(
                Thread.currentThread()
                    .getContextClassLoader()
                    .getResource("skill-tests/no_intent_after_country_selected_er.json")
                    .toURI()));
    final SkillResponse<ResponseEnvelope> response = skillInTest.execute(new BaseSkillRequest(input));

    assertThat(response)
        .isPresent()
        .sessionIsOver()
        .cardIsAbsent()
        .repromptIsAbsent()
        .sessionAttributesHasKeyWithValue("country", "unknown")
        .sessionAttributesHasKeyWithValue("lastIntent", "CountrySelected")
        .outputSpeechIs(
            "I'm sorry. Those are all the city names I know ! Please try again later, Goodbye!");
  }
}
