package info.tomfi.alexa.skills.shabbattimes.request.handlers;

import static info.tomfi.alexa.skills.shabbattimes.tools.GlobalEnums.Attributes;
import static info.tomfi.alexa.skills.shabbattimes.tools.GlobalEnums.CountryInfo;
import static info.tomfi.alexa.skills.shabbattimes.tools.GlobalEnums.Intents;

import java.util.Optional;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.impl.IntentRequestHandler;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Response;

import info.tomfi.alexa.skills.shabbattimes.annotation.IncludeRequestHandler;

@IncludeRequestHandler
public final class NoIntentHandler implements IntentRequestHandler
{
    @Override
    public boolean canHandle(final HandlerInput input, final IntentRequest intent)
    {
        return intent.getIntent().getName().equals(Intents.NO.name);
    }

    @Override
    public Optional<Response> handle(final HandlerInput input, final IntentRequest intent)
    {
        String speechOutput = "Ok.";
        try
        {
            if (input.getAttributesManager().getPersistentAttributes().get(Attributes.LAST_INTENT.name).equals(Intents.COUNTRY_SELECTED.name))
            {
                final String speechPrefix = "I'm sorry. Those are all the city names I know%s";
                final String attribValue = (String) input.getAttributesManager().getSessionAttributes().get(Attributes.COUNTRY.name);
                if (attribValue.equals(CountryInfo.UNITED_STATES.abbreviation))
                {
                    speechOutput = String.format(speechPrefix, " in the United States! Please try again, Goodbye!");
                }
                else if (attribValue.equals(CountryInfo.ISRAEL.abbreviation))
                {
                    speechOutput = String.format(speechPrefix, " in Israel! Please try again, Goodbye!");
                }
                else if (attribValue.equals(CountryInfo.UNITED_KINGDOM.abbreviation))
                {
                    speechOutput = String.format(speechPrefix, " in the United Kingdom! Please try again, Goodbye!");
                }
                else
                {
                    speechOutput = String.format(speechPrefix, "! Please try again, Goodbye!");
                }
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
