package info.tomfi.alexa.skills.shabbattimes.request.handlers;

import static info.tomfi.alexa.skills.shabbattimes.tools.CityLocator.getByCityAndCountry;
import static info.tomfi.alexa.skills.shabbattimes.tools.DateTimeUtils.getShabbatStartLocalDate;
import static info.tomfi.alexa.skills.shabbattimes.tools.GlobalEnums.Attributes;
import static info.tomfi.alexa.skills.shabbattimes.tools.GlobalEnums.Intents;
import static info.tomfi.alexa.skills.shabbattimes.tools.GlobalEnums.Slots;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.impl.IntentRequestHandler;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.Slot;

import info.tomfi.alexa.skills.shabbattimes.annotation.IncludeRequestHandler;
import info.tomfi.alexa.skills.shabbattimes.city.City;
import info.tomfi.alexa.skills.shabbattimes.exception.NoCityFoundException;
import info.tomfi.alexa.skills.shabbattimes.exception.NoCityInCountryException;
import info.tomfi.alexa.skills.shabbattimes.exception.NoCitySlotException;

@IncludeRequestHandler
public final class GetCityIntentHandler implements IntentRequestHandler
{
    @Override
    public boolean canHandle(final HandlerInput input, final IntentRequest intent)
    {
        return intent.getIntent().getName().equals(Intents.GET_CITY.name);
    }

    @Override
    public Optional<Response> handle(final HandlerInput input, final IntentRequest intent) throws NoCitySlotException, NoCityInCountryException, NoCityFoundException
    {
        final List<String> cityKeys = Arrays.asList(Slots.CITY_IL.name, Slots.CITY_GB.name, Slots.CITY_US.name);
        final Map<String, Slot> slots = intent.getIntent().getSlots();
        final Optional<String> slotKey = slots.keySet()
            .stream()
            .filter(key -> cityKeys.contains(key))
            .filter(key -> slots.get(key).getValue() != null)
            .findFirst();

            if (!slotKey.isPresent())
        {
            throw new NoCitySlotException("No city slot found.");
        }
        final City selectedCity = getByCityAndCountry(slots.get(Slots.COUNTRY.name), slots.get(slotKey.get()));

        final Map<String, Object> attribs = input.getAttributesManager().getSessionAttributes();
        attribs.put(Attributes.COUNTRY.name, selectedCity.getCountryAbbreviation());
        attribs.put(Attributes.CITY.name, selectedCity.getCityName());
        attribs.put(Attributes.GEOID.name, selectedCity.getGeoId());
        attribs.put(Attributes.GEONAME.name, selectedCity.getGeoName());
        input.getAttributesManager().setSessionAttributes(attribs);

        final LocalDate shabbatDate = getShabbatStartLocalDate(intent.getTimestamp().toLocalDate());


        String hebCalUrl = "http://www.hebcal.com/hebcal/?v=1&cfg=json&maj=off&min=off&mod=off&nx=off&year=%s&month=%s&ss=off&mf=off&c=on&geo=geoname&geonameid=%s&m=0&s=off";

        hebCalUrl = String.format(
            hebCalUrl, String.valueOf(shabbatDate.getYear()),
            String.format("0%d", shabbatDate.getMonth().getValue()).substring(0, 1),
            selectedCity.getGeoId()
        );

        // TODO
        return input.getResponseBuilder()
            .withSpeech("TODO found city shabbat is " + shabbatDate)
            .withShouldEndSession(true)
            .build();
    }
}
