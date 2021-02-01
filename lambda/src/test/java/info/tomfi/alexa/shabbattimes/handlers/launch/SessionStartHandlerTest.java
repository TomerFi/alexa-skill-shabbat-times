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
package info.tomfi.alexa.shabbattimes.handlers.launch;

import static info.tomfi.alexa.shabbattimes.BundleKey.DEFAULT_REPROMPT;
import static info.tomfi.alexa.shabbattimes.BundleKey.WELCOME_SPEECH;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.verifyNoMoreInteractions;

import com.amazon.ask.attributes.AttributesManager;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.LaunchRequest;
import com.amazon.ask.model.Response;
import com.amazon.ask.response.ResponseBuilder;
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
final class SessionStartHandlerTest extends HandlerFixtures {
  @Mock private HandlerInput input;
  @Mock private LaunchRequest request;
  @InjectMocks private SessionStartHandler sut;

  @Test
  void can_handle_should_always_return_true_for_typed_request_handlers() {
    assertThat(sut.canHandle(input, request)).isTrue();
  }

  @Test
  void invoking_the_handler_should_return_a_welcome_follow_up(
      @Mock final AttributesManager attribMngr,
      @Mock final ResponseBuilder builder,
      @Mock final LaunchRequest request,
      @Mock final Response response) {
    // get request attributes fixture, stub the attributes manager, and the input with it
    var requestAttribs = getRequestAttribs();
    given(attribMngr.getRequestAttributes()).willReturn(requestAttribs);
    given(input.getAttributesManager()).willReturn(attribMngr);
    // stub the builder with the steps expected to be performed by the sut
    given(builder.withSpeech(getText(WELCOME_SPEECH))).willReturn(builder);
    given(builder.withReprompt(getText(DEFAULT_REPROMPT))).willReturn(builder);
    given(builder.withShouldEndSession(Boolean.FALSE)).willReturn(builder);
    given(builder.build()).willReturn(Optional.of(response));
    given(input.getResponseBuilder()).willReturn(builder);
    // when invoking the handler
    var resp = sut.handle(input, request);
    // verify the mocked response return and no more builder steps
    then(resp).isNotEmpty().hasValue(response);
    verifyNoMoreInteractions(builder);
  }
}
