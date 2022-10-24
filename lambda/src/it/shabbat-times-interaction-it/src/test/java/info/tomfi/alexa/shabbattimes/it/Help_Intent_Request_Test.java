package info.tomfi.alexa.shabbattimes.it;

import static info.tomfi.alexa.skillstester.SkillsTester.givenSkill;

import java.io.IOException;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/** Integration test cases for the help intent. */
@Tag("integration-tests")
final class Help_Intent_Request_Test extends SkillInteractionFixtures {
  @Test
  void following_up_with_a_cancel_intent_request_should_end_the_interaction()
      throws IOException {
    givenSkill(sut)
        .whenRequestIs(getResource("requests/help/help_intent.json"))
        .thenResponseShould()
            .waitForFollowup()
            .haveOutputSpeechOf("I can list all the city names I know in the United States, "
                + "the United Kingdom, and Israel. Which country would you like to hear about?")
            .haveRepromptSpeechOf(
              "Please tell me the country name! United States, United Kingdom, or Israel.")
        .followingUpWith(getResource("requests/help/cancel_intent.json"))
        .thenResponseShould()
            .notWaitForFollowup()
            .haveOutputSpeechOf("Ok.");
  }

  @Test
  void following_up_with_a_stop_intent_request_should_end_the_interaction()
      throws IOException {
    givenSkill(sut)
        .whenRequestIs(getResource("requests/help/help_intent.json"))
        .thenResponseShould()
            .waitForFollowup()
            .haveOutputSpeechOf("I can list all the city names I know in the United States, "
                + "the United Kingdom, and Israel. Which country would you like to hear about?")
            .haveRepromptSpeechOf(
              "Please tell me the country name! United States, United Kingdom, or Israel.")
        .followingUpWith(getResource("requests/help/stop_intent.json"))
        .thenResponseShould()
            .waitForFollowup()
            .haveOutputSpeechOf("Hmmm... I'm not sure I understand.")
            .haveRepromptSpeechOf("Please tell me the requested city name. "
                + "For a list of all the possible city names, just ask me for help.");
  }

  @Test
  void following_up_with_a_thanks_intent_request_should_end_the_interaction()
      throws IOException {
    givenSkill(sut)
        .whenRequestIs(getResource("requests/help/help_intent.json"))
        .thenResponseShould()
            .waitForFollowup()
            .haveOutputSpeechOf("I can list all the city names I know in the United States, "
                + "the United Kingdom, and Israel. Which country would you like to hear about?")
            .haveRepromptSpeechOf(
              "Please tell me the country name! United States, United Kingdom, or Israel.")
        .followingUpWith(getResource("requests/help/thanks_intent.json"))
        .thenResponseShould()
            .notWaitForFollowup()
            .haveOutputSpeechOf("Happy to assist you. Have a nice day.");
  }

  @ParameterizedTest
  @ValueSource(strings = {"requests/help/no_intent.json", "requests/help/yes_intent.json"})
  void following_up_with_an_unrelated_intent_request_should_ask_for_clarification(
      final String followupResource) throws IOException {
    givenSkill(sut)
        .whenRequestIs(getResource("requests/help/help_intent.json"))
        .thenResponseShould()
            .waitForFollowup()
            .haveOutputSpeechOf("I can list all the city names I know in the United States, "
                + "the United Kingdom, and Israel. Which country would you like to hear about?")
            .haveRepromptSpeechOf(
              "Please tell me the country name! United States, United Kingdom, or Israel.")
        .followingUpWith(getResource(followupResource))
        .thenResponseShould()
            .waitForFollowup()
            .haveOutputSpeechOf("Hmmm... I'm not sure I understand.")
            .haveRepromptSpeechOf("Please tell me the requested city name. "
                + "For a list of all the possible city names, just ask me for help.");
  }
}
