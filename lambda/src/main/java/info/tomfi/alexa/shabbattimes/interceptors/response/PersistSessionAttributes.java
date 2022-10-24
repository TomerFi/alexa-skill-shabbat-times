package info.tomfi.alexa.shabbattimes.interceptors.response;

import static com.amazon.ask.request.Predicates.requestType;
import static info.tomfi.alexa.shabbattimes.AttributeKey.LAST_INTENT;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.interceptor.ResponseInterceptor;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Response;
import java.util.Optional;
import org.springframework.stereotype.Component;

/**
 * Response interceptor saving the current intent name as the last intent name in the session
 * attributes. for ongoing interactions.
 */
@Component
public final class PersistSessionAttributes implements ResponseInterceptor {
  public PersistSessionAttributes() {
    //
  }

  @Override
  public void process(final HandlerInput input, final Optional<Response> response) {
    if (input.matches(requestType(IntentRequest.class))
        && response.isPresent()
        && Boolean.FALSE.equals(response.get().getShouldEndSession())) {
      var attribs = input.getAttributesManager().getSessionAttributes();
      attribs.put(
          LAST_INTENT.toString(), ((IntentRequest) input.getRequest()).getIntent().getName());
      input.getAttributesManager().setSessionAttributes(attribs);
    }
  }
}
