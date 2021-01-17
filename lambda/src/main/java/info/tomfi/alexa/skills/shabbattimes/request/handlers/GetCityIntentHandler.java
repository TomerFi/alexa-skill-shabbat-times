/**
 * Copyright Tomer Figenblat
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package info.tomfi.alexa.skills.shabbattimes.request.handlers;

import static info.tomfi.alexa.skills.shabbattimes.enums.Intents.GET_CITY;
import static info.tomfi.alexa.skills.shabbattimes.tools.ApiTools.getCandlesAndHavdalahItems;
import static info.tomfi.alexa.skills.shabbattimes.tools.DateTimeUtils.getEndDateTime;
import static info.tomfi.alexa.skills.shabbattimes.tools.DateTimeUtils.getShabbatStartLocalDate;
import static info.tomfi.alexa.skills.shabbattimes.tools.DateTimeUtils.getStartDateTime;
import static info.tomfi.alexa.skills.shabbattimes.tools.LocalizationUtils.getEndsAtPresentation;
import static info.tomfi.alexa.skills.shabbattimes.tools.LocalizationUtils.getFromBundle;
import static info.tomfi.alexa.skills.shabbattimes.tools.LocalizationUtils.getStartsAtPresentation;
import static info.tomfi.alexa.skills.shabbattimes.tools.SkillTools.getCityFromSlots;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.impl.IntentRequestHandler;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Response;
import info.tomfi.alexa.skills.shabbattimes.api.ApiRequestMaker;
import info.tomfi.alexa.skills.shabbattimes.api.response.ApiResponse;
import info.tomfi.alexa.skills.shabbattimes.api.response.items.ResponseItem;
import info.tomfi.alexa.skills.shabbattimes.city.City;
import info.tomfi.alexa.skills.shabbattimes.enums.BundleKeys;
import info.tomfi.alexa.skills.shabbattimes.exception.NoCityFoundException;
import info.tomfi.alexa.skills.shabbattimes.exception.NoCityInCountryException;
import info.tomfi.alexa.skills.shabbattimes.exception.NoCitySlotException;
import info.tomfi.alexa.skills.shabbattimes.exception.NoItemFoundForDateException;
import info.tomfi.alexa.skills.shabbattimes.exception.NoResponseFromApiException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Implementation of com.amazon.ask.dispatcher.request.handler.impl.IntentRequestHandler, handles
 * {#value info.tomfi.alexa.skills.shabbattimes.enums.Intents.GET_CITY} intent requests.
 *
 * @author Tomer Figenblat {@literal <tomer.figenblat@gmail.com>}
 */
@Component
public final class GetCityIntentHandler implements IntentRequestHandler {
  @Autowired private ApiRequestMaker requestMaker;

  public GetCityIntentHandler() {
    //
  }

  @Override
  public boolean canHandle(final HandlerInput input, final IntentRequest intent) {
    return intent.getIntent().getName().equals(GET_CITY.getName());
  }

  @Override
  public Optional<Response> handle(final HandlerInput input, final IntentRequest intent)
      throws NoCityFoundException, NoCityInCountryException, NoCitySlotException,
          NoItemFoundForDateException, NoResponseFromApiException {
    final City selectedCity =
        getCityFromSlots(intent.getIntent().getSlots(), input.getAttributesManager());
    final LocalDate shabbatDate = getShabbatStartLocalDate(intent.getTimestamp().toLocalDate());
    final ApiResponse response;
    try {
      response = requestMaker.setGeoId(selectedCity.getGeoId()).setSpecificDate(shabbatDate).send();
    } catch (IllegalStateException | IOException exc) {
      throw new NoResponseFromApiException("no response from hebcal's shabbat api", exc);
    }

    final List<ResponseItem> items = getCandlesAndHavdalahItems(response);
    final ZonedDateTime shabbatStartDateTime = getStartDateTime(items, shabbatDate);
    final ZonedDateTime shabbatEndDateTime = getEndDateTime(items, shabbatDate.plusDays(1));
    final ZonedDateTime currentDateTime = intent.getTimestamp().toZonedDateTime();

    final Map<String, Object>  requestAttributes = input.getAttributesManager().getRequestAttributes();
    final String startsAtPresentation =
        getStartsAtPresentation(requestAttributes, currentDateTime.getDayOfWeek());
    final String endsAtPresentation =
        getEndsAtPresentation(requestAttributes, currentDateTime.getDayOfWeek());

    final String speechOutput =
        String.format(
            getFromBundle(requestAttributes, BundleKeys.SHABBAT_INFORMATION_FMT),
            response.getLocation().getCity(),
            startsAtPresentation,
            shabbatStartDateTime.toLocalDate().toString(),
            shabbatStartDateTime.toLocalTime().toString(),
            endsAtPresentation,
            shabbatEndDateTime.toLocalDate().toString(),
            shabbatEndDateTime.toLocalTime().toString());

    final String cardTitle =
        String.format(
            getFromBundle(requestAttributes, BundleKeys.SIMPLE_CARD_TITLE_FMT),
            selectedCity.getGeoName());
    final String cardContent =
        String.format(
            getFromBundle(requestAttributes, BundleKeys.SIMPLE_CARD_CONTENT_FMT),
            shabbatStartDateTime.toLocalDate().toString(),
            shabbatStartDateTime.toLocalTime().toString(),
            shabbatEndDateTime.toLocalDate().toString(),
            shabbatEndDateTime.toLocalTime().toString());

    return input
        .getResponseBuilder()
        .withSpeech(speechOutput)
        .withReprompt(getFromBundle(requestAttributes, BundleKeys.ASK_FOR_ANOTHER_REPROMPT))
        .withSimpleCard(cardTitle, cardContent)
        .withShouldEndSession(false)
        .build();
  }
}
