package info.tomfi.alexa.skills.shabbattimes.request.handlers;

import static java.util.stream.Collectors.toList;

import static info.tomfi.alexa.skills.shabbattimes.api.enums.ItemCategories.CANDLES;
import static info.tomfi.alexa.skills.shabbattimes.api.enums.ItemCategories.HOLIDAY;
import static info.tomfi.alexa.skills.shabbattimes.tools.CityLocator.getByCityAndCountry;
import static info.tomfi.alexa.skills.shabbattimes.tools.DateTimeUtils.getShabbatStartLocalDate;
import static info.tomfi.alexa.skills.shabbattimes.tools.GlobalEnums.Attributes;
import static info.tomfi.alexa.skills.shabbattimes.tools.GlobalEnums.Intents;
import static info.tomfi.alexa.skills.shabbattimes.tools.GlobalEnums.Slots;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
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
        throws NoCityFoundException, NoCityInCountryException, NoCitySlotException, NoResponseFromAPIException
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
        final APIRequestMaker maker = new APIRequestMaker();
        final APIResponse response;

        try
        {
            response = maker.setGeoId(selectedCity.getGeoId()).setSpecificDate(shabbatDate).send();
        } catch (IllegalStateException | IOException exc)
        {
            throw new NoResponseFromAPIException("no response from hebcal's shabbat api");
        }

        final List<ResponseItem> items = response.getItems().stream()
            .filter(item -> !item.getCategory().equals(HOLIDAY.value))
            .sorted(
                (prevItem, currentItem) ->
                ZonedDateTime.parse(prevItem.getDate(), DateTimeFormatter.ISO_DATE_TIME).compareTo(ZonedDateTime.parse(currentItem.getDate(), DateTimeFormatter.ISO_DATE_TIME))
            ).collect(toList());

        final ResponseItem shabbatStartItem = items.stream()
            .filter(item -> item.getCategory().equals(CANDLES.value))
            .filter(item -> ZonedDateTime.parse(item.getDate(), DateTimeFormatter.ISO_DATE_TIME).toLocalDate().equals(shabbatDate))
            .findFirst()
            .get();

        final ResponseItem shabbatEndItem = items.get(items.indexOf(shabbatStartItem) + 1);

        final ZonedDateTime shabbatStartDateTime = ZonedDateTime.parse(shabbatStartItem.getDate());
        final ZonedDateTime shabbatEndDateTime = ZonedDateTime.parse(shabbatEndItem.getDate());

        String speechPart1 = "starts on friday.";
        String speechPart2 = "and ends on saturday.";

        final ZonedDateTime currentDateTime = intent.getTimestamp().toZonedDateTime();

        if
        (
            currentDateTime.equals(shabbatStartDateTime) ||
            currentDateTime.equals(shabbatEndDateTime) ||
            (currentDateTime.isAfter(shabbatStartDateTime) && currentDateTime.isBefore(shabbatEndDateTime))
        )
        {
            if (currentDateTime.getDayOfWeek().compareTo(shabbatStartDateTime.getDayOfWeek()) == 0)
            {
                speechPart1 = "starts today.";
                speechPart2 = "and ends tomorrow.";
            }
            else if (currentDateTime.getDayOfWeek().compareTo(shabbatEndDateTime.getDayOfWeek()) == 0)
            {
                speechPart1 = "started yesterday.";
                speechPart2 = "and ends today.";
            }
        }
        else if (currentDateTime.getDayOfWeek().compareTo(shabbatStartDateTime.getDayOfWeek().minus(1)) == 0)
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
}
