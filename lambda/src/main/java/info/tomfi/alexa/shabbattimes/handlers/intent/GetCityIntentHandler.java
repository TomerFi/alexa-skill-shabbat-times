/**
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
import static info.tomfi.alexa.shabbattimes.BundleKey.SHABBAT_END_SATURDAY;
import static info.tomfi.alexa.shabbattimes.BundleKey.SHABBAT_END_TODAY;
import static info.tomfi.alexa.shabbattimes.BundleKey.SHABBAT_END_TOMORROW;
import static info.tomfi.alexa.shabbattimes.BundleKey.SHABBAT_INFORMATION_FMT;
import static info.tomfi.alexa.shabbattimes.BundleKey.SHABBAT_START_FRIDAY;
import static info.tomfi.alexa.shabbattimes.BundleKey.SHABBAT_START_TODAY;
import static info.tomfi.alexa.shabbattimes.BundleKey.SHABBAT_START_TOMORROW;
import static info.tomfi.alexa.shabbattimes.BundleKey.SHABBAT_START_YESTERDAY;
import static info.tomfi.alexa.shabbattimes.BundleKey.SIMPLE_CARD_CONTENT_FMT;
import static info.tomfi.alexa.shabbattimes.BundleKey.SIMPLE_CARD_TITLE_FMT;
import static info.tomfi.alexa.shabbattimes.IntentType.GET_CITY;
import static info.tomfi.alexa.shabbattimes.SlotName.COUNTRY_SLOT;
import static info.tomfi.hebcal.shabbat.response.ItemCategory.CANDLES;
import static info.tomfi.hebcal.shabbat.response.ItemCategory.HAVDALAH;
import static info.tomfi.hebcal.shabbat.tools.Comparators.byItemDate;
import static info.tomfi.hebcal.shabbat.tools.ItemExtractors.extractAndSort;
import static info.tomfi.hebcal.shabbat.tools.ItemExtractors.extractByDate;
import static java.time.DayOfWeek.FRIDAY;
import static java.time.DayOfWeek.SATURDAY;
import static java.time.DayOfWeek.THURSDAY;
import static java.time.ZonedDateTime.parse;
import static java.util.stream.Collectors.toList;

import com.amazon.ask.attributes.AttributesManager;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.impl.IntentRequestHandler;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Slot;
import info.tomfi.alexa.shabbattimes.BundleKey;
import info.tomfi.alexa.shabbattimes.City;
import info.tomfi.alexa.shabbattimes.LocatorService;
import info.tomfi.alexa.shabbattimes.SlotName.CitySlot;
import info.tomfi.alexa.shabbattimes.TextService;
import info.tomfi.alexa.shabbattimes.exceptions.NoCitySlotException;
import info.tomfi.alexa.shabbattimes.exceptions.NoItemFoundForDateException;
import info.tomfi.alexa.shabbattimes.exceptions.NoItemsInResponse;
import info.tomfi.alexa.shabbattimes.exceptions.NoResponseFromApiException;
import info.tomfi.hebcal.shabbat.ShabbatAPI;
import info.tomfi.hebcal.shabbat.request.Request;
import info.tomfi.hebcal.shabbat.response.Response;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import org.springframework.stereotype.Component;

/**
 * Implementation of com.amazon.ask.dispatcher.request.handler.impl.IntentRequestHandler, handles
 * {#value info.tomfi.shabbattimes.enums.Intents.GET_CITY} intent requests.
 */
@Component
public final class GetCityIntentHandler implements IntentRequestHandler {
  private final ShabbatAPI shabbatApi;
  private final LocatorService locator;
  private final TextService textor;

  private final List<String> slotKeys;

  private UnaryOperator<LocalDate> bumpToFriday =
      d -> {
        var dow = d.getDayOfWeek();
        var add =
            dow.equals(FRIDAY)
                ? 0
                : dow.equals(SATURDAY) ? -1 : FRIDAY.minus(dow.getValue()).getValue();
        return d.plusDays(add);
      };

  private Function<DayOfWeek, BundleKey> endAtStmt =
      d ->
          d.equals(FRIDAY)
              ? SHABBAT_END_TOMORROW
              : d.equals(SATURDAY) ? SHABBAT_END_TODAY : SHABBAT_END_SATURDAY;

  private Function<DayOfWeek, BundleKey> strtAtStmt =
      d ->
          d.equals(THURSDAY)
              ? SHABBAT_START_TOMORROW
              : d.equals(FRIDAY)
                  ? SHABBAT_START_TODAY
                  : d.equals(SATURDAY) ? SHABBAT_START_YESTERDAY : SHABBAT_START_FRIDAY;

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
    // retrieve the requested city
    var selectedCity = getCity(request.getIntent().getSlots(), input.getAttributesManager());
    // calculate the current or next friday date
    var shabbatDate = bumpToFriday.apply(request.getTimestamp().toLocalDate());
    // build the api request
    var apiRequest = Request.builder().forGeoId(selectedCity.geoId()).withDate(shabbatDate).build();
    // get the response future
    var responseFuture = shabbatApi.sendAsync(apiRequest);
    // get the response
    final Response response;
    try {
      response = responseFuture.get();
    } catch (IllegalStateException | InterruptedException | ExecutionException exc) {
      throw new NoResponseFromApiException("no response from hebcal's shabbat api", exc);
    }
    // get candles and havdalah items from response items
    var items =
        extractAndSort(
            response.items().orElseThrow(NoItemsInResponse::new), byItemDate(), CANDLES, HAVDALAH);
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
        textor.getText(requestAttributes, strtAtStmt.apply(currentDateTime.getDayOfWeek()));
    var endsAtPresentation =
        textor.getText(requestAttributes, endAtStmt.apply(currentDateTime.getDayOfWeek()));
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

  private City getCity(final Map<String, Slot> slots, final AttributesManager attribManager) {
    var selectedCity = locator.locate(slots.get(COUNTRY_SLOT), getCitySlot(slots));
    var sessionAttributes = attribManager.getSessionAttributes();
    sessionAttributes.put(COUNTRY.toString(), selectedCity.countryAbbreviation());
    sessionAttributes.put(CITY.toString(), selectedCity.cityName());
    attribManager.setSessionAttributes(sessionAttributes);
    return selectedCity;
  }

  private Slot getCitySlot(final Map<String, Slot> slots) {
    var selectedKey =
        slots.keySet().stream()
            .filter(slotKeys::contains)
            .map(slots::get)
            .map(Slot::getValue)
            .filter(Objects::nonNull)
            .findFirst()
            .orElseThrow(NoCitySlotException::new);
    return slots.get(selectedKey);
  }
}
