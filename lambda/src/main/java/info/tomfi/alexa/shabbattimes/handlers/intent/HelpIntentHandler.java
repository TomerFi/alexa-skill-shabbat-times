package info.tomfi.alexa.shabbattimes.handlers.intent;

import static info.tomfi.alexa.shabbattimes.BundleKey.HELP_REPROMPT;
import static info.tomfi.alexa.shabbattimes.BundleKey.HELP_SPEECH;
import static info.tomfi.alexa.shabbattimes.IntentType.HELP;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.impl.IntentRequestHandler;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Response;
import info.tomfi.alexa.shabbattimes.TextService;
import java.util.Optional;
import org.springframework.stereotype.Component;

/** Intent request handler for handling intent requests with the name {@value HELP}. */
@Component
public final class HelpIntentHandler implements IntentRequestHandler {
  private final TextService textor;

  public HelpIntentHandler(final TextService setTextor) {
    textor = setTextor;
  }

  @Override
  public boolean canHandle(final HandlerInput input, final IntentRequest request) {
    return request.getIntent().getName().equals(HELP.toString());
  }

  @Override
  public Optional<Response> handle(final HandlerInput input, final IntentRequest request) {
    // get request attributes
    var attributes = input.getAttributesManager().getRequestAttributes();
    // return help information and don't end the interaction
    return input
        .getResponseBuilder()
        .withSpeech(textor.getText(attributes, HELP_SPEECH))
        .withReprompt(textor.getText(attributes, HELP_REPROMPT))
        .withShouldEndSession(false)
        .build();
  }
}
