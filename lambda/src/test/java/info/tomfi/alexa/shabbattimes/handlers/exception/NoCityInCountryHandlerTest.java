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
package info.tomfi.alexa.shabbattimes.handlers.exception;

import static info.tomfi.alexa.shabbattimes.BundleKey.DEFAULT_REPROMPT;
import static info.tomfi.alexa.shabbattimes.BundleKey.EXC_NO_CITY_FOUND;
import static org.assertj.core.api.BDDAssertions.assertThat;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.BDDMockito.given;

import com.amazon.ask.model.Response;
import com.amazon.ask.response.ResponseBuilder;
import info.tomfi.alexa.shabbattimes.exceptions.NoCityInCountryException;
import info.tomfi.alexa.shabbattimes.handlers.HandlerFixtures;
import java.util.Optional;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

/** Test cases for the NoCityInCountryHandler exception handler. */
@Tag("unit-tests")
final class NoCityInCountryHandlerTest extends HandlerFixtures {
  @InjectMocks private NoCityInCountryHandler sut;

  @Test
  void can_handle_should_return_true_only_for_instances_of_no_city_in_country_exception() {
    assertThat(sut.canHandle(input, new NoCityInCountryException())).isTrue();
  }

  @Test
  void can_handle_should_return_false_for_exception_other_then_of_no_city_in_country_exception() {
    assertThat(sut.canHandle(input, new Throwable())).isFalse();
  }

  @Test
  void invoking_the_handler_should_return_a_follow_up(
      @Mock final ResponseBuilder builder,
      @Mock final Response response,
      @Mock final Throwable throwable) {
    // stub the builder with the steps expected to be performed by the sut
    given(builder.withSpeech(getText(EXC_NO_CITY_FOUND))).willReturn(builder);
    given(builder.withReprompt(getText(DEFAULT_REPROMPT))).willReturn(builder);
    given(builder.withShouldEndSession(Boolean.FALSE)).willReturn(builder);
    given(builder.build()).willReturn(Optional.of(response));
    given(input.getResponseBuilder()).willReturn(builder);
    // when invoking the handler
    var resp = sut.handle(input, throwable);
    // verify the mocked response return
    then(resp).isNotEmpty().hasValue(response);
  }
}
