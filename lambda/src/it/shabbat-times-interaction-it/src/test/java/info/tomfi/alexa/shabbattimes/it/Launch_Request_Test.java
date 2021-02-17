/**
 * Copyright Tomer Figenblat.
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
package info.tomfi.alexa.shabbattimes.it;

import static info.tomfi.alexa.skillstester.SkillsTester.givenSkill;

import java.io.IOException;
import java.net.URISyntaxException;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@Tag("integration-tests")
final class Launch_Request_Test extends SkillInteractionFixtures {
  @Test
  void following_up_with_a_cancel_intent_request_should_end_the_interaction()
      throws IOException, URISyntaxException {
    givenSkill(sut)
        .whenRequestIs(getResource("requests/launch/launch_request.json"))
        .thenResponseShould()
            .waitForFollowup()
            .haveOutputSpeechOf("Welcome to Shabbat Times! What is your city name?")
            .haveRepromptSpeechOf("Please tell me the requested city name. "
                + "For a list of all the possible city names, just ask me for help.")
        .followingUpWith(getResource("requests/launch/cancel_intent.json"))
        .thenResponseShould()
            .notWaitForFollowup()
            .haveOutputSpeechOf("Ok.");
  }

  @Test
  void following_up_with_a_stop_intent_request_should_end_the_interaction()
      throws IOException, URISyntaxException {
    givenSkill(sut)
        .whenRequestIs(getResource("requests/launch/launch_request.json"))
        .thenResponseShould()
            .waitForFollowup()
            .haveOutputSpeechOf("Welcome to Shabbat Times! What is your city name?")
            .haveRepromptSpeechOf("Please tell me the requested city name. "
                + "For a list of all the possible city names, just ask me for help.")
        .followingUpWith(getResource("requests/launch/stop_intent.json"))
        .thenResponseShould()
            .notWaitForFollowup()
            .haveOutputSpeechOf("Ok.");
  }

  @Test
  void following_up_with_a_thanks_intent_request_should_end_the_interaction()
      throws IOException, URISyntaxException {
    givenSkill(sut)
        .whenRequestIs(getResource("requests/launch/launch_request.json"))
        .thenResponseShould()
            .waitForFollowup()
            .haveOutputSpeechOf("Welcome to Shabbat Times! What is your city name?")
            .haveRepromptSpeechOf("Please tell me the requested city name. "
                + "For a list of all the possible city names, just ask me for help.")
        .followingUpWith(getResource("requests/launch/thanks_intent.json"))
        .thenResponseShould()
            .notWaitForFollowup()
            .haveOutputSpeechOf("Happy to assist you. Have a nice day.");
  }

  @ParameterizedTest
  @ValueSource(strings = {"requests/launch/no_intent.json", "requests/launch/yes_intent.json"})
  void following_up_with_an_unrelated_intent_request_should_ask_for_clarification(
      final String followupResource) throws IOException, URISyntaxException {
    givenSkill(sut)
        .whenRequestIs(getResource("requests/launch/launch_request.json"))
        .thenResponseShould()
            .waitForFollowup()
            .haveOutputSpeechOf("Welcome to Shabbat Times! What is your city name?")
            .haveRepromptSpeechOf("Please tell me the requested city name. "
                + "For a list of all the possible city names, just ask me for help.")
        .followingUpWith(getResource(followupResource))
        .thenResponseShould()
            .waitForFollowup()
            .haveOutputSpeechOf("Hmmm... I'm not sure I understand.")
            .haveRepromptSpeechOf("Please tell me the requested city name. "
                + "For a list of all the possible city names, just ask me for help.");
  }
}
