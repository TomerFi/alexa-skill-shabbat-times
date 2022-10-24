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
import static info.tomfi.alexa.shabbattimes.BundleKey.SHABBAT_END_TOMORROW;
import static info.tomfi.alexa.shabbattimes.BundleKey.SHABBAT_INFORMATION_FMT;
import static info.tomfi.alexa.shabbattimes.BundleKey.SHABBAT_START_TODAY;
import static info.tomfi.alexa.shabbattimes.BundleKey.SIMPLE_CARD_CONTENT_FMT;
import static info.tomfi.alexa.shabbattimes.BundleKey.SIMPLE_CARD_TITLE_FMT;
import static info.tomfi.alexa.shabbattimes.IntentType.GET_CITY;
import static info.tomfi.alexa.shabbattimes.SlotName.COUNTRY_SLOT;
import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenExceptionOfType;
import static org.junit.jupiter.params.provider.EnumSource.Mode.EXCLUDE;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import com.amazon.ask.model.Intent;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Slot;
import com.amazon.ask.response.ResponseBuilder;
import com.github.javafaker.Faker;
import info.tomfi.alexa.shabbattimes.City;
import info.tomfi.alexa.shabbattimes.IntentType;
import info.tomfi.alexa.shabbattimes.LocatorService;
import info.tomfi.alexa.shabbattimes.SlotName.CitySlot;
import info.tomfi.alexa.shabbattimes.exceptions.NoCitySlotException;
import info.tomfi.alexa.shabbattimes.exceptions.NoResponseFromApiException;
import info.tomfi.alexa.shabbattimes.handlers.HandlerFixtures;
import info.tomfi.shabbat.APIRequest;
import info.tomfi.shabbat.APIResponse;
import info.tomfi.shabbat.ShabbatAPI;
import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.ArgumentMatcher;
import org.mockito.InjectMocks;
import org.mockito.Mock;

/** Test cases for the GetCityIntentHandler intent request handler. */
@Tag("unit-tests")
final class GetCityIntentHandlerTest extends HandlerFixtures {
  @Mock private IntentRequest request;
  @Mock private Intent intent;
  @Mock private ShabbatAPI sapi;
  @Mock private LocatorService locator;
  @InjectMocks private GetCityIntentHandler sut;
  private Faker faker;

  @BeforeEach
  void intiailize() {
    faker = new Faker();
  }

  @Test
  void can_handle_should_return_true_for_get_city_intent_type() {
    // stub intent with GET_CITY as name and stub request with intnet
    given(intent.getName()).willReturn(GET_CITY.toString());
    given(request.getIntent()).willReturn(intent);
    // verify handler can handle
    then(sut.canHandle(input, request)).isTrue();
  }

  @ParameterizedTest
  @EnumSource(mode = EXCLUDE, names = "GET_CITY")
  void can_handle_should_return_false_for_non_get_city_intent_type(final IntentType intentType) {
    // stub intent with intent type as name and stub request with intnet
    given(intent.getName()).willReturn(intentType.toString());
    given(request.getIntent()).willReturn(intent);
    // verify handler cannot handle
    then(sut.canHandle(input, request)).isFalse();
  }

  @Test
  void invoking_handler_with_no_city_slot_throws_no_city_slot_exception() {
    // stub intnet with empty slot map and stub request with intent
    var slots = new HashMap<String, Slot>();
    given(intent.getSlots()).willReturn(slots);
    given(request.getIntent()).willReturn(intent);
    // verify exception throws
    thenExceptionOfType(NoCitySlotException.class).isThrownBy(() -> sut.handle(input, request));
  }

  @ParameterizedTest
  @EnumSource
  void invoking_handler_with_null_valued_city_slot_throws_no_city_slot_exception(
      final CitySlot slotName, @Mock final Slot citySlot) {
    // stub intnet with empty slot map and stub request with intent
    var slots = Map.of(slotName.toString(), citySlot);
    given(intent.getSlots()).willReturn(slots);
    given(request.getIntent()).willReturn(intent);
    // verify exception throws
    thenExceptionOfType(NoCitySlotException.class).isThrownBy(() -> sut.handle(input, request));
  }

