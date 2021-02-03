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
import static info.tomfi.alexa.shabbattimes.AttributeKey.LAST_INTENT;
import static info.tomfi.alexa.shabbattimes.BundleKey.DEFAULT_OK;
import static info.tomfi.alexa.shabbattimes.BundleKey.NOT_FOUND_FMT;
import static info.tomfi.alexa.shabbattimes.BundleKey.NOT_FOUND_IN_ISRAEL;
import static info.tomfi.alexa.shabbattimes.IntentType.COUNTRY_SELECTED;
import static info.tomfi.alexa.shabbattimes.IntentType.NO;
import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.params.provider.EnumSource.Mode.EXCLUDE;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

import com.amazon.ask.model.Intent;
import com.amazon.ask.model.Response;
import com.amazon.ask.response.ResponseBuilder;
import info.tomfi.alexa.shabbattimes.Country;
import info.tomfi.alexa.shabbattimes.IntentType;
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

/** Test cases for the NoIntentHandler intent request handler. */
@Tag("unit-tests")
final class NoIntentHandlerTest extends IntentHandlerFixtures {
  private static final String COUNTRY1_TST_ABBR = "IL";
  private static final String COUNTRY2_TST_ABBR = "US";
  private static final String UNKNOWN_COUNTRY_TST_ABBR = "GB";

  private NoIntentHandler sut;

  @BeforeEach
  void initialize(@Mock final Country country1, @Mock final Country country2) {
    when(country1.abbreviation()).thenReturn(COUNTRY1_TST_ABBR);
    when(country1.bundleKey()).thenReturn(NOT_FOUND_IN_ISRAEL);
    when(country2.abbreviation()).thenReturn(COUNTRY2_TST_ABBR);
    sut = new NoIntentHandler(List.of(country1, country2), textor);
  }

  @Test
  void can_handle_should_return_true_for_no_intent_type(@Mock final Intent intent) {
    // stub intent with NO as name and stub request with intnet
    given(intent.getName()).willReturn(NO.toString());
    given(request.getIntent()).willReturn(intent);
    // verify handler can handle
    then(sut.canHandle(input, request)).isTrue();
  }

  @ParameterizedTest
  @EnumSource(mode = EXCLUDE, names = "NO")
  void can_handle_should_return_false_for_non_no_intent_type(
      final IntentType intentType, @Mock final Intent intent) {
    // stub intent with intent type as name and stub request with intnet
    given(intent.getName()).willReturn(intentType.toString());
    given(request.getIntent()).willReturn(intent);
    // verify handler cannot handle
    then(sut.canHandle(input, request)).isFalse();
  }

  @ParameterizedTest
  @EnumSource(mode = EXCLUDE, names = "COUNTRY_SELECTED")
  void invoking_handler_when_previous_intent_is_not_country_selected_should_end_interaction(
      final IntentType intentType) {
    // stub attributes manager with session attributes with any last intent besides country selected
    var sessionAttribs = Map.of(LAST_INTENT.toString(), (Object) intentType.toString());
    given(attribMngr.getSessionAttributes()).willReturn(sessionAttribs);
    // when invoking the handler
    var resp = sut.handle(input, request);
    // verify empty response
    then(resp).isEmpty();
  }

  @Test
  void invoking_handler_with_no_last_intent_should_end_interaction() {
    // stub attributes manager with empty session attributes not containing the last intent key
    var sessionAttribs = new HashMap<String, Object>();
    given(attribMngr.getSessionAttributes()).willReturn(sessionAttribs);
    // when invoking the handler
    var resp = sut.handle(input, request);
    // verify empty response
    then(resp).isEmpty();
  }

  @Test
  void invoking_handler_after_country_selected_with_no_country_value_should_end_interaction() {
    // stub attributes manager with session attributes for follow with no country
    var sessionAttribs = Map.of(LAST_INTENT.toString(), (Object) COUNTRY_SELECTED.toString());
    given(attribMngr.getSessionAttributes()).willReturn(sessionAttribs);
    // when invoking the handler
    var resp = sut.handle(input, request);
    // verify empty response
    then(resp).isEmpty();
  }

  @Test
  void invoking_handler_after_country_selected_with_unknown_country_should_say_ok_and_end(
      @Mock final ResponseBuilder builder, @Mock final Response response) {
    // stub attributes manager with session attributes for follow with unknown country
    var sessionAttribs =
        Map.of(
            LAST_INTENT.toString(), (Object) COUNTRY_SELECTED.toString(),
            COUNTRY.toString(), (Object) UNKNOWN_COUNTRY_TST_ABBR);
    given(attribMngr.getSessionAttributes()).willReturn(sessionAttribs);
    // stub the builder with the steps expected to be performed by the sut
    given(builder.withSpeech(getText(DEFAULT_OK))).willReturn(builder);
    given(builder.withShouldEndSession(Boolean.TRUE)).willReturn(builder);
    given(builder.build()).willReturn(Optional.of(response));
    given(input.getResponseBuilder()).willReturn(builder);
    // when invoking the handler
    var resp = sut.handle(input, request);
    // verify the mocked response return
    then(resp).isNotEmpty().hasValue(response);
  }

  @Test
  void invoking_handler_after_country_selected_with_known_country_should_explain_and_end(
      @Mock final ResponseBuilder builder, @Mock final Response response) {
    // stub attributes manager with session attributes for follow with unknown country
    var sessionAttribs =
        Map.of(
            LAST_INTENT.toString(), (Object) COUNTRY_SELECTED.toString(),
            COUNTRY.toString(), (Object) COUNTRY1_TST_ABBR);
    given(attribMngr.getSessionAttributes()).willReturn(sessionAttribs);
    // stub the builder with the steps expected to be performed by the sut
    given(builder.withSpeech(String.format(getText(NOT_FOUND_FMT), getText(NOT_FOUND_IN_ISRAEL))))
        .willReturn(builder);
    given(builder.withShouldEndSession(Boolean.TRUE)).willReturn(builder);
    given(builder.build()).willReturn(Optional.of(response));
    given(input.getResponseBuilder()).willReturn(builder);
    // when invoking the handler
    var resp = sut.handle(input, request);
    // verify the mocked response return
    then(resp).isNotEmpty().hasValue(response);
  }
}
