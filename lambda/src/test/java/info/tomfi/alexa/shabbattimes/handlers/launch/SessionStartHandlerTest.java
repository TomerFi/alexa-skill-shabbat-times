package info.tomfi.alexa.shabbattimes.handlers.launch;

import static info.tomfi.alexa.shabbattimes.BundleKey.DEFAULT_REPROMPT;
import static info.tomfi.alexa.shabbattimes.BundleKey.WELCOME_SPEECH;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.BDDMockito.given;

import com.amazon.ask.model.LaunchRequest;
import com.amazon.ask.model.Response;
import com.amazon.ask.response.ResponseBuilder;
import info.tomfi.alexa.shabbattimes.handlers.HandlerFixtures;
import java.util.Optional;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

/** Test cases for the session start request handler. */
@Tag("unit-tests")
final class SessionStartHandlerTest extends HandlerFixtures {
  @Mock private LaunchRequest request;
  @InjectMocks private SessionStartHandler sut;

  @Test
  void can_handle_should_always_return_true_for_typed_request_handlers() {
    assertThat(sut.canHandle(input, request)).isTrue();
  }

  @Test
  void invoking_the_handler_should_return_a_welcome_follow_up(
      @Mock final ResponseBuilder builder, @Mock final Response response) {
    // stub the builder with the steps expected to be performed by the sut
    given(builder.withSpeech(getText(WELCOME_SPEECH))).willReturn(builder);
    given(builder.withReprompt(getText(DEFAULT_REPROMPT))).willReturn(builder);
    given(builder.withShouldEndSession(Boolean.FALSE)).willReturn(builder);
    given(builder.build()).willReturn(Optional.of(response));
    given(input.getResponseBuilder()).willReturn(builder);
    // when invoking the handler
    var resp = sut.handle(input, request);
    // verify the mocked response return
    then(resp).isNotEmpty().hasValue(response);
  }
}
