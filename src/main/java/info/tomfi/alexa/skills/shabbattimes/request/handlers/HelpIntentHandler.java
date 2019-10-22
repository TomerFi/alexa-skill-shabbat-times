package info.tomfi.alexa.skills.shabbattimes.request.handlers;

import static info.tomfi.alexa.skills.shabbattimes.enums.Intents.HELP;
import static info.tomfi.alexa.skills.shabbattimes.tools.LocalizationUtils.getFromBundle;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.impl.IntentRequestHandler;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Response;

import info.tomfi.alexa.skills.shabbattimes.enums.BundleKeys;

import java.util.Optional;

import lombok.NoArgsConstructor;
import lombok.val;

import org.springframework.stereotype.Component;

/**
 * Implementation of com.amazon.ask.dispatcher.request.handler.impl.IntentRequestHandler,
 * handles {#value info.tomfi.alexa.skills.shabbattimes.enums.Intents.HELP} intent requests.
 *
 * @author Tomer Figenblat {@literal <tomer.figenblat@gmail.com>}
 */
@Component
@NoArgsConstructor
public final class HelpIntentHandler implements IntentRequestHandler
{
    @Override
    public boolean canHandle(final HandlerInput input, final IntentRequest intent)
    {
        return intent.getIntent().getName().equals(HELP.getName());
    }

    @Override
    public Optional<Response> handle(final HandlerInput input, final IntentRequest intent)
    {
        val attributes = input.getAttributesManager().getRequestAttributes();
        return input.getResponseBuilder()
            .withSpeech(getFromBundle(attributes, BundleKeys.HELP_SPEECH))
            .withReprompt(getFromBundle(attributes, BundleKeys.HELP_REPROMPT))
            .withShouldEndSession(false)
            .build();
    }
}
