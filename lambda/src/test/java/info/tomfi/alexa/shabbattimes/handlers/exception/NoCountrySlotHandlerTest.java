package info.tomfi.alexa.shabbattimes.handlers.exception;

import static info.tomfi.alexa.shabbattimes.BundleKey.DEFAULT_REPROMPT;
import static info.tomfi.alexa.shabbattimes.BundleKey.EXC_NO_COUNTRY_PROVIDED;
import static org.assertj.core.api.BDDAssertions.assertThat;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.BDDMockito.given;

import com.amazon.ask.model.Response;
import com.amazon.ask.response.ResponseBuilder;
import info.tomfi.alexa.shabbattimes.exceptions.NoCountrySlotException;
import info.tomfi.alexa.shabbattimes.handlers.HandlerFixtures;
import java.util.Optional;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

/** Test cases for the NoCountrySlotHandler exception handler. */
@Tag("unit-tests")
final class NoCountrySlotHandlerTest extends HandlerFixtures {
  @InjectMocks private NoCountrySlotHandler sut;

  @Test
  void can_handle_should_return_true_only_for_instances_of_no_country_slot_exception() {
    assertThat(sut.canHandle(input, new NoCountrySlotException("some string"))).isTrue();
  }

  @Test
  void can_handle_should_return_false_for_exception_other_then_of_no_country_slot_exception() {
    assertThat(sut.canHandle(input, new Throwable())).isFalse();
  }

  @Test
  void invoking_the_handler_should_return_a_follow_up(
      @Mock final ResponseBuilder builder,
      @Mock final Response response,
      @Mock final Throwable throwable) {
    // stub the builder with the steps expected to be performed by the sut
    given(builder.withSpeech(getText(EXC_NO_COUNTRY_PROVIDED))).willReturn(builder);
    given(builder.withReprompt(getText(DEFAULT_REPROMPT))).willReturn(builder);
    given(builder.withShouldEndSession(Boolean.FALSE)).willReturn(builder);
    given(builder.build()).willReturn(Optional.of(response));
    given(input.getResponseBuilder()).willReturn(builder);
    // when invoking the handler
    var resp = sut.handle(input, throwable);
    // verify the mocked response return
    then(resp).isNotEmpty().hasValue(response);
  }
}
