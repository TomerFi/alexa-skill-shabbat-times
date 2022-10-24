package info.tomfi.alexa.shabbattimes.handlers.exception;

import static info.tomfi.alexa.shabbattimes.BundleKey.EXC_UNRECOVERABLE_ERROR;

import com.amazon.ask.dispatcher.exception.ExceptionHandler;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.Response;
import info.tomfi.alexa.shabbattimes.TextService;
import info.tomfi.alexa.shabbattimes.exceptions.NoJsonFileException;
import java.util.Optional;
import org.springframework.stereotype.Component;

/** Exception handler used for handling {@link NoJsonFileException} cases. */
@Component
public final class NoJsonFileHandler implements ExceptionHandler {
  private final TextService textor;

  public NoJsonFileHandler(final TextService setTextor) {
    textor = setTextor;
  }

  @Override
  public boolean canHandle(final HandlerInput input, final Throwable throwable) {
    return throwable instanceof NoJsonFileException;
  }

  @Override
  public Optional<Response> handle(final HandlerInput input, final Throwable throwable) {
    var requestAttributes = input.getAttributesManager().getRequestAttributes();
    return input
        .getResponseBuilder()
        .withSpeech(textor.getText(requestAttributes, EXC_UNRECOVERABLE_ERROR))
        .withShouldEndSession(true)
        .build();
  }
}