  @ParameterizedTest
  @EnumSource
  void invoking_handler_with_city_slot_api_fail_throws_no_response_from_api_exception(
      final CitySlot slotName, @Mock final Slot citySlot, @Mock final City city) {
    // stub city mock
    var countryAbbreviation = faker.country().countryCode2();
    var cityName = faker.country().capital();
    var cityGeoid = (int) faker.number().randomNumber(5, true);
    given(city.countryAbbreviation()).willReturn(countryAbbreviation);
    given(city.cityName()).willReturn(cityName);
    given(city.geoId()).willReturn(cityGeoid);
    // stub slot value with city name
    given(citySlot.getValue()).willReturn(cityName);
    // create slot map with city slot and stub it to the request
    var slots = Map.of(slotName.toString(), citySlot);
    given(intent.getSlots()).willReturn(slots);
    given(request.getIntent()).willReturn(intent);
    // stub empty session attributes map to attributes manager
    var sessionAttribs = new HashMap<String, Object>();
    given(attribMngr.getSessionAttributes()).willReturn(sessionAttribs);
    // stub locator to return mock city for locating by mock slot
    given(locator.locate(eq(citySlot))).willReturn(city);
    // stub request with a random friday offset date and time
    var fridayTimestamp = OffsetDateTime.parse("2019-10-04T10:20:30Z");
    given(request.getTimestamp()).willReturn(fridayTimestamp);
    // stub shabbat api with future and complete the future exceptionally to mimic api fail
    var future = new CompletableFuture<APIResponse>();
    future.completeExceptionally(new CompletionException(new Throwable()));
    given(sapi.sendAsync(argThat(verifyRequest(cityGeoid, fridayTimestamp)))).willReturn(future);
    // when invoking the handler verify exception is thrown
    thenExceptionOfType(NoResponseFromApiException.class)
        .isThrownBy(() -> sut.handle(input, request));
    // verify city information saved as session attributes
    verify(attribMngr)
        .setSessionAttributes(
            argThat(
                m ->
                    m.get(COUNTRY.toString()).equals(countryAbbreviation)
                        && m.get(CITY.toString()).equals(cityName)));
    then(sessionAttribs).hasEntrySatisfying(COUNTRY.toString(), o -> o.equals(countryAbbreviation));
    then(sessionAttribs).hasEntrySatisfying(CITY.toString(), o -> o.equals(cityName));
  }

  @ParameterizedTest
  @EnumSource
  void invoking_handler_with_city_and_country_slots_api_fail_throws_no_response_from_api_exception(
      final CitySlot slotName,
      @Mock final Slot citySlot,
      @Mock final City city,
      @Mock final Slot countrySlot) {
    // stub city mock
    var countryAbbreviation = faker.country().countryCode2();
    var cityName = faker.country().capital();
    var cityGeoid = (int) faker.number().randomNumber(5, true);
    given(city.countryAbbreviation()).willReturn(countryAbbreviation);
    given(city.cityName()).willReturn(cityName);
    given(city.geoId()).willReturn(cityGeoid);
    // stub slot value with city name
    given(citySlot.getValue()).willReturn(cityName);
    // create slot map with city and country slot and stub it to the request
    var slots = Map.of(slotName.toString(), citySlot, COUNTRY_SLOT, countrySlot);
    given(intent.getSlots()).willReturn(slots);
    given(request.getIntent()).willReturn(intent);
    // stub empty session attributes map to attributes manager
    var sessionAttribs = new HashMap<String, Object>();
    given(attribMngr.getSessionAttributes()).willReturn(sessionAttribs);
    // stub locator to return mock city for locating by mock city and country slots
    given(locator.locate(eq(countrySlot), eq(citySlot))).willReturn(city);
    // stub request with a random friday offset date and time
    var fridayTimestamp = OffsetDateTime.parse("2019-10-04T10:20:30Z");
    given(request.getTimestamp()).willReturn(fridayTimestamp);
    // stub shabbat api with future and complete the future exceptionally to mimic api fail
    var future = new CompletableFuture<APIResponse>();
    future.completeExceptionally(new CompletionException(new Throwable()));
    given(sapi.sendAsync(argThat(verifyRequest(cityGeoid, fridayTimestamp)))).willReturn(future);
    // when invoking the handler verify exception is thrown
    thenExceptionOfType(NoResponseFromApiException.class)
        .isThrownBy(() -> sut.handle(input, request));
    // verify city information saved as session attributes
    verify(attribMngr)
        .setSessionAttributes(
            argThat(
                m ->
                    m.get(COUNTRY.toString()).equals(countryAbbreviation)
                        && m.get(CITY.toString()).equals(cityName)));
    then(sessionAttribs).hasEntrySatisfying(COUNTRY.toString(), o -> o.equals(countryAbbreviation));
    then(sessionAttribs).hasEntrySatisfying(CITY.toString(), o -> o.equals(cityName));
  }

