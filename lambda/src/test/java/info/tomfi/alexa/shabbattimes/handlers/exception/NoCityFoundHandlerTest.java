package info.tomfi.alexa.shabbattimes.handlers.exception;

import static info.tomfi.alexa.shabbattimes.BundleKey.DEFAULT_REPROMPT;
import static info.tomfi.alexa.shabbattimes.BundleKey.EXC_NO_CITY_FOUND;
import static org.assertj.core.api.BDDAssertions.assertThat;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.verifyNoMoreInteractions;

import com.amazon.ask.attributes.AttributesManager;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.Response;
import com.amazon.ask.response.ResponseBuilder;
import java.util.Optional;
import info.tomfi.alexa.shabbattimes.exceptions.NoCityFoundException;
import info.tomfi.alexa.shabbattimes.handlers.HandlerFixtures;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@Tag("unit-tests")
final class NoCityFoundHandlerTest extends HandlerFixtures {
  @Mock HandlerInput input;
  @InjectMocks NoCityFoundHandler sut;

  @Test
  void can_handle_should_return_true_only_for_instances_of_no_city_exception() {
    assertThat(sut.canHandle(input, new NoCityFoundException())).isTrue();
  }

  @Test
  void can_handle_should_return_false_for_exception_other_then_of_no_city_exception() {
    assertThat(sut.canHandle(input, new Throwable())).isFalse();
  }

  @Test
  void invoking_the_handler_should_return_a_follow_up(
      @Mock final AttributesManager attribMngr,
      @Mock final ResponseBuilder builder,
      @Mock Response response,
      @Mock final Throwable throwable) {
    // get request attributes fixture, stub the attributes manager, and the input with it
    var requestAttribs = getRequestAttribs();
    given(attribMngr.getRequestAttributes()).willReturn(requestAttribs);
    given(input.getAttributesManager()).willReturn(attribMngr);
    // stub the builder with the steps expected to be performed by the sut
    given(builder.withSpeech(getFromBundle(EXC_NO_CITY_FOUND))).willReturn(builder);
    given(builder.withReprompt(getFromBundle(DEFAULT_REPROMPT))).willReturn(builder);
    given(builder.withShouldEndSession(Boolean.FALSE)).willReturn(builder);
    given(builder.build()).willReturn(Optional.of(response));
    given(input.getResponseBuilder()).willReturn(builder);
    // when invoking the handler
    var resp = sut.handle(input, throwable);
    // verify the mocked response return and no more builder steps
    then(resp).isNotEmpty().hasValue(response);
    verifyNoMoreInteractions(builder);
  }
}
