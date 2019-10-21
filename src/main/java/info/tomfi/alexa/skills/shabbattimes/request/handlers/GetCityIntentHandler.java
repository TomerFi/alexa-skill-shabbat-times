package info.tomfi.alexa.skills.shabbattimes.request.handlers;

import static info.tomfi.alexa.skills.shabbattimes.enums.Intents.GET_CITY;
import static info.tomfi.alexa.skills.shabbattimes.tools.APITools.getCandlesAndHavdalahItems;
import static info.tomfi.alexa.skills.shabbattimes.tools.DateTimeUtils.getEndDateTime;
import static info.tomfi.alexa.skills.shabbattimes.tools.DateTimeUtils.getShabbatStartLocalDate;
import static info.tomfi.alexa.skills.shabbattimes.tools.DateTimeUtils.getStartDateTime;
import static info.tomfi.alexa.skills.shabbattimes.tools.LocalizationUtils.getEndsAtPresentation;
import static info.tomfi.alexa.skills.shabbattimes.tools.LocalizationUtils.getFromBundle;
import static info.tomfi.alexa.skills.shabbattimes.tools.LocalizationUtils.getStartsAtPresentation;
import static info.tomfi.alexa.skills.shabbattimes.tools.SkillTools.getCityFromSlots;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.impl.IntentRequestHandler;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import info.tomfi.alexa.skills.shabbattimes.api.APIRequestMaker;
import info.tomfi.alexa.skills.shabbattimes.api.response.APIResponse;
import info.tomfi.alexa.skills.shabbattimes.enums.BundleKeys;
import info.tomfi.alexa.skills.shabbattimes.exception.NoCityFoundException;
import info.tomfi.alexa.skills.shabbattimes.exception.NoCityInCountryException;
import info.tomfi.alexa.skills.shabbattimes.exception.NoCitySlotException;
import info.tomfi.alexa.skills.shabbattimes.exception.NoItemFoundForDateException;
import info.tomfi.alexa.skills.shabbattimes.exception.NoResponseFromAPIException;

import lombok.val;

/**
 * Implementation of com.amazon.ask.dispatcher.request.handler.impl.IntentRequestHandler,
 * handles {#value info.tomfi.alexa.skills.shabbattimes.enums.Intents.GET_CITY} intent requests.
 *
 * @author Tomer Figenblat {@literal <tomer.figenblat@gmail.com>}
 */
@Component
public final class GetCityIntentHandler implements IntentRequestHandler
{
    @Autowired private APIRequestMaker requestMaker;

    @Override
    public boolean canHandle(final HandlerInput input, final IntentRequest intent)
    {
        return intent.getIntent().getName().equals(GET_CITY.getName());
    }

    @Override
    public Optional<Response> handle(final HandlerInput input, final IntentRequest intent)
        throws NoCityFoundException, NoCityInCountryException, NoCitySlotException, NoItemFoundForDateException, NoResponseFromAPIException
    {
        val selectedCity = getCityFromSlots(intent.getIntent().getSlots(), input.getAttributesManager());
        val shabbatDate = getShabbatStartLocalDate(intent.getTimestamp().toLocalDate());
        val response = getAPIResponse(selectedCity.getGeoId(), shabbatDate);

        val items = getCandlesAndHavdalahItems(response);
        val shabbatStartDateTime = getStartDateTime(items, shabbatDate);
        val shabbatEndDateTime = getEndDateTime(items, shabbatDate.plusDays(1));
        val currentDateTime = intent.getTimestamp().toZonedDateTime();

        val requestAttributes = input.getAttributesManager().getRequestAttributes();
        val startsAtPresentation = getStartsAtPresentation(requestAttributes, currentDateTime.getDayOfWeek());
        val endsAtPresentation = getEndsAtPresentation(requestAttributes, currentDateTime.getDayOfWeek());

        val speechOutput = String.format(
            getFromBundle(requestAttributes, BundleKeys.SHABBAT_INFORMATION_FMT),
            response.getLocation().getCity(),
            startsAtPresentation,
            shabbatStartDateTime.toLocalDate().toString(),
            shabbatStartDateTime.toLocalTime().toString(),
            endsAtPresentation,
            shabbatEndDateTime.toLocalDate().toString(),
            shabbatEndDateTime.toLocalTime().toString()
        );

        val cardTitle = String.format(getFromBundle(requestAttributes, BundleKeys.SIMPLE_CARD_TITLE_FMT), selectedCity.getGeoName());
        val cardContent = String.format(
            getFromBundle(requestAttributes, BundleKeys.SIMPLE_CARD_CONTENT_FMT),
            shabbatStartDateTime.toLocalDate().toString(),
            shabbatStartDateTime.toLocalTime().toString(),
            shabbatEndDateTime.toLocalDate().toString(),
            shabbatEndDateTime.toLocalTime().toString()
        );

        return input.getResponseBuilder()
            .withSpeech(speechOutput)
            .withReprompt(getFromBundle(requestAttributes, BundleKeys.ASK_FOR_ANOTHER_REPROMPT))
            .withSimpleCard(cardTitle, cardContent)
            .withShouldEndSession(false)
            .build();
    }

    private APIResponse getAPIResponse(final int setGeoId, final LocalDate shabbatDate)
        throws NoResponseFromAPIException
    {
        try
        {
            return requestMaker.setGeoId(setGeoId).setSpecificDate(shabbatDate).send();
        } catch (IllegalStateException | IOException exc)
        {
            throw new NoResponseFromAPIException("no response from hebcal's shabbat api");
        }
    }
}
