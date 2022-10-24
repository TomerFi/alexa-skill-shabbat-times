package info.tomfi.alexa.shabbattimes.it;

import static info.tomfi.alexa.skillstester.SkillsTester.givenSkill;

import java.io.IOException;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/** Integration test cases for retrieving information about an unknown country. */
@Tag("integration-tests")
final class Country_Selected_Intent_Unknown_Country_Test extends SkillInteractionFixtures {
  @Test
  void following_up_with_a_cancel_intent_request_for_an_unknown_country_should_end_the_interaction()
      throws IOException {
    givenSkill(sut)
        .whenRequestIs(
          getResource("requests/country_selected/unknown/country_selected_intent.json"))
        .thenResponseShould()
          .waitForFollowup()
            .haveOutputSpeechOf("I'm sorry. The only countries I know are the United States, "
              + "the United Kingdom, and Israel. Please repeat the country name. "
              + "For a list of all the possible city names, just ask me for help.")
            .haveRepromptSpeechOf("Please tell me the requested city name. "
              + "For a list of all the possible city names, just ask me for help.")
        .followingUpWith(getResource("requests/country_selected/unknown/cancel_intent.json"))
        .thenResponseShould()
            .notWaitForFollowup()
            .haveOutputSpeechOf("Ok.");
  }

  @Test
  void following_up_with_a_no_intent_request_for_an_unknown_country_should_end_the_interaction()
      throws IOException {
    givenSkill(sut)
        .whenRequestIs(
          getResource("requests/country_selected/unknown/country_selected_intent.json"))
        .thenResponseShould()
            .waitForFollowup()
            .haveOutputSpeechOf("I'm sorry. The only countries I know are the United States, "
                + "the United Kingdom, and Israel. Please repeat the country name. "
                + "For a list of all the possible city names, just ask me for help.")
            .haveRepromptSpeechOf("Please tell me the requested city name. "
                + "For a list of all the possible city names, just ask me for help.")
        .followingUpWith(getResource("requests/country_selected/unknown/no_intent.json"))
        .thenResponseShould()
            .notWaitForFollowup()
            .haveNoOutputSpeech();
  }

  @Test
  void following_up_with_a_stop_intent_request_for_an_unknown_country_should_ask_for_clarification()
      throws IOException {
    givenSkill(sut)
        .whenRequestIs(
          getResource("requests/country_selected/unknown/country_selected_intent.json"))
        .thenResponseShould()
            .waitForFollowup()
            .haveOutputSpeechOf("I'm sorry. The only countries I know are the United States, "
                + "the United Kingdom, and Israel. Please repeat the country name. "
                + "For a list of all the possible city names, just ask me for help.")
            .haveRepromptSpeechOf("Please tell me the requested city name. "
                + "For a list of all the possible city names, just ask me for help.")
        .followingUpWith(getResource("requests/country_selected/unknown/stop_intent.json"))
        .thenResponseShould()
            .waitForFollowup()
            .haveOutputSpeechOf("Hmmm... I'm not sure I understand.")
            .haveRepromptSpeechOf("Please tell me the requested city name. "
                + "For a list of all the possible city names, just ask me for help.");
  }

  @Test
  void following_up_with_a_thanks_intent_request_for_an_unknown_country_should_wait_for_a_followup()
      throws IOException {
    givenSkill(sut)
        .whenRequestIs(
          getResource("requests/country_selected/unknown/country_selected_intent.json"))
        .thenResponseShould()
            .waitForFollowup()
            .haveOutputSpeechOf("I'm sorry. The only countries I know are the United States, "
                + "the United Kingdom, and Israel. Please repeat the country name. "
                + "For a list of all the possible city names, just ask me for help.")
            .haveRepromptSpeechOf("Please tell me the requested city name. "
                + "For a list of all the possible city names, just ask me for help.")
        .followingUpWith(getResource("requests/country_selected/unknown/thanks_intent.json"))
        .thenResponseShould()
            .notWaitForFollowup()
            .haveOutputSpeechOf("Happy to assist you. Have a nice day.");
  }

  @Test
  void following_up_with_a_yes_intent_request_for_an_unknown_country_should_wait_for_a_followup()
      throws IOException {
    givenSkill(sut)
        .whenRequestIs(
          getResource("requests/country_selected/unknown/country_selected_intent.json"))
        .thenResponseShould()
            .waitForFollowup()
            .haveOutputSpeechOf("I'm sorry. The only countries I know are the United States, "
                + "the United Kingdom, and Israel. Please repeat the country name. "
                + "For a list of all the possible city names, just ask me for help.")
            .haveRepromptSpeechOf("Please tell me the requested city name. "
                + "For a list of all the possible city names, just ask me for help.")
        .followingUpWith(getResource("requests/country_selected/unknown/yes_intent.json"))
        .thenResponseShould()
            .waitForFollowup()
            .haveOutputSpeechOf("Please tell me the requested city name.")
            .haveRepromptSpeechOf("Please tell me the requested city name. "
                + "For a list of all the possible city names, just ask me for help.");
  }
}
