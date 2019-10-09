package info.tomfi.alexa.skills.shabbattimes.request.handlers;

import java.util.Optional;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.impl.SessionEndedRequestHandler;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.SessionEndedRequest;

import info.tomfi.alexa.skills.shabbattimes.annotation.IncludeRequestHandler;

@IncludeRequestHandler
public final class SessionEndHandler implements SessionEndedRequestHandler
{
    @Override
    public boolean canHandle(final HandlerInput input, final SessionEndedRequest request)
    {
        return true;
    }

    @Override
    public Optional<Response> handle(final HandlerInput input, final SessionEndedRequest request) {
        return Optional.empty();
    }
}