  @ParameterizedTest
  @EnumSource
  // CHECKSTYLE.OFF: ParameterNumber
  void invoking_handler_api_response_with_candles_and_havdalah_item_return_a_follow_up(
      final CitySlot slotName,
      @Mock final Slot citySlot,
      @Mock final City city,
      @Mock final Slot countrySlot,
      @Mock final APIResponse apiResponse,
      @Mock final APIResponse.Item holidayItem,
      @Mock final APIResponse.Item candlesItem,
      @Mock final APIResponse.Item havdalahItem,
      @Mock final APIResponse.Location location,
      @Mock final ResponseBuilder builder,
      @Mock final com.amazon.ask.model.Response skillResponse)
      throws NoSuchFieldException, SecurityException, IllegalArgumentException,
          IllegalAccessException {
    // stub city mock
    var countryAbbreviation = faker.country().countryCode2();
    var cityName = faker.country().capital();
    var cityGeoid = (int) faker.number().randomNumber(5, true);
    var cityGeoName = faker.lorem().word();
    given(city.countryAbbreviation()).willReturn(countryAbbreviation);
    given(city.cityName()).willReturn(cityName);
    given(city.geoId()).willReturn(cityGeoid);
    given(city.geoName()).willReturn(cityGeoName);
    // stub slot value with city name
    given(citySlot.getValue()).willReturn(cityName);
    // create slot map with city and country slot and stub it to the request
    var slots = Map.of(slotName.toString(), citySlot, COUNTRY_SLOT, countrySlot);
    given(intent.getSlots()).willReturn(slots);
    given(request.getIntent()).willReturn(intent);
    // stub empty session attributes map to attributes manager
    var sessionAttribs = new HashMap<String, Object>();
    given(attribMngr.getSessionAttributes()).willReturn(sessionAttribs);
    // stub locator to return mock city for locating by mock city and country slots
    given(locator.locate(eq(countrySlot), eq(citySlot))).willReturn(city);
    // stub request with a random friday offset date and time
    var fridayTimestamp = OffsetDateTime.parse("2019-10-04T10:20:30Z");
    given(request.getTimestamp()).willReturn(fridayTimestamp);
    // stub response with response items
    setFinalField(holidayItem, "category", APIResponse.Item.Category.HOLIDAY);
    setFinalField(candlesItem, "category", APIResponse.Item.Category.CANDLES);
    setFinalField(candlesItem, "date", "2019-10-04T18:04:00+03:00");
    setFinalField(havdalahItem, "category", APIResponse.Item.Category.HAVDALAH);
    setFinalField(havdalahItem, "date", "2019-10-05T19:11:00+03:00");
    setFinalField(
        apiResponse, "items", Optional.of(List.of(holidayItem, candlesItem, havdalahItem)));
    setFinalField(candlesItem, "category", APIResponse.Item.Category.CANDLES);
    // stub response with response location
    setFinalField(location, "city", cityName);
    setFinalField(apiResponse, "location", location);
    // stub the response utility methods
    given(apiResponse.getShabbatStart()).willReturn(OffsetDateTime.parse(candlesItem.date));
    given(apiResponse.getShabbatEnd()).willReturn(OffsetDateTime.parse(havdalahItem.date));
    // stub shabbat api with a completed future completed with mock response
    var future = new CompletableFuture<APIResponse>();
    future.complete(apiResponse);
    given(sapi.sendAsync(argThat(verifyRequest(cityGeoid, fridayTimestamp)))).willReturn(future);
    // stub the builder with the steps expected to be performed by the sut
    given(
            builder.withSpeech(
                String.format(
                    getText(SHABBAT_INFORMATION_FMT),
                    cityName,
                    getText(SHABBAT_START_TODAY),
                    "2019-10-04",
                    "18:04",
                    getText(SHABBAT_END_TOMORROW),
                    "2019-10-05",
                    "19:11")))
        .willReturn(builder);
    given(builder.withReprompt(getText(ASK_FOR_ANOTHER_REPROMPT))).willReturn(builder);
    given(builder.withShouldEndSession(Boolean.FALSE)).willReturn(builder);
    given(
            builder.withSimpleCard(
                String.format(getText(SIMPLE_CARD_TITLE_FMT), cityGeoName),
                String.format(
                    getText(SIMPLE_CARD_CONTENT_FMT),
                    "2019-10-04",
                    "18:04",
                    "2019-10-05",
                    "19:11")))
        .willReturn(builder);
    given(builder.build()).willReturn(Optional.of(skillResponse));
    given(input.getResponseBuilder()).willReturn(builder);
    // when invoking the handler
    var handlerResponse = sut.handle(input, request);
    // verify the mocked response return
    then(handlerResponse).isNotEmpty().hasValue(skillResponse);
    // verify city information saved as session attributes
    verify(attribMngr)
        .setSessionAttributes(
            argThat(
                m ->
                    m.get(COUNTRY.toString()).equals(countryAbbreviation)
                        && m.get(CITY.toString()).equals(cityName)));
    then(sessionAttribs).hasEntrySatisfying(COUNTRY.toString(), o -> o.equals(countryAbbreviation));
    then(sessionAttribs).hasEntrySatisfying(CITY.toString(), o -> o.equals(cityName));
  }

