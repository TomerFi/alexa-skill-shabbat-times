package info.tomfi.alexa.skills.shabbattimes.request.handlers;

import static info.tomfi.alexa.skills.shabbattimes.tools.LocalizationUtils.getBundleFromAttribures;
import static info.tomfi.alexa.skills.shabbattimes.tools.LocalizationUtils.getFromBundle;

import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.impl.IntentRequestHandler;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Response;

import org.springframework.stereotype.Component;

import info.tomfi.alexa.skills.shabbattimes.enums.Attributes;
import info.tomfi.alexa.skills.shabbattimes.enums.BundleKeys;
import info.tomfi.alexa.skills.shabbattimes.enums.CountryInfo;
import info.tomfi.alexa.skills.shabbattimes.enums.Intents;

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
        final ResourceBundle bundle = getBundleFromAttribures(input.getAttributesManager().getRequestAttributes());
        String speechOutput = bundle.getString(BundleKeys.DEFAULT_OK.toString());
        try
        {
            final Map<String, Object> sessionAttributes = input.getAttributesManager().getSessionAttributes();
            if (sessionAttributes.get(Attributes.LAST_INTENT.getName()).equals(Intents.COUNTRY_SELECTED.getName()))
            {
                final String attribValue = (String) sessionAttributes.get(Attributes.COUNTRY.getName());
                String speechMiddle = "";
                for (CountryInfo current : CountryInfo.values())
                {
                    if (attribValue.equals(current.getAbbreviation()))
                    {
                        speechMiddle = getFromBundle(bundle, current.getBundleKey());
                        break;
                    }
                }
                speechOutput = String.format(getFromBundle(bundle, BundleKeys.NOT_FOUND_FMT), speechMiddle);
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
