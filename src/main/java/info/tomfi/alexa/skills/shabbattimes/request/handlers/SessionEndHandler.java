package info.tomfi.alexa.skills.shabbattimes.request.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.impl.SessionEndedRequestHandler;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.SessionEndedRequest;

import java.util.Optional;

import org.springframework.stereotype.Component;

/**
 * Implementation of com.amazon.ask.dispatcher.request.handler.impl.SessionEndedRequestHandler
 * handles requests of type com.amazon.ask.model.SessionEndedRequest.
 *
 * @author Tomer Figenblat {@literal <tomer.figenblat@gmail.com>}
 */
@Component
public final class SessionEndHandler implements SessionEndedRequestHandler
{
    @Override
    public boolean canHandle(final HandlerInput input, final SessionEndedRequest request)
    {
        return true;
    }

    @Override
    public Optional<Response> handle(final HandlerInput input, final SessionEndedRequest request)
    {
        return Optional.empty();
    }
}