  private ArgumentMatcher<APIRequest> verifyRequest(
      final int geoId, final OffsetDateTime timestamp) {
    var exp =
        Map.of(
            APIRequest.ParamKey.OUTPUT_FORMAT, APIRequest.OutputType.JSON,
            APIRequest.ParamKey.GEO_TYPE, APIRequest.GeoType.GEO_NAME,
            APIRequest.ParamKey.GEO_ID, APIRequest.IntWrapper.of(geoId),
            APIRequest.ParamKey.ASHKENAZIS_TRANSLITERATIONS, APIRequest.FlagState.OFF,
            APIRequest.ParamKey.CANDLE_LIGHTING, APIRequest.IntWrapper.of(18),
            APIRequest.ParamKey.HAVDALAH, APIRequest.IntWrapper.of(50),
            APIRequest.ParamKey.INCLUDE_TURAH_HAFTARAH, APIRequest.FlagState.OFF,
            APIRequest.ParamKey.GREGORIAN_YEAR, APIRequest.IntWrapper.of(timestamp.getYear()),
            APIRequest.ParamKey.GREGORIAN_MONTH,
                APIRequest.PaddedIntWrapper.of(timestamp.getMonthValue()),
            APIRequest.ParamKey.GREGORIAN_DAY,
                APIRequest.PaddedIntWrapper.of(timestamp.getDayOfMonth()));
    return r -> r.queryParams().equals(exp);
  }

  private static void setFinalField(final Object instance, final String name, final Object value)
      throws NoSuchFieldException, SecurityException, IllegalArgumentException,
          IllegalAccessException {
    var field = instance.getClass().getDeclaredField(name);
    field.setAccessible(true);
    field.set(instance, value);
  }
}
