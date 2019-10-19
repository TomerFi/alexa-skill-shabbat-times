package info.tomfi.alexa.skills.shabbattimes.request.handlers;

import static info.tomfi.alexa.skills.shabbattimes.tools.LocalizationUtils.getFromBundle;

import java.util.Optional;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.impl.IntentRequestHandler;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Response;

import org.springframework.stereotype.Component;

import info.tomfi.alexa.skills.shabbattimes.enums.Attributes;
import info.tomfi.alexa.skills.shabbattimes.enums.BundleKeys;
import info.tomfi.alexa.skills.shabbattimes.enums.CountryInfo;
import info.tomfi.alexa.skills.shabbattimes.enums.Intents;

import lombok.val;

@Component
public final class NoIntentHandler implements IntentRequestHandler
{
    @Override
    public boolean canHandle(final HandlerInput input, final IntentRequest intent)
    {
        return intent.getIntent().getName().equals(Intents.NO.getName());
    }

    @Override
    public Optional<Response> handle(final HandlerInput input, final IntentRequest intent)
    {
        val requestAttributes = input.getAttributesManager().getRequestAttributes();
        String speechOutput = getFromBundle(requestAttributes, BundleKeys.DEFAULT_OK);
        try
        {
            val sessionAttributes = input.getAttributesManager().getSessionAttributes();
            if (sessionAttributes.get(Attributes.LAST_INTENT.getName()).equals(Intents.COUNTRY_SELECTED.getName()))
            {
                val attribValue = (String) sessionAttributes.get(Attributes.COUNTRY.getName());
                String speechMiddle = "";
                for (val current : CountryInfo.values())
                {
                    if (attribValue.equals(current.getAbbreviation()))
                    {
                        speechMiddle = getFromBundle(requestAttributes, current.getBundleKey());
                        break;
                    }
                }
                speechOutput = String.format(getFromBundle(requestAttributes, BundleKeys.NOT_FOUND_FMT), speechMiddle);
            }
        } catch (IllegalStateException exc)
        {
        }
        return input.getResponseBuilder()
            .withSpeech(speechOutput)
            .withShouldEndSession(true)
            .build();
    }
}
