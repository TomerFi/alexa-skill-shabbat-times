package info.tomfi.alexa.shabbattimes.handlers.intent;

import static info.tomfi.alexa.shabbattimes.AttributeKey.COUNTRY;
import static info.tomfi.alexa.shabbattimes.BundleKey.CITIES_IN_COUNTRY_FMT;
import static info.tomfi.alexa.shabbattimes.BundleKey.DEFAULT_ASK_FOR_CITY;
import static info.tomfi.alexa.shabbattimes.IntentType.COUNTRY_SELECTED;
import static info.tomfi.alexa.shabbattimes.SlotName.COUNTRY_SLOT;
import static java.util.Objects.isNull;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.impl.IntentRequestHandler;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Response;
import info.tomfi.alexa.shabbattimes.Country;
import info.tomfi.alexa.shabbattimes.TextService;
import info.tomfi.alexa.shabbattimes.exceptions.NoCountryFoundException;
import info.tomfi.alexa.shabbattimes.exceptions.NoCountrySlotException;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;

/** Intent request handler for handling intent requests with the name {@value COUNTRY_SELECTED}. */
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
  public boolean canHandle(final HandlerInput input, final IntentRequest request) {
    return request.getIntent().getName().equals(COUNTRY_SELECTED.toString());
  }

  @Override
  public Optional<Response> handle(final HandlerInput input, final IntentRequest request) {
    var slots = request.getIntent().getSlots();
    if (Boolean.FALSE.equals(slots.containsKey(COUNTRY_SLOT))
        || isNull(slots.get(COUNTRY_SLOT).getValue())) {
      throw new NoCountrySlotException("No country slot found.");
    }
    // get the requested country slot
    var countrySlot = slots.get(COUNTRY_SLOT);
    // get the country object
    var country =
        countries.stream()
            .filter(c -> c.hasUtterance(countrySlot.getValue().toLowerCase()))
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
