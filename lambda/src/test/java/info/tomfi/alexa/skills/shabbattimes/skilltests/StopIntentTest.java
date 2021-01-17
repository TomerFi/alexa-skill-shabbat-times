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

import com.amazon.ask.request.impl.BaseSkillRequest;
import info.tomfi.alexa.skills.shabbattimes.ShabbatTimesSkillCreator;
import info.tomfi.alexa.skills.shabbattimes.di.DiMockApiConfiguration;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import lombok.Cleanup;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeansException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public final class StopIntentTest {
  @Test
  @DisplayName("customer say 'stop'")
  public void testStopIntent()
      throws BeansException, IllegalAccessException, InstantiationException, IOException,
          URISyntaxException {
    @Cleanup val context = new AnnotationConfigApplicationContext(DiMockApiConfiguration.class);
    val skillInTest = context.getBean(ShabbatTimesSkillCreator.class).getSkill();

    val input =
        Files.readAllBytes(
            Paths.get(
                Thread.currentThread()
                    .getContextClassLoader()
                    .getResource("skill-tests/stop_intent.json")
                    .toURI()));
    val response = skillInTest.execute(new BaseSkillRequest(input));

    assertThat(response)
        .isPresent()
        .sessionIsStillOn()
        .cardIsAbsent()
        .sessionAttributesHasKeyWithValue("lastIntent", "AMAZON.StopIntent")
        .outputSpeechIs("Please tell me the requested city name.")
        .repromptSpeechIs(
            "Please tell me the requested city name. For a list of all the possible city names, just ask me for help.");
  }
}
