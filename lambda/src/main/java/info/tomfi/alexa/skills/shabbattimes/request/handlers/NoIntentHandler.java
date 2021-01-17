/**
 * Copyright Tomer Figenblat
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
package info.tomfi.alexa.skills.shabbattimes.request.handlers;

import static info.tomfi.alexa.skills.shabbattimes.tools.LocalizationUtils.getFromBundle;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.impl.IntentRequestHandler;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Response;
import info.tomfi.alexa.skills.shabbattimes.enums.Attributes;
import info.tomfi.alexa.skills.shabbattimes.enums.BundleKeys;
import info.tomfi.alexa.skills.shabbattimes.enums.CountryInfo;
import info.tomfi.alexa.skills.shabbattimes.enums.Intents;
import java.util.Optional;
import lombok.NoArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Component;

/**
 * Implementation of com.amazon.ask.dispatcher.request.handler.impl.IntentRequestHandler, handles
 * {#value info.tomfi.alexa.skills.shabbattimes.enums.Intents.NO} intent requests.
 *
 * @author Tomer Figenblat {@literal <tomer.figenblat@gmail.com>}
 */
@Component
@NoArgsConstructor
public final class NoIntentHandler implements IntentRequestHandler {
  @Override
  public boolean canHandle(final HandlerInput input, final IntentRequest intent) {
    return intent.getIntent().getName().equals(Intents.NO.getName());
  }

  @Override
  @SuppressWarnings({"PMD.AvoidCatchingGenericException", "PMD.AvoidCatchingNPE"})
  public Optional<Response> handle(final HandlerInput input, final IntentRequest intent) {
    val requestAttributes = input.getAttributesManager().getRequestAttributes();
    String speechOutput = "";
    try {
      val sessionAttributes = input.getAttributesManager().getSessionAttributes();
      if (sessionAttributes
          .get(Attributes.LAST_INTENT.getName())
          .equals(Intents.COUNTRY_SELECTED.getName())) {
        val attribValue = (String) sessionAttributes.get(Attributes.COUNTRY.getName());
        String speechMiddle = "";
        for (val current : CountryInfo.values()) {
          if (attribValue.equals(current.getAbbreviation())) {
            speechMiddle = getFromBundle(requestAttributes, current.getBundleKey());
            break;
          }
        }
        speechOutput =
            String.format(getFromBundle(requestAttributes, BundleKeys.NOT_FOUND_FMT), speechMiddle);
      }
    } catch (IllegalStateException | NullPointerException exc) {
      speechOutput = getFromBundle(requestAttributes, BundleKeys.DEFAULT_OK);
    }
    return input.getResponseBuilder().withSpeech(speechOutput).withShouldEndSession(true).build();
  }
}
