package info.tomfi.alexa.shabbattimes.handlers.intent;

import static info.tomfi.alexa.shabbattimes.BundleKey.THANKS_AND_BYE;
import static info.tomfi.alexa.shabbattimes.IntentType.THANKS;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.impl.IntentRequestHandler;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Response;
import info.tomfi.alexa.shabbattimes.TextService;
import java.util.Optional;
import org.springframework.stereotype.Component;

/** Intent request handler for handling intent requests with the name {@value THANKS}. */
@Component
public final class ThanksIntentHandler implements IntentRequestHandler {
  private final TextService textor;

  public ThanksIntentHandler(final TextService setTextor) {
    textor = setTextor;
  }

  @Override
  public boolean canHandle(final HandlerInput input, final IntentRequest request) {
    return request.getIntent().getName().equals(THANKS.toString());
  }

  @Override
  public Optional<Response> handle(final HandlerInput input, final IntentRequest request) {
    // get request attributes
    var attributes = input.getAttributesManager().getRequestAttributes();
    // if the user is polite and reply's `no thanks` to follow up questions
    // return polite ending message and end the interaction
    return input
        .getResponseBuilder()
        .withSpeech(textor.getText(attributes, THANKS_AND_BYE))
        .withShouldEndSession(true)
        .build();
  }
}
