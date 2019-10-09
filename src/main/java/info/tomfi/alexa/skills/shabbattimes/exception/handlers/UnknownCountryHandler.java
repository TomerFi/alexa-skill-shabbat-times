package info.tomfi.alexa.skills.shabbattimes.exception.handlers;

import static info.tomfi.alexa.skills.shabbattimes.tools.GlobalEnums.Slots;

import java.util.Optional;

import com.amazon.ask.dispatcher.exception.ExceptionHandler;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Response;

import info.tomfi.alexa.skills.shabbattimes.exception.UnknownCountryException;

public final class UnknownCountryHandler implements ExceptionHandler
{
    @Override
    public boolean canHandle(final HandlerInput input, final Throwable throwable) {
        return throwable instanceof UnknownCountryException;
    }

    @Override
    public Optional<Response> handle(final HandlerInput input, final Throwable throwable) {
        return input.getResponseBuilder()
            .withSpeech(
                String.format(
                    "I'm sorry. I haven't heard of %s. Please try again.",
                    ((IntentRequest) input.getRequest())
                        .getIntent()
                        .getSlots()
                        .get(Slots.COUNTRY.name)
                        .getValue()
                )
            ).withReprompt("Please try again, if you want, you can ask me for help.")
            .withShouldEndSession(false)
            .build();
    }
}
