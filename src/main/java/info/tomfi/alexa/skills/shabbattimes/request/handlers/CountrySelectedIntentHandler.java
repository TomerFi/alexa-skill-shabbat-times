package info.tomfi.alexa.skills.shabbattimes.request.handlers;

import static info.tomfi.alexa.skills.shabbattimes.tools.GlobalEnums.Attributes;
import static info.tomfi.alexa.skills.shabbattimes.tools.GlobalEnums.Intents;
import static info.tomfi.alexa.skills.shabbattimes.tools.GlobalEnums.Slots;

import java.util.Map;
import java.util.Optional;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.impl.IntentRequestHandler;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.Slot;

import info.tomfi.alexa.skills.shabbattimes.annotation.IncludeRequestHandler;
import info.tomfi.alexa.skills.shabbattimes.country.Country;
import info.tomfi.alexa.skills.shabbattimes.country.CountryFactory;
import info.tomfi.alexa.skills.shabbattimes.exception.NoCountrySlotException;
import info.tomfi.alexa.skills.shabbattimes.exception.NoJsonFileException;

@IncludeRequestHandler
public final class CountrySelectedIntentHandler implements IntentRequestHandler
{
    @Override
    public boolean canHandle(final HandlerInput input, final IntentRequest intent)
    {
        return intent.getIntent().getName().equals(Intents.COUNTRY_SELECTED.name);
    }

    @Override
    public Optional<Response> handle(final HandlerInput input, final IntentRequest intent) throws NoCountrySlotException, NoJsonFileException
    {
        final Slot countrySlot = intent.getIntent().getSlots().get(Slots.COUNTRY.name);
        if (countrySlot == null)
        {
            throw new NoCountrySlotException("No country slot found.");
        }
        final Country country = CountryFactory.getCountry(countrySlot.getValue());

        final Map<String, Object> attribs = input.getAttributesManager().getSessionAttributes();
        attribs.put(Attributes.COUNTRY.name, country.getAbbreviation());
        input.getAttributesManager().setSessionAttributes(attribs);

        return input.getResponseBuilder()
            .withSpeech(String.format("These are the city names I know in %s: %s.", country.getName(), country.getPrettyCityNames()))
            .withReprompt("Please tell me the requested city name.")
            .withShouldEndSession(false)
            .build();
    }

}
