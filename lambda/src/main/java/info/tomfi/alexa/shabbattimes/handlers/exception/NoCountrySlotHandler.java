package info.tomfi.alexa.shabbattimes.handlers.exception;

import static info.tomfi.alexa.shabbattimes.BundleKey.DEFAULT_REPROMPT;
import static info.tomfi.alexa.shabbattimes.BundleKey.EXC_NO_COUNTRY_PROVIDED;

import com.amazon.ask.dispatcher.exception.ExceptionHandler;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.Response;
import info.tomfi.alexa.shabbattimes.TextService;
import info.tomfi.alexa.shabbattimes.exceptions.NoCountrySlotException;
import java.util.Optional;
import org.springframework.stereotype.Component;

/** Exception handler used for handling {@link NoCountrySlotException} cases. */
@Component
public final class NoCountrySlotHandler implements ExceptionHandler {
  private final TextService textor;

  public NoCountrySlotHandler(final TextService setTextor) {
    textor = setTextor;
  }

  @Override
  public boolean canHandle(final HandlerInput input, final Throwable throwable) {
    return throwable instanceof NoCountrySlotException;
  }

  @Override
  public Optional<Response> handle(final HandlerInput input, final Throwable throwable) {
    var requestAttributes = input.getAttributesManager().getRequestAttributes();
    return input
        .getResponseBuilder()
        .withSpeech(textor.getText(requestAttributes, EXC_NO_COUNTRY_PROVIDED))
        .withReprompt(textor.getText(requestAttributes, DEFAULT_REPROMPT))
        .withShouldEndSession(false)
        .build();
  }
}
