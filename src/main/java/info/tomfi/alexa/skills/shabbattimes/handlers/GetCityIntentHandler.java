package info.tomfi.alexa.skills.shabbattimes.handlers;

import java.util.Optional;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.impl.IntentRequestHandler;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Response;

import info.tomfi.alexa.skills.shabbattimes.annotation.IncludeHandler;

@IncludeHandler
public final class GetCityIntentHandler implements IntentRequestHandler
{
    @Override
    public boolean canHandle(final HandlerInput input, final IntentRequest intent)
    {
        return intent.getIntent().getName().equals(("GetCityIntent"));
    }

    @Override
    public Optional<Response> handle(final HandlerInput input, final IntentRequest intent)
    {
        // TODO
        return input.getResponseBuilder()
            .withSpeech("TODO")
            .withShouldEndSession(true)
            .build();
    }
}