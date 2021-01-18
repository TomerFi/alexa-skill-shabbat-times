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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeansException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public final class SessionStartTest {
  @Test
  @DisplayName("customer ask 'start shabbat times'")
  public void testSessionStart()
      throws BeansException, IllegalAccessException, InstantiationException, IOException,
          URISyntaxException {
    final AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DiMockApiConfiguration.class);
    final Skill skillInTest = context.getBean(ShabbatTimesSkillCreator.class).getSkill();

    final byte[] input =
        Files.readAllBytes(
            Paths.get(
                Thread.currentThread()
                    .getContextClassLoader()
                    .getResource("skill-tests/session_start.json")
                    .toURI()));
    final SkillResponse<ResponseEnvelope> response = skillInTest.execute(new BaseSkillRequest(input));

    assertThat(response)
        .isPresent()
        .sessionIsStillOn()
        .sessionAttributesAreAbsent()
        .cardIsAbsent()
        .outputSpeechIs("Welcome to Shabbat Times! What is your city name?")
        .repromptSpeechIs(
            "Please tell me the requested city name. For a list of all the possible city names, just ask me for help.");
    context.close();
  }
}
