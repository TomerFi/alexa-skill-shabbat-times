package info.tomfi.alexa.skills.shabbattimes.request.handlers;

import static info.tomfi.alexa.skills.shabbattimes.enums.Intents.COUNTRY_SELECTED;
import static info.tomfi.alexa.skills.shabbattimes.tools.LocalizationUtils.getFromBundle;

import java.util.Optional;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.impl.IntentRequestHandler;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Response;

import org.springframework.stereotype.Component;

import info.tomfi.alexa.skills.shabbattimes.country.CountryFactory;
import info.tomfi.alexa.skills.shabbattimes.enums.Attributes;
import info.tomfi.alexa.skills.shabbattimes.enums.BundleKeys;
import info.tomfi.alexa.skills.shabbattimes.enums.Slots;
import info.tomfi.alexa.skills.shabbattimes.exception.NoCountrySlotException;
import info.tomfi.alexa.skills.shabbattimes.exception.NoJsonFileException;
import lombok.val;

/**
 * Implementation of com.amazon.ask.dispatcher.request.handler.impl.IntentRequestHandler,
 * handles {#value info.tomfi.alexa.skills.shabbattimes.enums.Intents.COUNTRY_SELECTED} intent requests.
 *
 * @author Tomer Figenblat {@literal <tomer.figenblat@gmail.com>}
 */
@Component
public final class CountrySelectedIntentHandler implements IntentRequestHandler
{
    @Override
    public boolean canHandle(final HandlerInput input, final IntentRequest intent)
    {
        return intent.getIntent().getName().equals(COUNTRY_SELECTED.getName());
    }

    @Override
    public Optional<Response> handle(final HandlerInput input, final IntentRequest intent)
        throws NoCountrySlotException, NoJsonFileException
    {
        val countrySlot = intent.getIntent().getSlots().get(Slots.COUNTRY.getName());
        if (countrySlot.getValue() == null)
        {
            throw new NoCountrySlotException("No country slot found.");
        }
        val country = CountryFactory.getCountry(countrySlot.getValue());

        val sessionAttributes = input.getAttributesManager().getSessionAttributes();
        sessionAttributes.put(Attributes.COUNTRY.getName(), country.getAbbreviation());
        input.getAttributesManager().setSessionAttributes(sessionAttributes);

        val requestAttributes = input.getAttributesManager().getRequestAttributes();
        return input.getResponseBuilder()
            .withSpeech(
                String.format(
                    getFromBundle(requestAttributes, BundleKeys.CITIES_IN_COUNTRY_FMT),
                    country.getName(),
                    country.getPrettyCityNames()
                )
            )
            .withReprompt(getFromBundle(requestAttributes, BundleKeys.DEFAULT_ASK_FOR_CITY))
            .withShouldEndSession(false)
            .build();
    }

}
