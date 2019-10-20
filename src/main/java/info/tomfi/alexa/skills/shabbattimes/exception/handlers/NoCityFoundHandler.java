package info.tomfi.alexa.skills.shabbattimes.exception.handlers;

import static info.tomfi.alexa.skills.shabbattimes.enums.BundleKeys.DEFAULT_REPROMPT;
import static info.tomfi.alexa.skills.shabbattimes.enums.BundleKeys.EXC_NO_CITY_FOUND;
import static info.tomfi.alexa.skills.shabbattimes.tools.LocalizationUtils.getFromBundle;

import java.util.Optional;

import com.amazon.ask.dispatcher.exception.ExceptionHandler;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.Response;

import org.springframework.stereotype.Component;

import info.tomfi.alexa.skills.shabbattimes.exception.NoCityFoundException;
import lombok.val;

@Component
public final class NoCityFoundHandler implements ExceptionHandler
{
    @Override
    public boolean canHandle(final HandlerInput input, final Throwable throwable) {
        return throwable instanceof NoCityFoundException;
    }

    @Override
    public Optional<Response> handle(final HandlerInput input, final Throwable throwable) {
        val requestAttributes = input.getAttributesManager().getRequestAttributes();
        return input.getResponseBuilder()
            .withSpeech(getFromBundle(requestAttributes, EXC_NO_CITY_FOUND))
            .withReprompt(getFromBundle(requestAttributes, DEFAULT_REPROMPT))
            .withShouldEndSession(false)
            .build();
    }
}
