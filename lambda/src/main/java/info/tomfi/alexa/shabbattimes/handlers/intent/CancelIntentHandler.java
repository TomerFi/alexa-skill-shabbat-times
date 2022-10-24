package info.tomfi.alexa.shabbattimes.handlers.intent;

import static info.tomfi.alexa.shabbattimes.BundleKey.DEFAULT_OK;
import static info.tomfi.alexa.shabbattimes.IntentType.CANCEL;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.impl.IntentRequestHandler;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Response;
import info.tomfi.alexa.shabbattimes.TextService;
import java.util.Optional;
import org.springframework.stereotype.Component;

/** Intent request handler for handling intent requests with the name {@value CANCEL}. */
@Component
public final class CancelIntentHandler implements IntentRequestHandler {
  private final TextService textor;

  public CancelIntentHandler(final TextService setTextor) {
    textor = setTextor;
  }

  @Override
  public boolean canHandle(final HandlerInput input, final IntentRequest request) {
    return request.getIntent().getName().equals(CANCEL.toString());
  }

  @Override
  public Optional<Response> handle(final HandlerInput input, final IntentRequest request) {
    // get request attributes
    var attributes = input.getAttributesManager().getRequestAttributes();
    // return OK and end the interaction
    return input
        .getResponseBuilder()
        .withSpeech(textor.getText(attributes, DEFAULT_OK))
        .withShouldEndSession(true)
        .build();
  }
}
