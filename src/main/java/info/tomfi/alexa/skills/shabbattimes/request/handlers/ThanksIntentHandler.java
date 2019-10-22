package info.tomfi.alexa.skills.shabbattimes.request.handlers;

import static info.tomfi.alexa.skills.shabbattimes.enums.BundleKeys.THANKS_AND_BYE;
import static info.tomfi.alexa.skills.shabbattimes.enums.Intents.THANKS;
import static info.tomfi.alexa.skills.shabbattimes.tools.LocalizationUtils.getFromBundle;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.impl.IntentRequestHandler;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Response;

import java.util.Optional;

import lombok.NoArgsConstructor;

import org.springframework.stereotype.Component;

/**
 * Implementation of com.amazon.ask.dispatcher.request.handler.impl.IntentRequestHandler,
 * handles {#value info.tomfi.alexa.skills.shabbattimes.enums.Intents.THANKS} intent requests.
 *
 * @author Tomer Figenblat {@literal <tomer.figenblat@gmail.com>}
 */
@Component
@NoArgsConstructor
public final class ThanksIntentHandler implements IntentRequestHandler
{
    @Override
    public boolean canHandle(final HandlerInput input, final IntentRequest intent)
    {
        return intent.getIntent().getName().equals(THANKS.getName());
    }

    @Override
    public Optional<Response> handle(final HandlerInput input, final IntentRequest intent)
    {
        return input.getResponseBuilder()
            .withSpeech(getFromBundle(
                input.getAttributesManager().getRequestAttributes(), THANKS_AND_BYE)
            )
            .withShouldEndSession(true)
            .build();
    }
}
