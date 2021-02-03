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

import static info.tomfi.alexa.shabbattimes.BundleKey.HELP_REPROMPT;
import static info.tomfi.alexa.shabbattimes.BundleKey.HELP_SPEECH;
import static info.tomfi.alexa.shabbattimes.IntentType.HELP;
import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.params.provider.EnumSource.Mode.EXCLUDE;
import static org.mockito.BDDMockito.given;

import com.amazon.ask.model.Intent;
import com.amazon.ask.model.Response;
import com.amazon.ask.response.ResponseBuilder;
import info.tomfi.alexa.shabbattimes.IntentType;
import info.tomfi.alexa.shabbattimes.handlers.IntentHandlerFixtures;
import java.util.Optional;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;

/** Test cases for the HelpIntentHandler intent request handler. */
@Tag("unit-tests")
final class HelpIntentHandlerTest extends IntentHandlerFixtures {
  @InjectMocks private HelpIntentHandler sut;

  @Test
  void can_handle_should_return_true_for_help_intent_type(@Mock final Intent intent) {
    // stub intent with HELP as name and stub request with intnet
    given(intent.getName()).willReturn(HELP.toString());
    given(request.getIntent()).willReturn(intent);
    // verify handler can handle
    then(sut.canHandle(input, request)).isTrue();
  }

  @ParameterizedTest
  @EnumSource(mode = EXCLUDE, names = "HELP")
  void can_handle_should_return_false_for_non_help_intent_type(
      final IntentType intentType, @Mock final Intent intent) {
    // stub intent with intent type as name and stub request with intnet
    given(intent.getName()).willReturn(intentType.toString());
    given(request.getIntent()).willReturn(intent);
    // verify handler cannot handle
    then(sut.canHandle(input, request)).isFalse();
  }

  @Test
  void invoking_the_handler_should_return_a_follow_up(
      @Mock final ResponseBuilder builder, @Mock final Response response) {
    // stub the builder with the steps expected to be performed by the sut
    given(builder.withSpeech(getText(HELP_SPEECH))).willReturn(builder);
    given(builder.withReprompt(getText(HELP_REPROMPT))).willReturn(builder);
    given(builder.withShouldEndSession(Boolean.FALSE)).willReturn(builder);
    given(builder.build()).willReturn(Optional.of(response));
    given(input.getResponseBuilder()).willReturn(builder);
    // when invoking the handler
    var resp = sut.handle(input, request);
    // verify the mocked response return
    then(resp).isNotEmpty().hasValue(response);
  }
}
