package info.tomfi.alexa.skills.shabbattimes.handlers;

import java.util.Optional;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.impl.IntentRequestHandler;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Response;

import info.tomfi.alexa.skills.shabbattimes.annotation.IncludeHandler;

@IncludeHandler
public final class NoIntentHandler implements IntentRequestHandler
{
    @Override
    public boolean canHandle(final HandlerInput input, final IntentRequest intent)
    {
        return intent.getIntent().getName().equals("AMAZON.NoIntent");
    }

    @Override
    public Optional<Response> handle(final HandlerInput input, final IntentRequest intent)
    {
        String speechOutput = "Ok.";
        try
        {
            if ("CountrySelected".equals(input.getAttributesManager().getPersistentAttributes().get("lastIntent")))
            {
                final String speechPrefix = "I'm sorry. Those are all the city names I know%s";
                switch ((String) input.getAttributesManager().getPersistentAttributes().get("country"))
                {
                    case "US":
                        speechOutput = String.format(speechPrefix, " in the United States! Please try again, Goodbye!");
                        break;
                    case "IL":
                        speechOutput = String.format(speechPrefix, " in Israel! Please try again, Goodbye!");
                        break;
                    case "GB":
                        speechOutput = String.format(speechPrefix, " in the United Kingdom! Please try again, Goodbye!");
                        break;
                    default:
                        speechOutput = String.format(speechPrefix, "! Please try again, Goodbye!");
                        break;
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