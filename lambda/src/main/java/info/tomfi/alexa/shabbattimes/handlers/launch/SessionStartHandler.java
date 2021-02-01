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
import static info.tomfi.alexa.shabbattimes.internal.LocalizationUtils.getFromBundle;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.impl.LaunchRequestHandler;
import com.amazon.ask.model.LaunchRequest;
import com.amazon.ask.model.Response;
import java.util.Optional;
import org.springframework.stereotype.Component;

/**
 * Implementation of com.amazon.ask.dispatcher.request.handler.impl.LaunchRequestHandler, handles
 * requests of type com.amazon.ask.model.LaunchRequest.
 */
@Component
public final class SessionStartHandler implements LaunchRequestHandler {
  public SessionStartHandler() {
    //
  }

  @Override
  public boolean canHandle(final HandlerInput input, final LaunchRequest request) {
    return Boolean.TRUE;
  }

  @Override
  public Optional<Response> handle(final HandlerInput input, final LaunchRequest request) {
    var attributes = input.getAttributesManager().getRequestAttributes();
    return input
        .getResponseBuilder()
        .withSpeech(getFromBundle(attributes, WELCOME_SPEECH))
        .withReprompt(getFromBundle(attributes, DEFAULT_REPROMPT))
        .withShouldEndSession(false)
        .build();
  }
}
