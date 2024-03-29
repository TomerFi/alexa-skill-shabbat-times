package info.tomfi.alexa.shabbattimes.it;

import static info.tomfi.alexa.skillstester.SkillsTester.givenSkill;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import java.io.IOException;
import java.util.stream.Stream;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/** Integration test cases for retrieving information about a specific known country. */
@Tag("integration-tests")
final class Country_Selected_Intent_Known_Country_Test extends SkillInteractionFixtures {
  static Stream<Arguments> abbreviation_and_name_arguments_provider() {
    return Stream.of(
      arguments("il", "Israel"),
      arguments("us", "the United States"),
      arguments("gb", "the United Kingdom")
    );
  }

  @ParameterizedTest
  @MethodSource(value = "abbreviation_and_name_arguments_provider")
  void following_up_with_a_cancel_intent_request_for_a_known_country_should_end_the_interaction(
      final String abbr, final String name) throws IOException {
    givenSkill(sut)
        .whenRequestIs(getResource(
          "requests/country_selected/" + abbr + "/country_selected_intent.json"))
        .thenResponseShould()
            .waitForFollowup()
            .haveOutputSpeechThatStartsWith("These are the city names I know in " + name + ":")
            .haveOutputSpeechThatEndsWith("Was your city on this list?")
            .haveRepromptSpeechOf("Please tell me the requested city name.")
        .followingUpWith(getResource("requests/country_selected/" + abbr + "/cancel_intent.json"))
        .thenResponseShould()
            .notWaitForFollowup()
            .haveOutputSpeechOf("Ok.");
  }

  @ParameterizedTest
  @MethodSource(value = "abbreviation_and_name_arguments_provider")
  void following_up_with_a_no_intent_request_for_a_known_country_should_end_the_interaction(
      final String abbr, final String name) throws IOException {
    givenSkill(sut)
        .whenRequestIs(getResource(
          "requests/country_selected/" + abbr + "/country_selected_intent.json"))
        .thenResponseShould()
            .waitForFollowup()
            .haveOutputSpeechThatStartsWith("These are the city names I know in " + name + ":")
            .haveOutputSpeechThatEndsWith("Was your city on this list?")
            .haveRepromptSpeechOf("Please tell me the requested city name.")
        .followingUpWith(getResource("requests/country_selected/" + abbr + "/no_intent.json"))
        .thenResponseShould()
            .notWaitForFollowup()
            .haveOutputSpeechOf("I'm sorry. Those are all the city names I know in " + name + "! "
                + "Please try again later, Goodbye!")
            .and()
            .haveNoCard();
  }

  @ParameterizedTest
  @MethodSource(value = "abbreviation_and_name_arguments_provider")
  void following_up_with_a_stop_intent_request_for_a_known_country_should_ask_for_clarification(
      final String abbr, final String name) throws IOException {
    givenSkill(sut)
        .whenRequestIs(getResource(
          "requests/country_selected/" + abbr + "/country_selected_intent.json"))
        .thenResponseShould()
            .waitForFollowup()
            .haveOutputSpeechThatStartsWith("These are the city names I know in " + name + ":")
            .haveOutputSpeechThatEndsWith("Was your city on this list?")
            .haveRepromptSpeechOf("Please tell me the requested city name.")
        .followingUpWith(getResource("requests/country_selected/" + abbr + "/stop_intent.json"))
        .thenResponseShould()
            .waitForFollowup()
            .haveOutputSpeechOf("Hmmm... I'm not sure I understand.")
            .haveRepromptSpeechOf("Please tell me the requested city name. "
                + "For a list of all the possible city names, just ask me for help.");
  }

  @ParameterizedTest
  @MethodSource(value = "abbreviation_and_name_arguments_provider")
  void following_up_with_a_thanks_intent_request_for_a_known_country_should_end_the_interaction(
      final String abbr, final String name) throws IOException {
    givenSkill(sut)
        .whenRequestIs(getResource(
          "requests/country_selected/" + abbr + "/country_selected_intent.json"))
        .thenResponseShould()
            .waitForFollowup()
            .haveOutputSpeechThatStartsWith("These are the city names I know in " + name + ":")
            .haveOutputSpeechThatEndsWith("Was your city on this list?")
            .haveRepromptSpeechOf("Please tell me the requested city name.")
        .followingUpWith(getResource("requests/country_selected/" + abbr + "/thanks_intent.json"))
        .thenResponseShould()
            .notWaitForFollowup()
            .haveOutputSpeechOf("Happy to assist you. Have a nice day.");
  }

  @ParameterizedTest
  @MethodSource(value = "abbreviation_and_name_arguments_provider")
  void following_up_with_a_yes_intent_request_for_a_known_country_should_wait_for_a_followup(
      final String abbr, final String name) throws IOException {
    givenSkill(sut)
        .whenRequestIs(getResource(
          "requests/country_selected/" + abbr + "/country_selected_intent.json"))
        .thenResponseShould()
            .waitForFollowup()
            .haveOutputSpeechThatStartsWith("These are the city names I know in " + name + ":")
            .haveOutputSpeechThatEndsWith("Was your city on this list?")
            .haveRepromptSpeechOf("Please tell me the requested city name.")
        .followingUpWith(getResource("requests/country_selected/" + abbr + "/yes_intent.json"))
        .thenResponseShould()
            .waitForFollowup()
            .haveOutputSpeechOf("Please tell me the requested city name.")
            .haveRepromptSpeechOf("Please tell me the requested city name. "
                + "For a list of all the possible city names, just ask me for help.");
  }
}
