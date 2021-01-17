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

import static info.tomfi.alexa.skills.shabbattimes.enums.Intents.COUNTRY_SELECTED;
import static info.tomfi.alexa.skills.shabbattimes.tools.LocalizationUtils.getFromBundle;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.impl.IntentRequestHandler;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.Slot;

import info.tomfi.alexa.skills.shabbattimes.country.Country;
import info.tomfi.alexa.skills.shabbattimes.country.CountryFactory;
import info.tomfi.alexa.skills.shabbattimes.enums.Attributes;
import info.tomfi.alexa.skills.shabbattimes.enums.BundleKeys;
import info.tomfi.alexa.skills.shabbattimes.enums.Slots;
import info.tomfi.alexa.skills.shabbattimes.exception.NoCountrySlotException;
import info.tomfi.alexa.skills.shabbattimes.exception.NoJsonFileException;
import java.util.Map;
import java.util.Optional;
import org.springframework.stereotype.Component;

/**
 * Implementation of com.amazon.ask.dispatcher.request.handler.impl.IntentRequestHandler, handles
 * {#value info.tomfi.alexa.skills.shabbattimes.enums.Intents.COUNTRY_SELECTED} intent requests.
 *
 * @author Tomer Figenblat {@literal <tomer.figenblat@gmail.com>}
 */
@Component
public final class CountrySelectedIntentHandler implements IntentRequestHandler {
  public CountrySelectedIntentHandler() {
    //
  }

  @Override
  public boolean canHandle(final HandlerInput input, final IntentRequest intent) {
    return intent.getIntent().getName().equals(COUNTRY_SELECTED.getName());
  }

  @Override
  public Optional<Response> handle(final HandlerInput input, final IntentRequest intent)
      throws NoCountrySlotException, NoJsonFileException {
    final Slot countrySlot = intent.getIntent().getSlots().get(Slots.COUNTRY);
    if (countrySlot.getValue() == null) {
      throw new NoCountrySlotException("No country slot found.");
    }
    final Country country = CountryFactory.getCountry(countrySlot.getValue());

    final Map<String, Object>  sessionAttributes = input.getAttributesManager().getSessionAttributes();
    sessionAttributes.put(Attributes.COUNTRY.getName(), country.getAbbreviation());
    input.getAttributesManager().setSessionAttributes(sessionAttributes);

    final Map<String, Object>  requestAttributes = input.getAttributesManager().getRequestAttributes();
    return input
        .getResponseBuilder()
        .withSpeech(
            String.format(
                getFromBundle(requestAttributes, BundleKeys.CITIES_IN_COUNTRY_FMT),
                country.getName(),
                country.getPrettyCityNames()))
        .withReprompt(getFromBundle(requestAttributes, BundleKeys.DEFAULT_ASK_FOR_CITY))
        .withShouldEndSession(false)
        .build();
  }
}
