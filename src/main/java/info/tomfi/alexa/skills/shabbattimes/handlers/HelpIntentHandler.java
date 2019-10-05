package info.tomfi.alexa.skills.shabbattimes.handlers;

import java.util.Optional;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.impl.IntentRequestHandler;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Response;

import info.tomfi.alexa.skills.shabbattimes.annotation.IncludeHandler;

@IncludeHandler
public final class HelpIntentHandler implements IntentRequestHandler
{
    @Override
    public boolean canHandle(final HandlerInput input, final IntentRequest intent)
    {
        return intent.getIntent().getName().equals(("AMAZON.HelpIntent"));
    }

    @Override
    public Optional<Response> handle(final HandlerInput input, final IntentRequest intent)
    {
        return input.getResponseBuilder()
            .withSpeech("I can list all the city names i know in the United States, the United Kingdom, and in Israel. Which country would you like to hear about?")
            .withReprompt("Please tell me your country! United States, United Kingdom, or Israel.")
            .withShouldEndSession(false)
            .build();
    }
}