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

import static info.tomfi.alexa.shabbattimes.BundleKey.DEFAULT_ASK_FOR_CITY;
import static info.tomfi.alexa.shabbattimes.BundleKey.DEFAULT_REPROMPT;
import static info.tomfi.alexa.shabbattimes.IntentType.YES;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.impl.IntentRequestHandler;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Response;
import info.tomfi.alexa.shabbattimes.TextService;
import java.util.Optional;
import org.springframework.stereotype.Component;

/**
 * Implementation of com.amazon.ask.dispatcher.request.handler.impl.IntentRequestHandler, handles
 * {#value info.tomfi.shabbattimes.enums.Intents.YES} intent requests.
 */
@Component
public final class YesIntentHandler implements IntentRequestHandler {
  private final TextService textor;

  public YesIntentHandler(final TextService setTextor) {
    textor = setTextor;
  }

  @Override
  public boolean canHandle(final HandlerInput input, final IntentRequest request) {
    return request.getIntent().getName().equals(YES.toString());
  }

  @Override
  public Optional<Response> handle(final HandlerInput input, final IntentRequest request) {
    // get request attributes
    var attributes = input.getAttributesManager().getRequestAttributes();
    // the user reply yes to the question `was you city on the list?`
    // return follow up for city name and don't end the interaction
    return input
        .getResponseBuilder()
        .withSpeech(textor.getText(attributes, DEFAULT_ASK_FOR_CITY))
        .withReprompt(textor.getText(attributes, DEFAULT_REPROMPT))
        .withShouldEndSession(false)
        .build();
  }
}
