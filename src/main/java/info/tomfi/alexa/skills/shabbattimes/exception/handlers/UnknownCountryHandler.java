package info.tomfi.alexa.skills.shabbattimes.exception.handlers;

import static info.tomfi.alexa.skills.shabbattimes.enums.BundleKeys.EXC_PLEASE_TRY_AGAIN;
import static info.tomfi.alexa.skills.shabbattimes.enums.BundleKeys.EXC_UNKNOWN_COUNTRY;
import static info.tomfi.alexa.skills.shabbattimes.tools.LocalizationUtils.getFromBundle;

import com.amazon.ask.dispatcher.exception.ExceptionHandler;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Response;

import info.tomfi.alexa.skills.shabbattimes.enums.Slots;
import info.tomfi.alexa.skills.shabbattimes.exception.UnknownCountryException;

import java.util.Optional;

import lombok.val;

import org.springframework.stereotype.Component;

/**
 * Extension of com.amazon.ask.dispatcher.exception.ExceptionHandler.
 * Used for handling {@link info.tomfi.alexa.skills.shabbattimes.exception.UnknownCountryException}
 * exceptions.
 *
 * @author Tomer Figenblat {@literal <tomer.figenblat@gmail.com>}
 */
@Component
public final class UnknownCountryHandler implements ExceptionHandler
{
    @Override
    public boolean canHandle(final HandlerInput input, final Throwable throwable)
    {
        return throwable instanceof UnknownCountryException;
    }

    @Override
    public Optional<Response> handle(final HandlerInput input, final Throwable throwable)
    {

        val requestAttributes = input.getAttributesManager().getRequestAttributes();
        val outputSpeech = String.format(
            getFromBundle(requestAttributes, EXC_UNKNOWN_COUNTRY),
            ((IntentRequest) input.getRequest())
                .getIntent()
                .getSlots()
                .get(Slots.COUNTRY.getName())
                .getValue()
        );

        return input.getResponseBuilder()
            .withSpeech(outputSpeech)
            .withReprompt(getFromBundle(requestAttributes, EXC_PLEASE_TRY_AGAIN))
            .withShouldEndSession(false)
            .build();
    }
}
