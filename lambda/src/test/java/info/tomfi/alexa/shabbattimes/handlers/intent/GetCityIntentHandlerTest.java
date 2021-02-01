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

import static info.tomfi.alexa.shabbattimes.IntentType.GET_CITY;
import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.params.provider.EnumSource.Mode.EXCLUDE;
import static org.mockito.BDDMockito.given;

import com.amazon.ask.model.Intent;
import info.tomfi.alexa.shabbattimes.IntentType;
import info.tomfi.alexa.shabbattimes.LocatorService;
import info.tomfi.alexa.shabbattimes.handlers.IntentHandlerFixtures;
import info.tomfi.hebcal.shabbat.ShabbatAPI;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;

@Tag("unit-tests")
final class GetCityIntentHandlerTest extends IntentHandlerFixtures {
  @Mock private ShabbatAPI sapi;
  @Mock private LocatorService locator;
  @InjectMocks private GetCityIntentHandler sut;

  @Test
  void can_handle_should_return_true_for_get_city_intent_type(@Mock final Intent intent) {
    // stub intent with GET_CITY as name and stub request with intnet
    given(intent.getName()).willReturn(GET_CITY.toString());
    given(request.getIntent()).willReturn(intent);
    // verify handler can handle
    then(sut.canHandle(input, request)).isTrue();
  }

  @ParameterizedTest
  @EnumSource(mode = EXCLUDE, names = "GET_CITY")
  void can_handle_should_return_false_for_non_get_city_intent_type(
      final IntentType intentType, @Mock final Intent intent) {
    // stub intent with intent type as name and stub request with intnet
    given(intent.getName()).willReturn(intentType.toString());
    given(request.getIntent()).willReturn(intent);
    // verify handler cannot handle
    then(sut.canHandle(input, request)).isFalse();
  }
}
