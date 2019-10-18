package info.tomfi.alexa.skills.shabbattimes.request.handlers;

import static info.tomfi.alexa.skills.shabbattimes.tools.APITools.getCandlesAndHavdalahItems;
import static info.tomfi.alexa.skills.shabbattimes.tools.APITools.getShabbatCandlesItem;
import static info.tomfi.alexa.skills.shabbattimes.tools.CityLocator.getByCityAndCountry;
import static info.tomfi.alexa.skills.shabbattimes.tools.DateTimeUtils.getShabbatStartLocalDate;
import static info.tomfi.alexa.skills.shabbattimes.tools.DateTimeUtils.isShabbatEndsToday;
import static info.tomfi.alexa.skills.shabbattimes.tools.DateTimeUtils.isShabbatNow;
import static info.tomfi.alexa.skills.shabbattimes.tools.DateTimeUtils.isShabbatStartsToday;
import static info.tomfi.alexa.skills.shabbattimes.tools.DateTimeUtils.isShabbatStartsTommorow;
import static info.tomfi.alexa.skills.shabbattimes.tools.LocalizationUtils.getFromBundle;
import static info.tomfi.alexa.skills.shabbattimes.tools.SkillTools.getCitySlotFromMap;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.Optional;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.impl.IntentRequestHandler;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import info.tomfi.alexa.skills.shabbattimes.api.APIRequestMaker;
import info.tomfi.alexa.skills.shabbattimes.api.response.APIResponse;
import info.tomfi.alexa.skills.shabbattimes.enums.Attributes;
import info.tomfi.alexa.skills.shabbattimes.enums.BundleKeys;
import info.tomfi.alexa.skills.shabbattimes.enums.Intents;
import info.tomfi.alexa.skills.shabbattimes.enums.Slots;
import info.tomfi.alexa.skills.shabbattimes.exception.NoCityFoundException;
import info.tomfi.alexa.skills.shabbattimes.exception.NoCityInCountryException;
import info.tomfi.alexa.skills.shabbattimes.exception.NoCitySlotException;
import info.tomfi.alexa.skills.shabbattimes.exception.NoItemFoundForDateException;
import info.tomfi.alexa.skills.shabbattimes.exception.NoResponseFromAPIException;

import lombok.Setter;
import lombok.val;

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
        throws NoCityFoundException, NoCityInCountryException, NoCitySlotException, NoItemFoundForDateException, NoResponseFromAPIException
    {
        val slots = intent.getIntent().getSlots();
        val selectedCity = getByCityAndCountry(slots.get(Slots.COUNTRY.getName()), getCitySlotFromMap(slots));

        val sessionAttributes = input.getAttributesManager().getSessionAttributes();
        sessionAttributes.put(Attributes.COUNTRY.getName(), selectedCity.getCountryAbbreviation());
        sessionAttributes.put(Attributes.CITY.getName(), selectedCity.getCityName());
        input.getAttributesManager().setSessionAttributes(sessionAttributes);

        val shabbatDate = getShabbatStartLocalDate(intent.getTimestamp().toLocalDate());
        final APIResponse response;
        try
        {
            response = requestMaker.setGeoId(selectedCity.getGeoId()).setSpecificDate(shabbatDate).send();
        } catch (IllegalStateException | IOException exc)
        {
            throw new NoResponseFromAPIException("no response from hebcal's shabbat api");
        }

        val items = getCandlesAndHavdalahItems(response);
        val shabbatStartItem = getShabbatCandlesItem(items, shabbatDate);
        if (!shabbatStartItem.isPresent())
        {
            throw new NoItemFoundForDateException("no candles item found for requested date");
        }
        val shabbatEndItem = items.get(items.indexOf(shabbatStartItem.get()) + 1);

        val shabbatStartDateTime = ZonedDateTime.parse(shabbatStartItem.get().getDate());
        val shabbatEndDateTime = ZonedDateTime.parse(shabbatEndItem.getDate());
        val currentDateTime = intent.getTimestamp().toZonedDateTime();

        val requestAttributes = input.getAttributesManager().getRequestAttributes();
        String speechPart1 = getFromBundle(requestAttributes, BundleKeys.SHABBAT_START_FRIDAY);
        String speechPart2 = getFromBundle(requestAttributes, BundleKeys.SHABBAT_END_SATURDAY);

        if (isShabbatNow(shabbatStartDateTime, currentDateTime, shabbatEndDateTime))
        {
            if (isShabbatStartsToday(shabbatStartDateTime, currentDateTime))
            {
                speechPart1 = getFromBundle(requestAttributes, BundleKeys.SHABBAT_START_TODAY);
                speechPart2 = getFromBundle(requestAttributes, BundleKeys.SHABBAT_END_TOMORROW);
            }
            else if (isShabbatEndsToday(currentDateTime, shabbatEndDateTime))
            {
                speechPart1 = getFromBundle(requestAttributes, BundleKeys.SHABBAT_START_YESTERDAY);
                speechPart2 = getFromBundle(requestAttributes, BundleKeys.SHABBAT_END_TODAY);
            }
        }
        else if (isShabbatStartsTommorow(shabbatStartDateTime, currentDateTime))
        {
            speechPart1 = getFromBundle(requestAttributes, BundleKeys.SHABBAT_START_TOMORROW);
        }

        val speechOutput = String.format(
            getFromBundle(requestAttributes, BundleKeys.SHABBAT_INFORMATION_FMT),
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
            .withReprompt(getFromBundle(requestAttributes, BundleKeys.ASK_FOR_ANOTHER_REPROMPT))
            .withSimpleCard(String.format("Shabbat times: %s", selectedCity.getGeoName()), speechOutput)
            .withShouldEndSession(false)
            .build();
    }
}
