/*
 * Copyright Tomer Figenblat.
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
package info.tomfi.alexa.shabbattimes.handlers.intent;

import static info.tomfi.alexa.shabbattimes.AttributeKey.CITY;
import static info.tomfi.alexa.shabbattimes.AttributeKey.COUNTRY;
import static info.tomfi.alexa.shabbattimes.BundleKey.ASK_FOR_ANOTHER_REPROMPT;
import static info.tomfi.alexa.shabbattimes.BundleKey.SHABBAT_INFORMATION_FMT;
import static info.tomfi.alexa.shabbattimes.BundleKey.SIMPLE_CARD_CONTENT_FMT;
import static info.tomfi.alexa.shabbattimes.BundleKey.SIMPLE_CARD_TITLE_FMT;
import static info.tomfi.alexa.shabbattimes.IntentType.GET_CITY;
import static info.tomfi.alexa.shabbattimes.SlotName.COUNTRY_SLOT;
import static info.tomfi.alexa.shabbattimes.Tools.bumpToFriday;
import static info.tomfi.alexa.shabbattimes.Tools.endAtStmt;
import static info.tomfi.alexa.shabbattimes.Tools.findCitySlot;
import static info.tomfi.alexa.shabbattimes.Tools.strtAtStmt;
import static info.tomfi.hebcal.shabbat.response.ItemCategory.CANDLES;
import static info.tomfi.hebcal.shabbat.response.ItemCategory.HAVDALAH;
import static info.tomfi.hebcal.shabbat.tools.Comparators.byItemDate;
import static info.tomfi.hebcal.shabbat.tools.ItemExtractors.extractAndSort;
import static info.tomfi.hebcal.shabbat.tools.ItemExtractors.extractByDate;
import static java.time.ZonedDateTime.parse;
import static java.util.stream.Collectors.toList;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.impl.IntentRequestHandler;
import com.amazon.ask.model.IntentRequest;
import info.tomfi.alexa.shabbattimes.LocatorService;
import info.tomfi.alexa.shabbattimes.SlotName.CitySlot;
import info.tomfi.alexa.shabbattimes.TextService;
import info.tomfi.alexa.shabbattimes.exceptions.NoItemFoundForDateException;
import info.tomfi.alexa.shabbattimes.exceptions.NoItemsInResponseException;
import info.tomfi.alexa.shabbattimes.exceptions.NoResponseFromApiException;
import info.tomfi.hebcal.shabbat.ShabbatAPI;
import info.tomfi.hebcal.shabbat.request.Request;
import info.tomfi.hebcal.shabbat.response.Response;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import org.springframework.stereotype.Component;

/** Intent request handler for handling intent requests with the name {@value GET_CITY}. */
@Component
public final class GetCityIntentHandler implements IntentRequestHandler {
  private final ShabbatAPI shabbatApi;
  private final LocatorService locator;
  private final TextService textor;

  private final List<String> slotKeys;

  /**
   * Default contructor.
   *
   * @param setShabbatApi the inject shabbat api instance.
   * @param setLocator the injected locator service provider.
   * @param setTextor the injected text service provider.
   */
  public GetCityIntentHandler(
      final ShabbatAPI setShabbatApi,
      final LocatorService setLocator,
      final TextService setTextor) {
    shabbatApi = setShabbatApi;
    locator = setLocator;
    textor = setTextor;
    slotKeys = Arrays.stream(CitySlot.values()).map(Object::toString).collect(toList());
  }

  @Override
  public boolean canHandle(final HandlerInput input, final IntentRequest request) {
    return request.getIntent().getName().equals(GET_CITY.toString());
  }

  @Override
  public Optional<com.amazon.ask.model.Response> handle(
      final HandlerInput input, final IntentRequest request) {
    // get requested city slot if exists
    var slots = request.getIntent().getSlots();
    var citySlot = findCitySlot(slotKeys).apply(slots);
    // locate the requested city incorporating a country if one exists
    var selectedCity =
        slots.containsKey(COUNTRY_SLOT)
            ? locator.locate(slots.get(COUNTRY_SLOT), citySlot)
            : locator.locate(citySlot);
    // save selected city name and country abbreviation as session attributes
    var attribMngr = input.getAttributesManager();
    var sessionAttribs = attribMngr.getSessionAttributes();
    sessionAttribs.putAll(
        Map.of(
            COUNTRY.toString(), selectedCity.countryAbbreviation(),
            CITY.toString(), selectedCity.cityName()));
    attribMngr.setSessionAttributes(sessionAttribs);
    // calculate the current or next friday date
    var shabbatDate = bumpToFriday().apply(request.getTimestamp().toLocalDate());
    // build the api request
    var apiRequest = Request.builder().forGeoId(selectedCity.geoId()).withDate(shabbatDate).build();
    // get the response future
    var responseFuture = shabbatApi.sendAsync(apiRequest);
    // get the response
    final Response response;
    try {
      response = responseFuture.get();
    } catch (IllegalStateException | InterruptedException | ExecutionException exc) {
      throw new NoResponseFromApiException(exc);
    }
    // get candles and havdalah items from response items
    var items =
        extractAndSort(
            response.items().orElseThrow(NoItemsInResponseException::new),
            byItemDate(),
            CANDLES,
            HAVDALAH);
    // calculate the current and the shabbat start and end date and time
    var currentDateTime = request.getTimestamp().toZonedDateTime();
    var shabbatStartDateTime =
        parse(
            extractByDate(items, shabbatDate, CANDLES)
                .orElseThrow(NoItemFoundForDateException::new)
                .date());
    var shabbatEndDateTime =
        parse(
            extractByDate(items, shabbatDate.plusDays(1), HAVDALAH)
                .orElseThrow(NoItemFoundForDateException::new)
                .date());
    // retrieve the request attributes
    var requestAttributes = input.getAttributesManager().getRequestAttributes();
    // construct the start at... and ends at... parts of the prompt
    var startsAtPresentation =
        textor.getText(requestAttributes, strtAtStmt().apply(currentDateTime.getDayOfWeek()));
    var endsAtPresentation =
        textor.getText(requestAttributes, endAtStmt().apply(currentDateTime.getDayOfWeek()));
    // construct the output prompt
    var speechOutput =
        String.format(
            textor.getText(requestAttributes, SHABBAT_INFORMATION_FMT),
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
            textor.getText(requestAttributes, SIMPLE_CARD_TITLE_FMT), selectedCity.geoName());
    var cardContent =
        String.format(
            textor.getText(requestAttributes, SIMPLE_CARD_CONTENT_FMT),
            shabbatStartDateTime.toLocalDate().toString(),
            shabbatStartDateTime.toLocalTime().toString(),
            shabbatEndDateTime.toLocalDate().toString(),
            shabbatEndDateTime.toLocalTime().toString());
    // return requested information and don't end the interaction
    return input
        .getResponseBuilder()
        .withSpeech(speechOutput)
        .withReprompt(textor.getText(requestAttributes, ASK_FOR_ANOTHER_REPROMPT))
        .withSimpleCard(cardTitle, cardContent)
        .withShouldEndSession(false)
        .build();
  }
}
