package info.tomfi.alexa.skills.shabbattimes.handlers;

import java.util.Optional;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.impl.IntentRequestHandler;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Response;

import info.tomfi.alexa.skills.shabbattimes.annotation.IncludeHandler;

@IncludeHandler
public final class ThanksIntentHandler implements IntentRequestHandler
{
    @Override
    public boolean canHandle(final HandlerInput input, final IntentRequest intent)
    {
        return intent.getIntent().getName().equals(("ThanksIntent"));
    }

    @Override
    public Optional<Response> handle(final HandlerInput input, final IntentRequest intent)
    {
        return input.getResponseBuilder()
            .withSpeech("Happy to assist you. Have a nice day.")
            .withShouldEndSession(true)
            .build();
    }
}