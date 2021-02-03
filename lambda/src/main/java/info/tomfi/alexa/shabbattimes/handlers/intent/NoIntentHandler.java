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

import static info.tomfi.alexa.shabbattimes.AttributeKey.COUNTRY;
import static info.tomfi.alexa.shabbattimes.AttributeKey.LAST_INTENT;
import static info.tomfi.alexa.shabbattimes.BundleKey.DEFAULT_OK;
import static info.tomfi.alexa.shabbattimes.BundleKey.NOT_FOUND_FMT;
import static info.tomfi.alexa.shabbattimes.IntentType.COUNTRY_SELECTED;
import static info.tomfi.alexa.shabbattimes.IntentType.NO;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.impl.IntentRequestHandler;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Response;
import info.tomfi.alexa.shabbattimes.Country;
import info.tomfi.alexa.shabbattimes.TextService;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.stereotype.Component;

/**
 * Implementation of com.amazon.ask.dispatcher.request.handler.impl.IntentRequestHandler, handles
 * {#value info.tomfi.shabbattimes.enums.Intents.NO} intent requests.
 */
@Component
public final class NoIntentHandler implements IntentRequestHandler {
  private final List<Country> countries;
  private final TextService textor;

  public NoIntentHandler(final List<Country> setCountries, final TextService setTextor) {
    countries = setCountries;
    textor = setTextor;
  }

  @Override
  public boolean canHandle(final HandlerInput input, final IntentRequest request) {
    return request.getIntent().getName().equals(NO.toString());
  }

  @Override
  public Optional<Response> handle(final HandlerInput input, final IntentRequest request) {
    // get session attributes
    var sessionAttribs = input.getAttributesManager().getSessionAttributes();
    if (sessionAttribs.containsKey(LAST_INTENT.toString())) {
      // if last intent was country selected intent, the user is replying to:
      // 'was your city on the list?' after selecting a country
      if (sessionAttribs.get(LAST_INTENT.toString()).equals(COUNTRY_SELECTED.toString())
          && sessionAttribs.containsKey(COUNTRY.toString())) {
        return countrySelectedFollowUp(input, sessionAttribs);
      }
    }
    return Optional.empty();
  }

  private Optional<Response> countrySelectedFollowUp(
      final HandlerInput input, final Map<String, Object> sessionAttribs) {
    // get request attributes
    var requestAttribs = input.getAttributesManager().getRequestAttributes();
    // find the requested country
    var selectedAbbr = (String) sessionAttribs.get(COUNTRY.toString());
    var optCountry =
        countries.stream().filter(c -> c.abbreviation().equals(selectedAbbr)).findFirst();
    // construct the speech output
    var speechOutput =
        optCountry.isEmpty()
            ? textor.getText(requestAttribs, DEFAULT_OK)
            : // eg ok
            String.format(
                textor.getText(requestAttribs, NOT_FOUND_FMT), // eg .. 'city names in %s'
                textor.getText(requestAttribs, optCountry.get().bundleKey())); // eg 'in israel'
    return input.getResponseBuilder().withSpeech(speechOutput).withShouldEndSession(true).build();
  }
}
