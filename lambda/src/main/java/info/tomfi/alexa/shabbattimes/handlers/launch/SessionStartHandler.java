package info.tomfi.alexa.shabbattimes.handlers.launch;

import static info.tomfi.alexa.shabbattimes.BundleKey.DEFAULT_REPROMPT;
import static info.tomfi.alexa.shabbattimes.BundleKey.WELCOME_SPEECH;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.impl.LaunchRequestHandler;
import com.amazon.ask.model.LaunchRequest;
import com.amazon.ask.model.Response;
import info.tomfi.alexa.shabbattimes.TextService;
import java.util.Optional;
import org.springframework.stereotype.Component;

/** Handler for handling launch requests. */
@Component
public final class SessionStartHandler implements LaunchRequestHandler {
  private final TextService textor;

  public SessionStartHandler(final TextService setTextor) {
    textor = setTextor;
  }

  @Override
  public boolean canHandle(final HandlerInput input, final LaunchRequest request) {
    return true;
  }

  @Override
  public Optional<Response> handle(final HandlerInput input, final LaunchRequest request) {
    var attributes = input.getAttributesManager().getRequestAttributes();
    return input
        .getResponseBuilder()
        .withSpeech(textor.getText(attributes, WELCOME_SPEECH))
        .withReprompt(textor.getText(attributes, DEFAULT_REPROMPT))
        .withShouldEndSession(false)
        .build();
  }
}
