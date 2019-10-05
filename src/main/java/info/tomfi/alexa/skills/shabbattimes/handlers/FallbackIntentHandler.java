package info.tomfi.alexa.skills.shabbattimes.handlers;

import java.util.Optional;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.impl.IntentRequestHandler;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Response;

import info.tomfi.alexa.skills.shabbattimes.annotation.IncludeHandler;

@IncludeHandler
public final class FallbackIntentHandler implements IntentRequestHandler
{
    @Override
    public boolean canHandle(final HandlerInput input, final IntentRequest intent)
    {
        return intent.getIntent().getName().equals(("AMAZON.FallbackIntent"));
    }

    @Override
    public Optional<Response> handle(final HandlerInput input, final IntentRequest intent)
    {
        return input.getResponseBuilder()
            .withSpeech("I'm sorry. I am unable to help you at the moment. Please try again later!")
            .withShouldEndSession(true)
            .build();
    }
}