package info.tomfi.alexa.skills.shabbattimes.exception.handlers;

import java.util.Optional;

import com.amazon.ask.dispatcher.exception.ExceptionHandler;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.Response;

import info.tomfi.alexa.skills.shabbattimes.exception.NoCountrySlotException;

public final class NoCountrySlotHandler implements ExceptionHandler
{
    @Override
    public boolean canHandle(final HandlerInput input, final Throwable throwable) {
        return throwable instanceof NoCountrySlotException;
    }

    @Override
    public Optional<Response> handle(final HandlerInput input, final Throwable throwable) {
        return input.getResponseBuilder()
            .withSpeech("I'm sorry. The only countries I know are the United States, the United Kingdom, and Israel. Please repeat the country name. For a list of all the possible city names, just ask me for help.")
            .withReprompt("Please tell me the requested city name. For a list of all the possible city names, just ask me for help.")
            .withShouldEndSession(false)
            .build();
    }
}
