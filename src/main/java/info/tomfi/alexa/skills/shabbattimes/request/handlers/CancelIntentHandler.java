package info.tomfi.alexa.skills.shabbattimes.request.handlers;

import static info.tomfi.alexa.skills.shabbattimes.enums.BundleKeys.DEFAULT_OK;
import static info.tomfi.alexa.skills.shabbattimes.enums.Intents.CANCEL;
import static info.tomfi.alexa.skills.shabbattimes.tools.LocalizationUtils.getFromBundle;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.impl.IntentRequestHandler;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Response;

import java.util.Optional;

import org.springframework.stereotype.Component;

/**
 * Implementation of com.amazon.ask.dispatcher.request.handler.impl.IntentRequestHandler,
 * handles {#value info.tomfi.alexa.skills.shabbattimes.enums.Intents.CANCEL} intent requests.
 *
 * @author Tomer Figenblat {@literal <tomer.figenblat@gmail.com>}
 */
@Component
public final class CancelIntentHandler implements IntentRequestHandler
{
    @Override
    public boolean canHandle(final HandlerInput input, final IntentRequest intent)
    {
        return intent.getIntent().getName().equals(CANCEL.getName());
    }

    @Override
    public Optional<Response> handle(final HandlerInput input, final IntentRequest intent)
    {
        return input.getResponseBuilder()
            .withSpeech(
                getFromBundle(input.getAttributesManager().getRequestAttributes(), DEFAULT_OK)
            )
            .withShouldEndSession(true)
            .build();
    }
}
