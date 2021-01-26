/**
 * Copyright Tomer Figenblat Licensed under the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in
 * writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific
 * language governing permissions and limitations under the License.
 */
package info.tomfi.alexa.shabbattimes.handlers.intent;

import static info.tomfi.alexa.shabbattimes.BundleKey.THANKS_AND_BYE;
import static info.tomfi.alexa.shabbattimes.IntentType.THANKS;
import static info.tomfi.alexa.shabbattimes.internal.LocalizationUtils.getFromBundle;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.impl.IntentRequestHandler;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Response;
import java.util.Optional;
import org.springframework.stereotype.Component;

/**
 * Implementation of com.amazon.ask.dispatcher.request.handler.impl.IntentRequestHandler, handles
 * {#value info.tomfi.shabbattimes.enums.Intents.THANKS} intent requests.
 */
@Component
public final class ThanksIntentHandler implements IntentRequestHandler {
  public ThanksIntentHandler() {
    //
  }

  @Override
  public boolean canHandle(final HandlerInput input, final IntentRequest intent) {
    return intent.getIntent().getName().equals(THANKS.toString());
  }

  @Override
  public Optional<Response> handle(final HandlerInput input, final IntentRequest intent) {
    // get request attributes
    var attributes = input.getAttributesManager().getRequestAttributes();
    // if the user is polite and reply's `no thanks` to follow up questions
    // return polite ending message and end the interaction
    return input.getResponseBuilder()
        .withSpeech(getFromBundle(attributes, THANKS_AND_BYE))
        .withShouldEndSession(true)
        .build();
  }
}
