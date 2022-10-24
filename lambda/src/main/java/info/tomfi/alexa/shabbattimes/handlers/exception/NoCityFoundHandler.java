package info.tomfi.alexa.shabbattimes.handlers.exception;

import static info.tomfi.alexa.shabbattimes.BundleKey.DEFAULT_REPROMPT;
import static info.tomfi.alexa.shabbattimes.BundleKey.EXC_NO_CITY_FOUND;

import com.amazon.ask.dispatcher.exception.ExceptionHandler;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.Response;
import info.tomfi.alexa.shabbattimes.TextService;
import info.tomfi.alexa.shabbattimes.exceptions.NoCityFoundException;
import java.util.Optional;
import org.springframework.stereotype.Component;

/** Exception handler used for handling {@link NoCityFoundException} cases. */
@Component
public final class NoCityFoundHandler implements ExceptionHandler {
  private final TextService textor;

  public NoCityFoundHandler(final TextService setTextor) {
    textor = setTextor;
  }

  @Override
  public boolean canHandle(final HandlerInput input, final Throwable throwable) {
    return throwable instanceof NoCityFoundException;
  }

  @Override
  public Optional<Response> handle(final HandlerInput input, final Throwable throwable) {
    var requestAttributes = input.getAttributesManager().getRequestAttributes();
    return input
        .getResponseBuilder()
        .withSpeech(textor.getText(requestAttributes, EXC_NO_CITY_FOUND))
        .withReprompt(textor.getText(requestAttributes, DEFAULT_REPROMPT))
        .withShouldEndSession(false)
        .build();
  }
}
