package info.tomfi.alexa.shabbattimes.it;

import static info.tomfi.alexa.skillstester.SkillsTester.givenSkill;

import java.io.IOException;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/** Integration test cases for session end requests. */
@Tag("integration-tests")
final class Session_End_Request_Test extends SkillInteractionFixtures {
  @Test
  void invoking_with_a_session_end_request_should_return_an_empty_response_and_end_the_interaction()
      throws IOException {
    givenSkill(sut)
        .whenRequestIs(getResource("requests/session_end/session_end_request.json"))
        .thenResponseShould().beEmpty();
  }
}
