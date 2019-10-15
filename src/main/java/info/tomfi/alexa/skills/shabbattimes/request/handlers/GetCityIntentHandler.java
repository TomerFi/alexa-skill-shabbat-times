package info.tomfi.alexa.skills.shabbattimes.request.handlers;

import static info.tomfi.alexa.skills.shabbattimes.tools.CityLocator.getByCityAndCountry;
import static info.tomfi.alexa.skills.shabbattimes.tools.DateTimeUtils.getShabbatStartLocalDate;
import static info.tomfi.alexa.skills.shabbattimes.tools.DateTimeUtils.isShabbatEndsToday;
import static info.tomfi.alexa.skills.shabbattimes.tools.DateTimeUtils.isShabbatNow;
import static info.tomfi.alexa.skills.shabbattimes.tools.DateTimeUtils.isShabbatStartsToday;
import static info.tomfi.alexa.skills.shabbattimes.tools.DateTimeUtils.isShabbatStartsTommorow;
import static info.tomfi.alexa.skills.shabbattimes.tools.GlobalEnums.Attributes;
import static info.tomfi.alexa.skills.shabbattimes.tools.GlobalEnums.Intents;
import static info.tomfi.alexa.skills.shabbattimes.tools.GlobalEnums.Slots.CITY_GB;
import static info.tomfi.alexa.skills.shabbattimes.tools.GlobalEnums.Slots.CITY_IL;
import static info.tomfi.alexa.skills.shabbattimes.tools.GlobalEnums.Slots.CITY_US;
import static info.tomfi.alexa.skills.shabbattimes.tools.GlobalEnums.Slots.COUNTRY;

import static info.tomfi.alexa.skills.shabbattimes.tools.APITools.getCandlesAndHavdalahItems;
import static info.tomfi.alexa.skills.shabbattimes.tools.APITools.getShabbatCandlesItem;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZonedDateTime;
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
import info.tomfi.alexa.skills.shabbattimes.api.APIRequestMaker;
import info.tomfi.alexa.skills.shabbattimes.api.response.APIResponse;
import info.tomfi.alexa.skills.shabbattimes.api.response.items.ResponseItem;
import info.tomfi.alexa.skills.shabbattimes.city.City;
import info.tomfi.alexa.skills.shabbattimes.exception.NoCityFoundException;
import info.tomfi.alexa.skills.shabbattimes.exception.NoCityInCountryException;
import info.tomfi.alexa.skills.shabbattimes.exception.NoCitySlotException;
import info.tomfi.alexa.skills.shabbattimes.exception.NoItemFoundForDateExepion;
import info.tomfi.alexa.skills.shabbattimes.exception.NoResponseFromAPIException;

@IncludeRequestHandler
public final class GetCityIntentHandler implements IntentRequestHandler
{
    @Override
    public boolean canHandle(final HandlerInput input, final IntentRequest intent)
    {
        return intent.getIntent().getName().equals(Intents.GET_CITY.name);
    }

    @Override
    public Optional<Response> handle(final HandlerInput input, final IntentRequest intent)
        throws NoCityFoundException, NoCityInCountryException, NoCitySlotException, NoItemFoundForDateExepion, NoResponseFromAPIException
    {
        final Map<String, Slot> slots = intent.getIntent().getSlots();
        final City selectedCity = getByCityAndCountry(slots.get(COUNTRY.name), getCitySlot(slots));

        final Map<String, Object> attribs = input.getAttributesManager().getSessionAttributes();
        attribs.put(Attributes.COUNTRY.name, selectedCity.getCountryAbbreviation());
        attribs.put(Attributes.CITY.name, selectedCity.getCityName());
        input.getAttributesManager().setSessionAttributes(attribs);

        final LocalDate shabbatDate = getShabbatStartLocalDate(intent.getTimestamp().toLocalDate());
        final APIResponse response;
        try
        {
            response = new APIRequestMaker().setGeoId(selectedCity.getGeoId()).setSpecificDate(shabbatDate).send();
        } catch (IllegalStateException | IOException exc)
        {
            throw new NoResponseFromAPIException("no response from hebcal's shabbat api");
        }

        final List<ResponseItem> items = getCandlesAndHavdalahItems(response);
        final Optional<ResponseItem> shabbatStartItem = getShabbatCandlesItem(items, shabbatDate);
        if (!shabbatStartItem.isPresent())
        {
            throw new NoItemFoundForDateExepion("no candles item found for requested date");
        }
        final ResponseItem shabbatEndItem = items.get(items.indexOf(shabbatStartItem.get()) + 1);

        final ZonedDateTime shabbatStartDateTime = ZonedDateTime.parse(shabbatStartItem.get().getDate());
        final ZonedDateTime shabbatEndDateTime = ZonedDateTime.parse(shabbatEndItem.getDate());
        final ZonedDateTime currentDateTime = intent.getTimestamp().toZonedDateTime();

        String speechPart1 = "starts on friday.";
        String speechPart2 = "and ends on saturday.";

        if (isShabbatNow(shabbatStartDateTime, currentDateTime, shabbatEndDateTime))
        {
            if (isShabbatStartsToday(shabbatStartDateTime, currentDateTime))
            {
                speechPart1 = "starts today.";
                speechPart2 = "and ends tomorrow.";
            }
            else if (isShabbatEndsToday(currentDateTime, shabbatEndDateTime))
            {
                speechPart1 = "started yesterday.";
                speechPart2 = "and ends today.";
            }
        }
        else if (isShabbatStartsTommorow(shabbatStartDateTime, currentDateTime))
        {
            speechPart1 = "starts tomorrow.";
        }

        final String speechOutput = String.format(
            "Shabbat times in %s: %s %s, at %s. %s %s, at %s.<break time='500ms'/>Would you like me to get the shabbat times in another city?",
            response.getLocation().getCity(),
            speechPart1,
            shabbatStartDateTime.toLocalDate().toString(),
            shabbatStartDateTime.toLocalTime().toString(),
            speechPart2,
            shabbatEndDateTime.toLocalDate().toString(),
            shabbatEndDateTime.toLocalTime().toString()
        );

        return input.getResponseBuilder()
            .withSpeech(speechOutput)
            .withReprompt("If you're interested in another city, please tell me the city name. For a list of all the possible city names, just ask me for help.")
            .withSimpleCard(String.format("Shabbat times: %s", selectedCity.getGeoName()), speechOutput)
            .withShouldEndSession(false)
            .build();
    }

    private Slot getCitySlot(final Map<String, Slot> slots) throws NoCitySlotException
    {
        final List<String> cityKeys = Arrays.asList(CITY_IL.name, CITY_GB.name, CITY_US.name);
        final Optional<String> slotKey = slots.keySet()
            .stream()
            .filter(key -> cityKeys.contains(key))
            .filter(key -> slots.get(key).getValue() != null)
            .findFirst();

        if (!slotKey.isPresent())
        {
            throw new NoCitySlotException("No city slot found.");
        }
        return slots.get(slotKey.get());
    }
}
