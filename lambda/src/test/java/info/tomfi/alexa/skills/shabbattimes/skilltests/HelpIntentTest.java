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
package info.tomfi.alexa.skills.shabbattimes.skilltests;

import static info.tomfi.alexa.skills.shabbattimes.assertions.Assertions.assertThat;

import com.amazon.ask.Skill;
import com.amazon.ask.model.ResponseEnvelope;
import com.amazon.ask.request.impl.BaseSkillRequest;
import com.amazon.ask.response.SkillResponse;
import info.tomfi.alexa.skills.shabbattimes.ShabbatTimesSkillCreator;
import info.tomfi.alexa.skills.shabbattimes.di.DiMockApiConfiguration;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeansException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public final class HelpIntentTest {
  @Test
  @DisplayName("customer ask 'help'")
  public void testHelpIntent()
      throws BeansException, IllegalAccessException, InstantiationException, IOException,
          URISyntaxException {
    final AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DiMockApiConfiguration.class);
    final Skill skillInTest = context.getBean(ShabbatTimesSkillCreator.class).getSkill();

    byte[] input =
        Files.readAllBytes(
            Paths.get(
                Thread.currentThread()
                    .getContextClassLoader()
                    .getResource("skill-tests/help_intent.json")
                    .toURI()));
    final SkillResponse<ResponseEnvelope> response = skillInTest.execute(new BaseSkillRequest(input));

    assertThat(response)
        .isPresent()
        .sessionIsStillOn()
        .cardIsAbsent()
        .sessionAttributesHasKeyWithValue("lastIntent", "AMAZON.HelpIntent")
        .outputSpeechIs(
            "I can list all the city names I know in the United States, the United Kingdom, and Israel. Which country would you like to hear about?")
        .repromptSpeechIs(
            "Please tell me the country name! United States, United Kingdom, or Israel.");

    context.close();
  }
}
