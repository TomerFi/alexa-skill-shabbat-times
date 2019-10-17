package info.tomfi.alexa.skills.shabbattimes.request.handlers;

import static info.tomfi.alexa.skills.shabbattimes.tools.APITools.getCandlesAndHavdalahItems;
import static info.tomfi.alexa.skills.shabbattimes.tools.APITools.getShabbatCandlesItem;
import static info.tomfi.alexa.skills.shabbattimes.tools.CityLocator.getByCityAndCountry;
import static info.tomfi.alexa.skills.shabbattimes.tools.DateTimeUtils.getShabbatStartLocalDate;
import static info.tomfi.alexa.skills.shabbattimes.tools.DateTimeUtils.isShabbatEndsToday;
import static info.tomfi.alexa.skills.shabbattimes.tools.DateTimeUtils.isShabbatNow;
import static info.tomfi.alexa.skills.shabbattimes.tools.DateTimeUtils.isShabbatStartsToday;
import static info.tomfi.alexa.skills.shabbattimes.tools.DateTimeUtils.isShabbatStartsTommorow;
import static info.tomfi.alexa.skills.shabbattimes.tools.LocalizationUtils.getBundleFromAttribures;
import static info.tomfi.alexa.skills.shabbattimes.tools.LocalizationUtils.getFromBundle;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.impl.IntentRequestHandler;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.Slot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import info.tomfi.alexa.skills.shabbattimes.api.APIRequestMaker;
import info.tomfi.alexa.skills.shabbattimes.api.response.APIResponse;
import info.tomfi.alexa.skills.shabbattimes.api.response.items.ResponseItem;
import info.tomfi.alexa.skills.shabbattimes.city.City;
import info.tomfi.alexa.skills.shabbattimes.enums.Attributes;
import info.tomfi.alexa.skills.shabbattimes.enums.BundleKeys;
import info.tomfi.alexa.skills.shabbattimes.enums.Intents;
import info.tomfi.alexa.skills.shabbattimes.enums.Slots;
import info.tomfi.alexa.skills.shabbattimes.exception.NoCityFoundException;
import info.tomfi.alexa.skills.shabbattimes.exception.NoCityInCountryException;
import info.tomfi.alexa.skills.shabbattimes.exception.NoCitySlotException;
import info.tomfi.alexa.skills.shabbattimes.exception.NoItemFoundForDateExepion;
import info.tomfi.alexa.skills.shabbattimes.exception.NoResponseFromAPIException;

import lombok.Setter;

@Component
public final class GetCityIntentHandler implements IntentRequestHandler
{
    @Setter @Autowired private APIRequestMaker requestMaker;

    @Override
    public boolean canHandle(final HandlerInput input, final IntentRequest intent)
    {
        return intent.getIntent().getName().equals(Intents.GET_CITY.getName());
    }

    @Override
    public Optional<Response> handle(final HandlerInput input, final IntentRequest intent)
        throws NoCityFoundException, NoCityInCountryException, NoCitySlotException, NoItemFoundForDateExepion, NoResponseFromAPIException
    {
        final Map<String, Slot> slots = intent.getIntent().getSlots();
        final City selectedCity = getByCityAndCountry(slots.get(Slots.COUNTRY.getName()), getCitySlot(slots));

        final Map<String, Object> attribs = input.getAttributesManager().getSessionAttributes();
        attribs.put(Attributes.COUNTRY.getName(), selectedCity.getCountryAbbreviation());
        attribs.put(Attributes.CITY.getName(), selectedCity.getCityName());
        input.getAttributesManager().setSessionAttributes(attribs);

        final LocalDate shabbatDate = getShabbatStartLocalDate(intent.getTimestamp().toLocalDate());
        final APIResponse response;
        try
        {
            response = requestMaker.setGeoId(selectedCity.getGeoId()).setSpecificDate(shabbatDate).send();
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

        final ResourceBundle bundle = getBundleFromAttribures(input.getAttributesManager().getRequestAttributes());

        String speechPart1 = getFromBundle(bundle, BundleKeys.SHABBAT_START_FRIDAY);
        String speechPart2 = getFromBundle(bundle, BundleKeys.SHABBAT_END_SATURDAY);

        if (isShabbatNow(shabbatStartDateTime, currentDateTime, shabbatEndDateTime))
        {
            if (isShabbatStartsToday(shabbatStartDateTime, currentDateTime))
            {
                speechPart1 = getFromBundle(bundle, BundleKeys.SHABBAT_START_TODAY);
                speechPart2 = getFromBundle(bundle, BundleKeys.SHABBAT_END_TOMORROW);
            }
            else if (isShabbatEndsToday(currentDateTime, shabbatEndDateTime))
            {
                speechPart1 = getFromBundle(bundle, BundleKeys.SHABBAT_START_YESTERDAY);
                speechPart2 = getFromBundle(bundle, BundleKeys.SHABBAT_END_TODAY);
            }
        }
        else if (isShabbatStartsTommorow(shabbatStartDateTime, currentDateTime))
        {
            speechPart1 = getFromBundle(bundle, BundleKeys.SHABBAT_START_TOMORROW);
        }

        final String speechOutput = String.format(
            getFromBundle(bundle, BundleKeys.SHABBAT_INFORMATION_FMT),
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
            .withReprompt(getFromBundle(bundle, BundleKeys.ASK_FOR_ANOTHER_REPROMPT))
            .withSimpleCard(String.format("Shabbat times: %s", selectedCity.getGeoName()), speechOutput)
            .withShouldEndSession(false)
            .build();
    }

    private Slot getCitySlot(final Map<String, Slot> slots) throws NoCitySlotException
    {
        final List<String> cityKeys = Arrays.asList(Slots.CITY_IL.getName(), Slots.CITY_GB.getName(), Slots.CITY_US.getName());
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
