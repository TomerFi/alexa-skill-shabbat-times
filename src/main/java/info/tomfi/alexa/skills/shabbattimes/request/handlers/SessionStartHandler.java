package info.tomfi.alexa.skills.shabbattimes.request.handlers;

import java.util.Optional;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.impl.LaunchRequestHandler;
import com.amazon.ask.model.LaunchRequest;
import com.amazon.ask.model.Response;

import info.tomfi.alexa.skills.shabbattimes.annotation.IncludeRequestHandler;

@IncludeRequestHandler
public class SessionStartHandler implements LaunchRequestHandler {

    @Override
    public boolean canHandle(final HandlerInput input, final LaunchRequest request)
    {
        return true;
    }

    @Override
    public Optional<Response> handle(final HandlerInput input, final LaunchRequest request) {
        return input.getResponseBuilder()
                .withSpeech("Welcome to shabbat times! What is your city name?")
                .withReprompt("Please tell me the requested city name. For a list of all the possible city names, just ask me for help.")
                .withShouldEndSession(false)
                .build();
    }
}
