package info.tomfi.alexa.skills.shabbattimes.exception.handlers;

import static info.tomfi.alexa.skills.shabbattimes.enums.BundleKeys.EXC_UNRECOVERABLE_ERROR;
import static info.tomfi.alexa.skills.shabbattimes.tools.LocalizationUtils.getFromBundle;

import com.amazon.ask.dispatcher.exception.ExceptionHandler;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.Response;

import info.tomfi.alexa.skills.shabbattimes.exception.NoItemFoundForDateException;

import java.util.Optional;

import lombok.NoArgsConstructor;

import org.springframework.stereotype.Component;

/**
 * Extension of com.amazon.ask.dispatcher.exception.ExceptionHandler.
 * Used for handling
 * {@link info.tomfi.alexa.skills.shabbattimes.exception.NoItemFoundForDateException} exceptions.
 *
 * @author Tomer Figenblat {@literal <tomer.figenblat@gmail.com>}
 */
@Component
@NoArgsConstructor
public final class NoItemFoundForDateHandler implements ExceptionHandler
{
    @Override
    public boolean canHandle(final HandlerInput input, final Throwable throwable)
    {
        return throwable instanceof NoItemFoundForDateException;
    }

    @Override
    public Optional<Response> handle(final HandlerInput input, final Throwable throwable)
    {
        return input.getResponseBuilder()
            .withSpeech(getFromBundle(
                input.getAttributesManager().getRequestAttributes(), EXC_UNRECOVERABLE_ERROR)
            )
            .withShouldEndSession(true)
            .build();
    }
}
