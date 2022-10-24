package info.tomfi.alexa.shabbattimes.it;

import static info.tomfi.alexa.skillstester.SkillsTester.givenSkill;

import java.io.IOException;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/** Integration test cases for the fallback intent. */
@Tag("integration-tests")
final class Fallback_Intent_Request_Test extends SkillInteractionFixtures {
  @Test
  void invoking_with_a_fallback_intent_request_should_play_a_prompt_and_end_the_interaction()
      throws IOException {
    givenSkill(sut)
        .whenRequestIs(getResource("requests/fallback/fallback_intent.json"))
        .thenResponseShould()
            .haveOutputSpeechOf(
              "I'm sorry. I am unable to help you at the moment. Please try again later!")
            .and()
            .notWaitForFollowup();
  }
}
