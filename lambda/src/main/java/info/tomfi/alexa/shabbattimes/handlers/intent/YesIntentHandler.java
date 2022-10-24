package info.tomfi.alexa.shabbattimes.handlers.intent;

import static info.tomfi.alexa.shabbattimes.AttributeKey.LAST_INTENT;
import static info.tomfi.alexa.shabbattimes.BundleKey.DEFAULT_ASK_FOR_CITY;
import static info.tomfi.alexa.shabbattimes.BundleKey.DEFAULT_PLEASE_CLARIFY;
import static info.tomfi.alexa.shabbattimes.BundleKey.DEFAULT_REPROMPT;
import static info.tomfi.alexa.shabbattimes.IntentType.COUNTRY_SELECTED;
import static info.tomfi.alexa.shabbattimes.IntentType.YES;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.impl.IntentRequestHandler;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Response;
import info.tomfi.alexa.shabbattimes.TextService;
import java.util.Optional;
import org.springframework.stereotype.Component;

/** Intent request handler for handling intent requests with the name {@value YES}. */
@Component
public final class YesIntentHandler implements IntentRequestHandler {
  private final TextService textor;

  public YesIntentHandler(final TextService setTextor) {
    textor = setTextor;
  }

  @Override
  public boolean canHandle(final HandlerInput input, final IntentRequest request) {
    return request.getIntent().getName().equals(YES.toString());
  }

  @Override
  public Optional<Response> handle(final HandlerInput input, final IntentRequest request) {
    // get attributes
    var requstAttribs = input.getAttributesManager().getRequestAttributes();
    var sessionAttribs = input.getAttributesManager().getSessionAttributes();
    // start builder
    var respBuilder =
        input
            .getResponseBuilder()
            .withReprompt(textor.getText(requstAttribs, DEFAULT_REPROMPT))
            .withShouldEndSession(false);
    if (sessionAttribs.containsKey(LAST_INTENT.toString())
        && sessionAttribs.get(LAST_INTENT.toString()).equals(COUNTRY_SELECTED.toString())) {
      // the user reply yes to the question `was you city on the list?`
      respBuilder.withSpeech(textor.getText(requstAttribs, DEFAULT_ASK_FOR_CITY));
    } else {
      respBuilder.withSpeech(textor.getText(requstAttribs, DEFAULT_PLEASE_CLARIFY));
    }
    return respBuilder.build();
  }
}
