package info.tomfi.alexa.shabbattimes.interceptors.response;

import static info.tomfi.alexa.shabbattimes.AttributeKey.LAST_INTENT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import com.amazon.ask.attributes.AttributesManager;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.Intent;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Response;
import java.util.HashMap;
import java.util.Optional;
import java.util.function.Predicate;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/** Test cases for the PersistSessionAttributes response interceptor implementation. */
@ExtendWith(MockitoExtension.class)
@SuppressWarnings("unchecked")
@Tag("unit-tests")
final class PersistSessionAttributesTest {
  @InjectMocks private PersistSessionAttributes sut;

  @Test
  void invoking_process_for_interactions_with_no_response_does_nothing(
      @Mock final HandlerInput input) {
    // short-circuit input mock to match any predicate
    given(input.matches(any(Predicate.class))).willReturn(Boolean.TRUE);
    // when invoking the handler
    sut.process(input, Optional.empty());
    // verify the new attributes are updated and saved to session
    then(input).shouldHaveNoMoreInteractions();
  }

  @Test
  void invoking_process_for_interactions_with_failed_predicate_does_nothing(
      @Mock final HandlerInput input, @Mock final Response response) {
    // short-circuit input mock to match no predicate
    given(input.matches(any(Predicate.class))).willReturn(Boolean.FALSE);
    // when invoking the handler
    sut.process(input, Optional.of(response));
    // verify the new attributes are updated and saved to session
    then(input).shouldHaveNoMoreInteractions();
    then(response).shouldHaveNoInteractions();
  }

  @Test
  void invoking_process_for_interactions_not_waiting_for_follow_up_does_nothing(
      @Mock final HandlerInput input, @Mock final Response response) {
    // short-circuit input mock to match any predicate
    given(input.matches(any(Predicate.class))).willReturn(Boolean.TRUE);
    // stub request to end the session
    given(response.getShouldEndSession()).willReturn(Boolean.TRUE);
    // when invoking the handler
    sut.process(input, Optional.of(response));
    // verify the new attributes are updated and saved to session
    then(input).shouldHaveNoMoreInteractions();
    then(response).shouldHaveNoMoreInteractions();
  }

  @Test
  void invoking_process_for_interactions_waiting_for_follow_up_saves_last_intent_name_to_session(
      @Mock final IntentRequest request,
      @Mock final Intent intnet,
      @Mock final AttributesManager attribManager,
      @Mock final HandlerInput input,
      @Mock final Response response) {
    // stub intent with fake name, stub request with intnet, and stub input with request
    given(intnet.getName()).willReturn("fake name");
    given(request.getIntent()).willReturn(intnet);
    given(input.getRequest()).willReturn(request);
    // stub attribute manager with fake attributes map, and stub input with manager
    var sessionAttribs = new HashMap<String, Object>();
    given(attribManager.getSessionAttributes()).willReturn(sessionAttribs);
    given(input.getAttributesManager()).willReturn(attribManager);
    // short-circuit input mock to match any predicate
    given(input.matches(any(Predicate.class))).willReturn(Boolean.TRUE);
    // stub request to not end the session
    given(response.getShouldEndSession()).willReturn(Boolean.FALSE);
    // when invoking the handler
    sut.process(input, Optional.of(response));
    // verify the new attributes are updated and saved to session
    then(attribManager).should().setSessionAttributes(eq(sessionAttribs));
    assertThat(sessionAttribs.get(LAST_INTENT.toString())).isEqualTo("fake name");
  }
}
