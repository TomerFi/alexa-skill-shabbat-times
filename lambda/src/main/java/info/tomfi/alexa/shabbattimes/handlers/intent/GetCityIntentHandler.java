/**
 * Copyright Tomer Figenblat Licensed under the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in
 * writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific
 * language governing permissions and limitations under the License.
 */
package info.tomfi.alexa.shabbattimes.handlers.intent;

import static info.tomfi.alexa.shabbattimes.BundleKey.ASK_FOR_ANOTHER_REPROMPT;
import static info.tomfi.alexa.shabbattimes.BundleKey.SHABBAT_INFORMATION_FMT;
import static info.tomfi.alexa.shabbattimes.BundleKey.SIMPLE_CARD_CONTENT_FMT;
import static info.tomfi.alexa.shabbattimes.BundleKey.SIMPLE_CARD_TITLE_FMT;
import static info.tomfi.alexa.shabbattimes.IntentType.GET_CITY;
import static info.tomfi.alexa.shabbattimes.internal.ApiTools.getCandlesAndHavdalahItems;
import static info.tomfi.alexa.shabbattimes.internal.DateTimeUtils.getEndDateTime;
import static info.tomfi.alexa.shabbattimes.internal.DateTimeUtils.getShabbatStartLocalDate;
import static info.tomfi.alexa.shabbattimes.internal.DateTimeUtils.getStartDateTime;
import static info.tomfi.alexa.shabbattimes.internal.LocalizationUtils.getEndsAtPresentation;
import static info.tomfi.alexa.shabbattimes.internal.LocalizationUtils.getFromBundle;
import static info.tomfi.alexa.shabbattimes.internal.LocalizationUtils.getStartsAtPresentation;
import static info.tomfi.alexa.shabbattimes.internal.SkillTools.getCityFromSlots;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.impl.IntentRequestHandler;
import com.amazon.ask.model.IntentRequest;
import info.tomfi.alexa.shabbattimes.Country;
import info.tomfi.alexa.shabbattimes.exceptions.NoResponseFromApiException;
import info.tomfi.hebcal.shabbat.ShabbatAPI;
import info.tomfi.hebcal.shabbat.request.Request;
import info.tomfi.hebcal.shabbat.response.Response;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import org.springframework.stereotype.Component;

/**
 * Implementation of com.amazon.ask.dispatcher.request.handler.impl.IntentRequestHandler, handles
 * {#value info.tomfi.shabbattimes.enums.Intents.GET_CITY} intent requests.
 */
@Component
public final class GetCityIntentHandler implements IntentRequestHandler {
  private final ShabbatAPI shabbatApi;
  private final List<Country> countries;

  public GetCityIntentHandler(final ShabbatAPI setShabbatApi, final List<Country> setCountries) {
    shabbatApi = setShabbatApi;
    countries = setCountries;
  }

  @Override
  public boolean canHandle(final HandlerInput input, final IntentRequest intent) {
    return intent.getIntent().getName().equals(GET_CITY.toString());
  }

  @Override
  public Optional<com.amazon.ask.model.Response> handle(final HandlerInput input, final IntentRequest intent) {
    // retrieve the requested city
    var selectedCity = getCityFromSlots(
      intent.getIntent().getSlots(), input.getAttributesManager(), countries);
    // calculare the nearest shabbat start date
    var shabbatDate = getShabbatStartLocalDate(intent.getTimestamp().toLocalDate());
    // build the api request
    var request = Request.builder().forGeoId(selectedCity.geoId()).withDate(shabbatDate).build();
    // get the response future
    var responseFuture = shabbatApi.sendAsync(request);
    // get the response
    final Response response;
    try {
      response = responseFuture.get();
    } catch (IllegalStateException | InterruptedException | ExecutionException exc) {
      throw new NoResponseFromApiException("no response from hebcal's shabbat api", exc);
    }
    // get candles and havdalah items from response items
    var items = getCandlesAndHavdalahItems(response);
    // calculate the current and the shabbat start and end date and time
    var currentDateTime = intent.getTimestamp().toZonedDateTime();
    var shabbatStartDateTime = getStartDateTime(items, shabbatDate);
    var shabbatEndDateTime = getEndDateTime(items, shabbatDate.plusDays(1));
    // retrieve the request attributes
    var requestAttributes = input.getAttributesManager().getRequestAttributes();
    // construct the start at... and ends at... parts of the prompt
    var startsAtPresentation = getStartsAtPresentation(requestAttributes, currentDateTime.getDayOfWeek());
    var endsAtPresentation = getEndsAtPresentation(requestAttributes, currentDateTime.getDayOfWeek());
    // construct the output prompt
    var speechOutput =
        String.format(
            getFromBundle(requestAttributes, SHABBAT_INFORMATION_FMT),
            response.location().city(),
            startsAtPresentation,
            shabbatStartDateTime.toLocalDate().toString(),
            shabbatStartDateTime.toLocalTime().toString(),
            endsAtPresentation,
            shabbatEndDateTime.toLocalDate().toString(),
            shabbatEndDateTime.toLocalTime().toString());
    // construct card items
    var cardTitle =
        String.format(
            getFromBundle(requestAttributes, SIMPLE_CARD_TITLE_FMT),
            selectedCity.geoName());
    var cardContent =
        String.format(
            getFromBundle(requestAttributes, SIMPLE_CARD_CONTENT_FMT),
            shabbatStartDateTime.toLocalDate().toString(),
            shabbatStartDateTime.toLocalTime().toString(),
            shabbatEndDateTime.toLocalDate().toString(),
            shabbatEndDateTime.toLocalTime().toString());
    // return requested information and don't end the interaction
    return input.getResponseBuilder()
        .withSpeech(speechOutput)
        .withReprompt(getFromBundle(requestAttributes, ASK_FOR_ANOTHER_REPROMPT))
        .withSimpleCard(cardTitle, cardContent)
        .withShouldEndSession(false)
        .build();
  }
}
