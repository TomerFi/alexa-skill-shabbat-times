/**
 * Copyright Tomer Figenblat Licensed under the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in
 * writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific
 * language governing permissions and limitations under the License.
 */
package info.tomfi.alexa.shabbattimes.handlers.intent;

import static info.tomfi.alexa.shabbattimes.AttributeKey.COUNTRY;
import static info.tomfi.alexa.shabbattimes.BundleKey.DEFAULT_OK;
import static info.tomfi.alexa.shabbattimes.BundleKey.NOT_FOUND_FMT;
import static info.tomfi.alexa.shabbattimes.IntentType.COUNTRY_SELECTED;
import static info.tomfi.alexa.shabbattimes.IntentType.NO;
import static info.tomfi.alexa.shabbattimes.internal.LocalizationUtils.getFromBundle;
import static info.tomfi.alexa.shabbattimes.AttributeKey.LAST_INTENT;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.impl.IntentRequestHandler;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Response;
import info.tomfi.alexa.shabbattimes.Country;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;

/**
 * Implementation of com.amazon.ask.dispatcher.request.handler.impl.IntentRequestHandler, handles
 * {#value info.tomfi.shabbattimes.enums.Intents.NO} intent requests.
 */
@Component
public final class NoIntentHandler implements IntentRequestHandler {
  private final List<Country> countries;

  public NoIntentHandler(final List<Country> setCountries) {
    countries = setCountries;
  }

  @Override
  public boolean canHandle(final HandlerInput input, final IntentRequest intent) {
    return intent.getIntent().getName().equals(NO.toString());
  }

  @Override
  public Optional<Response> handle(final HandlerInput input, final IntentRequest intent) {
    // get request attributes
    var requestAttributes = input.getAttributesManager().getRequestAttributes();
    // get session attributes
    var sessionAttributes = input.getAttributesManager().getSessionAttributes();
    // default empty speech - not needed when replying to 'no' by the user
    String speechOutput = "";
    // if last intent was country selected intent, the user is replying to:
    // 'was your city on the list?' after selecting a country
    if (sessionAttributes.get(LAST_INTENT.toString()).equals(COUNTRY_SELECTED.toString())) {
      // find the requested country
      var selectedAbbr = (String) sessionAttributes.get(COUNTRY.toString());
      var optCountry = countries.stream()
          .filter(c -> c.abbreviation().equals(selectedAbbr))
          .findFirst();
      // if country not found replay 'ok'
      if (optCountry.isEmpty()) {
        speechOutput = getFromBundle(requestAttributes, DEFAULT_OK);
      } else {
        // retrieve the correct 'in x' phrase (x being a country name)
        var speechMiddle = getFromBundle(requestAttributes, optCountry.get().bundleKey());
        // format the 'not fount in x' prompt
        speechOutput = String.format(getFromBundle(requestAttributes, NOT_FOUND_FMT), speechMiddle);
      }
    }
    // return empty or constructed speech and end the interaction
    return input.getResponseBuilder().withSpeech(speechOutput).withShouldEndSession(true).build();
  }
}
