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
import static info.tomfi.alexa.shabbattimes.BundleKey.CITIES_IN_COUNTRY_FMT;
import static info.tomfi.alexa.shabbattimes.BundleKey.DEFAULT_ASK_FOR_CITY;
import static info.tomfi.alexa.shabbattimes.IntentType.COUNTRY_SELECTED;
import static java.util.Objects.isNull;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.impl.IntentRequestHandler;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Response;
import info.tomfi.alexa.shabbattimes.Country;
import info.tomfi.alexa.shabbattimes.SlotName;
import info.tomfi.alexa.shabbattimes.TextService;
import info.tomfi.alexa.shabbattimes.exceptions.NoCountryFoundException;
import info.tomfi.alexa.shabbattimes.exceptions.NoCountrySlotException;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;

/**
 * Implementation of com.amazon.ask.dispatcher.request.handler.impl.IntentRequestHandler, handles
 * {#value info.tomfi.shabbattimes.enums.Intents.COUNTRY_SELECTED} intent requests.
 */
@Component
public final class CountrySelectedIntentHandler implements IntentRequestHandler {
  private final TextService textor;
  private final List<Country> countries;

  public CountrySelectedIntentHandler(
      final List<Country> setCountries, final TextService setTextor) {
    countries = setCountries;
    textor = setTextor;
  }

  @Override
  public boolean canHandle(final HandlerInput input, final IntentRequest intent) {
    return intent.getIntent().getName().equals(COUNTRY_SELECTED.toString());
  }

  @Override
  public Optional<Response> handle(final HandlerInput input, final IntentRequest intent) {
    // get the requested country slot
    var countrySlot = intent.getIntent().getSlots().get(SlotName.COUNTRY_SLOT);
    if (isNull(countrySlot.getValue())) {
      throw new NoCountrySlotException("No country slot found.");
    }
    // get the country object
    var country =
        countries.stream()
            .filter(c -> c.hasUtterance(countrySlot.getValue()))
            .findFirst()
            .orElseThrow(NoCountryFoundException::new);
    // save country abbreviation to session attributes
    var sessionAttributes = input.getAttributesManager().getSessionAttributes();
    sessionAttributes.put(COUNTRY.toString(), country.abbreviation());
    input.getAttributesManager().setSessionAttributes(sessionAttributes);
    // get request attributes
    var requestAttributes = input.getAttributesManager().getRequestAttributes();
    // return city list in selected country as response and don't end the interaction
    return input
        .getResponseBuilder()
        .withSpeech(
            String.format(
                textor.getText(requestAttributes, CITIES_IN_COUNTRY_FMT),
                country.name(),
                country.stringCities()))
        .withReprompt(textor.getText(requestAttributes, DEFAULT_ASK_FOR_CITY))
        .withShouldEndSession(false)
        .build();
  }
}
