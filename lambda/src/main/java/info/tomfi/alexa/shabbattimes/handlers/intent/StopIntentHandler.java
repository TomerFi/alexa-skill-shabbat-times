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

import static info.tomfi.alexa.shabbattimes.AttributeKey.LAST_INTENT;
import static info.tomfi.alexa.shabbattimes.BundleKey.DEFAULT_ASK_FOR_CITY;
import static info.tomfi.alexa.shabbattimes.BundleKey.DEFAULT_OK;
import static info.tomfi.alexa.shabbattimes.BundleKey.DEFAULT_PLEASE_CLARIFY;
import static info.tomfi.alexa.shabbattimes.BundleKey.DEFAULT_REPROMPT;
import static info.tomfi.alexa.shabbattimes.IntentType.HELP;
import static info.tomfi.alexa.shabbattimes.IntentType.STOP;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.impl.IntentRequestHandler;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Response;
import info.tomfi.alexa.shabbattimes.TextService;
import java.util.Optional;
import org.springframework.stereotype.Component;

/** Intent request handler for handling intent requests with the name {@value STOP}. */
@Component
public final class StopIntentHandler implements IntentRequestHandler {
  private final TextService textor;

  public StopIntentHandler(final TextService setTextor) {
    textor = setTextor;
  }

  @Override
  public boolean canHandle(final HandlerInput input, final IntentRequest request) {
    return request.getIntent().getName().equals(STOP.toString());
  }

  @Override
  public Optional<Response> handle(final HandlerInput input, final IntentRequest request) {
    // get attributes
    var reqAttribs = input.getAttributesManager().getRequestAttributes();
    var sessAttribs = input.getAttributesManager().getSessionAttributes();

    // start builder
    var respBuilder = input.getResponseBuilder();

    if (!sessAttribs.containsKey(LAST_INTENT.toString())) {
      // if the user said stop after a launch request
      respBuilder
        .withSpeech(textor.getText(reqAttribs, DEFAULT_OK))
        .withShouldEndSession(true)
        .build();
    } else if (sessAttribs.get(LAST_INTENT.toString()).equals(HELP)) {
      // if the user said stop while listenting to the help informative message
      respBuilder
          .withSpeech(textor.getText(reqAttribs, DEFAULT_ASK_FOR_CITY))
          .withReprompt(textor.getText(reqAttribs, DEFAULT_REPROMPT))
          .withShouldEndSession(false);
    } else {
      respBuilder
          .withSpeech(textor.getText(reqAttribs, DEFAULT_PLEASE_CLARIFY))
          .withReprompt(textor.getText(reqAttribs, DEFAULT_REPROMPT))
          .withShouldEndSession(false);
    }
    return respBuilder.build();
  }
}
