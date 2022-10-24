package info.tomfi.alexa.shabbattimes.handlers.exception;

import static info.tomfi.alexa.shabbattimes.BundleKey.EXC_UNRECOVERABLE_ERROR;
import static org.assertj.core.api.BDDAssertions.assertThat;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.BDDMockito.given;

import com.amazon.ask.model.Response;
import com.amazon.ask.response.ResponseBuilder;
import info.tomfi.alexa.shabbattimes.exceptions.NoResponseFromApiException;
import info.tomfi.alexa.shabbattimes.handlers.HandlerFixtures;
import java.util.Optional;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

/** Test cases for the NoResponseFromApiHandler exception handler. */
@Tag("unit-tests")
final class NoResponseFromApiHandlerTest extends HandlerFixtures {
  @InjectMocks private NoResponseFromApiHandler sut;

  @Test
  void can_handle_should_return_true_only_for_instances_of_no_response_from_api_exception() {
    assertThat(sut.canHandle(input, new NoResponseFromApiException(new Throwable()))).isTrue();
  }

  @Test
  void can_handle_should_return_false_for_exception_other_then_of_no_response_from_api_exception() {
    assertThat(sut.canHandle(input, new Throwable())).isFalse();
  }

  @Test
  void invoking_the_handler_should_play_a_prompt_and_end_the_session(
      @Mock final ResponseBuilder builder,
      @Mock final Response response,
      @Mock final Throwable throwable) {
    // stub the builder with the steps expected to be performed by the sut
    given(builder.withSpeech(getText(EXC_UNRECOVERABLE_ERROR))).willReturn(builder);
    given(builder.withShouldEndSession(Boolean.TRUE)).willReturn(builder);
    given(builder.build()).willReturn(Optional.of(response));
    given(input.getResponseBuilder()).willReturn(builder);
    // when invoking the handler
    var resp = sut.handle(input, throwable);
    // verify the mocked response return
    then(resp).isNotEmpty().hasValue(response);
  }
}
