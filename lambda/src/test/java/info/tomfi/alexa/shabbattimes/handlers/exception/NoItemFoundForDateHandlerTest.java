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

import static info.tomfi.alexa.shabbattimes.BundleKey.EXC_UNRECOVERABLE_ERROR;
import static org.assertj.core.api.BDDAssertions.assertThat;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.verifyNoMoreInteractions;

import com.amazon.ask.attributes.AttributesManager;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.Response;
import com.amazon.ask.response.ResponseBuilder;
import info.tomfi.alexa.shabbattimes.exceptions.NoItemFoundForDateException;
import info.tomfi.alexa.shabbattimes.handlers.HandlerFixtures;
import java.util.Optional;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@Tag("unit-tests")
final class NoItemFoundForDateHandlerTest extends HandlerFixtures {
  @Mock HandlerInput input;
  @InjectMocks NoItemFoundForDateHandler sut;

  @Test
  void can_handle_should_return_true_only_for_instances_of_no_item_found_for_date_exception() {
    assertThat(sut.canHandle(input, new NoItemFoundForDateException())).isTrue();
  }

  @Test
  void
      can_handle_should_return_false_for_exception_other_then_of_no_item_found_for_date_exception() {
    assertThat(sut.canHandle(input, new Throwable())).isFalse();
  }

  @Test
  void invoking_the_handler_should_play_a_prompt_and_end_the_session(
      @Mock final AttributesManager attribMngr,
      @Mock final ResponseBuilder builder,
      @Mock Response response,
      @Mock final Throwable throwable) {
    // get request attributes fixture, stub the attributes manager, and the input with it
    var requestAttribs = getRequestAttribs();
    given(attribMngr.getRequestAttributes()).willReturn(requestAttribs);
    given(input.getAttributesManager()).willReturn(attribMngr);
    // stub the builder with the steps expected to be performed by the sut
    given(builder.withSpeech(getText(EXC_UNRECOVERABLE_ERROR))).willReturn(builder);
    given(builder.withShouldEndSession(Boolean.TRUE)).willReturn(builder);
    given(builder.build()).willReturn(Optional.of(response));
    given(input.getResponseBuilder()).willReturn(builder);
    // when invoking the handler
    var resp = sut.handle(input, throwable);
    // verify the mocked response return and no more builder steps
    then(resp).isNotEmpty().hasValue(response);
    verifyNoMoreInteractions(builder);
  }
}
