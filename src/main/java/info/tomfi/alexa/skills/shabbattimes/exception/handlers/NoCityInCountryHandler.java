package info.tomfi.alexa.skills.shabbattimes.exception.handlers;

import static info.tomfi.alexa.skills.shabbattimes.enums.BundleKeys.DEFAULT_REPROMPT;
import static info.tomfi.alexa.skills.shabbattimes.enums.BundleKeys.EXC_NO_CITY_FOUND;
import static info.tomfi.alexa.skills.shabbattimes.tools.LocalizationUtils.getFromBundle;

import com.amazon.ask.dispatcher.exception.ExceptionHandler;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.Response;

import info.tomfi.alexa.skills.shabbattimes.exception.NoCityInCountryException;

import java.util.Optional;

import lombok.val;

import org.springframework.stereotype.Component;

/**
 * Extension of com.amazon.ask.dispatcher.exception.ExceptionHandler.
 * Used for handling {@link info.tomfi.alexa.skills.shabbattimes.exception.NoCityInCountryException}
 * exceptions.
 *
 * @author Tomer Figenblat {@literal <tomer.figenblat@gmail.com>}
 */
@Component
public final class NoCityInCountryHandler implements ExceptionHandler
{
    @Override
    public boolean canHandle(final HandlerInput input, final Throwable throwable)
    {
        return throwable instanceof NoCityInCountryException;
    }

    @Override
    public Optional<Response> handle(final HandlerInput input, final Throwable throwable)
    {
        val requestAttributes = input.getAttributesManager().getRequestAttributes();
        return input.getResponseBuilder()
            .withSpeech(getFromBundle(requestAttributes, EXC_NO_CITY_FOUND))
            .withReprompt(getFromBundle(requestAttributes, DEFAULT_REPROMPT))
            .withShouldEndSession(false)
            .build();
    }
}
