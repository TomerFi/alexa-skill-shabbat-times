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

import static info.tomfi.alexa.shabbattimes.AttributeKey.COUNTRY;
import static info.tomfi.alexa.shabbattimes.BundleKey.CITIES_IN_COUNTRY_FMT;
import static info.tomfi.alexa.shabbattimes.BundleKey.DEFAULT_ASK_FOR_CITY;
import static info.tomfi.alexa.shabbattimes.IntentType.COUNTRY_SELECTED;
import static info.tomfi.alexa.shabbattimes.SlotName.COUNTRY_SLOT;
import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenExceptionOfType;
import static org.junit.jupiter.params.provider.EnumSource.Mode.EXCLUDE;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.when;

import com.amazon.ask.model.Intent;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.Slot;
import com.amazon.ask.response.ResponseBuilder;
import com.github.javafaker.Faker;
import info.tomfi.alexa.shabbattimes.Country;
import info.tomfi.alexa.shabbattimes.IntentType;
import info.tomfi.alexa.shabbattimes.exceptions.NoCountryFoundException;
import info.tomfi.alexa.shabbattimes.exceptions.NoCountrySlotException;
import info.tomfi.alexa.shabbattimes.handlers.IntentHandlerFixtures;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.Mock;

/** Test cases for the CountrySelectedIntentHandler intent request handler. */
@Tag("unit-tests")
final class CountrySelectedIntentHandlerTest extends IntentHandlerFixtures {
  @Mock private Intent intent;
  @Mock private Country country1;
  private String country1Utterance;
  private Faker faker;
  private CountrySelectedIntentHandler sut;

  @BeforeEach
  void initialize(@Mock Country country2) {
    faker = new Faker();
    country1Utterance = faker.lorem().word();
    var country2Utterance = faker.lorem().word();
    when(country1.hasUtterance(country1Utterance)).thenReturn(Boolean.TRUE);
    when(country2.hasUtterance(country2Utterance)).thenReturn(Boolean.TRUE);
    sut = new CountrySelectedIntentHandler(List.of(country1, country2), textor);
  }

  @Test
  void can_handle_should_return_true_for_country_selected_intent_type() {
    // stub intent with COUNTRY_SELECTED as name and stub request with intnet
    given(intent.getName()).willReturn(COUNTRY_SELECTED.toString());
    given(request.getIntent()).willReturn(intent);
    // verify handler can handle
    then(sut.canHandle(input, request)).isTrue();
  }

  @ParameterizedTest
  @EnumSource(mode = EXCLUDE, names = "COUNTRY_SELECTED")
  void can_handle_should_return_false_for_non_country_selected_intent_type(
      final IntentType intentType) {
    // stub intent with intent type as name and stub request with intnet
    given(intent.getName()).willReturn(intentType.toString());
    given(request.getIntent()).willReturn(intent);
    // verify handler cannot handle
    then(sut.canHandle(input, request)).isFalse();
  }

  @Test
  void invoking_handler_with_no_country_slot_throws_no_country_exception() {
    // stub intnet with empty slot map and stub request with intent
    var slots = new HashMap<String, Slot>();
    given(intent.getSlots()).willReturn(slots);
    given(request.getIntent()).willReturn(intent);
    // verify exception throws
    thenExceptionOfType(NoCountrySlotException.class).isThrownBy(() -> sut.handle(input, request));
  }

  @Test
  void invoking_handler_with_null_country_value_throws_no_country_slot_exception(
      @Mock final Slot countrySlot) {
    // stub intnet with null value country slot and stub request with intent
    given(countrySlot.getValue()).willReturn(null);
    var slots = Map.of(COUNTRY_SLOT.toString(), countrySlot);
    given(intent.getSlots()).willReturn(slots);
    given(request.getIntent()).willReturn(intent);
    // verify exception throws
    thenExceptionOfType(NoCountrySlotException.class).isThrownBy(() -> sut.handle(input, request));
  }

  @Test
  void invoking_handler_unknown_country_value_throws_no_country_found_exception(
      @Mock final Slot countrySlot) {
    // stub intnet with unknown country slot value and stub request with intent
    given(countrySlot.getValue()).willReturn(faker.lorem().word());
    var slots = Map.of(COUNTRY_SLOT.toString(), countrySlot);
    given(intent.getSlots()).willReturn(slots);
    given(request.getIntent()).willReturn(intent);
    // verify exception throws
    thenExceptionOfType(NoCountryFoundException.class).isThrownBy(() -> sut.handle(input, request));
  }

  @Test
  void invoking_handler_known_country_value_saves_session_info_and_return_follow_up(
      @Mock final Slot countrySlot,
      @Mock final ResponseBuilder builder,
      @Mock final Response response) {
    // stub intnet with known country slot value and stub request with intent
    given(countrySlot.getValue()).willReturn(country1Utterance);
    var slots = Map.of(COUNTRY_SLOT.toString(), countrySlot);
    given(intent.getSlots()).willReturn(slots);
    given(request.getIntent()).willReturn(intent);
    // stub attributes manager with empty session attributes map
    var sessionAttribs = new HashMap<String, Object>();
    given(attribMngr.getSessionAttributes()).willReturn(sessionAttribs);
    // stub selected country
    var abbreviation = faker.lorem().characters(2);
    var countryName = faker.country().name();
    var cities = faker.lorem().sentence();
    given(country1.abbreviation()).willReturn(abbreviation);
    given(country1.name()).willReturn(countryName);
    given(country1.stringCities()).willReturn(cities);
    // stub the builder with the steps expected to be performed by the sut
    given(builder.withSpeech(String.format(getText(CITIES_IN_COUNTRY_FMT), countryName, cities)))
        .willReturn(builder);
    given(builder.withReprompt(getText(DEFAULT_ASK_FOR_CITY))).willReturn(builder);
    given(builder.withShouldEndSession(Boolean.FALSE)).willReturn(builder);
    given(builder.build()).willReturn(Optional.of(response));
    given(input.getResponseBuilder()).willReturn(builder);
    // when invoking the handler
    var resp = sut.handle(input, request);
    // verify the mocked response return
    then(resp).isNotEmpty().hasValue(response);
    // verify the selected country abbrivation is saved as a session attribute
    then(sessionAttribs).hasSize(1).containsKey(COUNTRY.toString()).containsValue(abbreviation);
  }
}
