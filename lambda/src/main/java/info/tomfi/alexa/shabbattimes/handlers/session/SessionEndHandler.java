package info.tomfi.alexa.shabbattimes.handlers.session;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.impl.SessionEndedRequestHandler;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.SessionEndedRequest;
import java.util.Optional;
import org.springframework.stereotype.Component;

/** Handler for handling session end requests. */
@Component
public final class SessionEndHandler implements SessionEndedRequestHandler {
  public SessionEndHandler() {
    //
  }

  @Override
  public boolean canHandle(final HandlerInput input, final SessionEndedRequest request) {
    return true;
  }

  @Override
  public Optional<Response> handle(final HandlerInput input, final SessionEndedRequest request) {
    return Optional.empty();
  }
}
