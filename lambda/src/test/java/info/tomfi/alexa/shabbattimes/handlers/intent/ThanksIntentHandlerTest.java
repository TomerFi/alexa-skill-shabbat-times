package info.tomfi.alexa.shabbattimes.handlers.intent;

import static info.tomfi.alexa.shabbattimes.BundleKey.THANKS_AND_BYE;
import static info.tomfi.alexa.shabbattimes.IntentType.THANKS;
import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.params.provider.EnumSource.Mode.EXCLUDE;
import static org.mockito.BDDMockito.given;

import com.amazon.ask.model.Intent;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Response;
import com.amazon.ask.response.ResponseBuilder;
import info.tomfi.alexa.shabbattimes.IntentType;
import info.tomfi.alexa.shabbattimes.handlers.HandlerFixtures;
import java.util.Optional;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;

/** Test cases for the ThanksIntentHandler intent request handler. */
@Tag("unit-tests")
final class ThanksIntentHandlerTest extends HandlerFixtures {
  @Mock private IntentRequest request;
  @InjectMocks private ThanksIntentHandler sut;

  @Test
  void can_handle_should_return_true_for_thanks_intent_type(@Mock final Intent intent) {
    // stub intent with THANKS as name and stub request with intnet
    given(intent.getName()).willReturn(THANKS.toString());
    given(request.getIntent()).willReturn(intent);
    // verify handler can handle
    then(sut.canHandle(input, request)).isTrue();
  }

  @ParameterizedTest
  @EnumSource(mode = EXCLUDE, names = "THANKS")
  void can_handle_should_return_false_for_non_thanks_intent_type(
      final IntentType intentType, @Mock final Intent intent) {
    // stub intent with intent type as name and stub request with intnet
    given(intent.getName()).willReturn(intentType.toString());
    given(request.getIntent()).willReturn(intent);
    // verify handler cannot handle
    then(sut.canHandle(input, request)).isFalse();
  }

  @Test
  void invoking_the_handler_should_play_a_prompt_and_end_the_session(
      @Mock final ResponseBuilder builder, @Mock final Response response) {
    // stub the builder with the steps expected to be performed by the sut
    given(builder.withSpeech(getText(THANKS_AND_BYE))).willReturn(builder);
    given(builder.withShouldEndSession(Boolean.TRUE)).willReturn(builder);
    given(builder.build()).willReturn(Optional.of(response));
    given(input.getResponseBuilder()).willReturn(builder);
    // when invoking the handler
    var resp = sut.handle(input, request);
    // verify the mocked response return
    then(resp).isNotEmpty().hasValue(response);
  }
}
