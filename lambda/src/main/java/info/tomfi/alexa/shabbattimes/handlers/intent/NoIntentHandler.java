package info.tomfi.alexa.shabbattimes.handlers.intent;

import static info.tomfi.alexa.shabbattimes.AttributeKey.COUNTRY;
import static info.tomfi.alexa.shabbattimes.AttributeKey.LAST_INTENT;
import static info.tomfi.alexa.shabbattimes.BundleKey.DEFAULT_OK;
import static info.tomfi.alexa.shabbattimes.BundleKey.DEFAULT_PLEASE_CLARIFY;
import static info.tomfi.alexa.shabbattimes.BundleKey.DEFAULT_REPROMPT;
import static info.tomfi.alexa.shabbattimes.BundleKey.NOT_FOUND_FMT;
import static info.tomfi.alexa.shabbattimes.IntentType.COUNTRY_SELECTED;
import static info.tomfi.alexa.shabbattimes.IntentType.HELP;
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

/** Intent request handler for handling intent requests with the name {@value NO}. */
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
    if (!sessionAttribs.containsKey(LAST_INTENT.toString())) {
      // if the user said no after a launch request
      return askForClarification(input);
    } else if (sessionAttribs.get(LAST_INTENT.toString()).equals(COUNTRY_SELECTED.toString())) {
      // if the user is following up on a country selected intent request
      return countrySelectedFollowUp(input, sessionAttribs);
    } else if (sessionAttribs.get(LAST_INTENT.toString()).equals(HELP.toString())) {
      // if the user is following up on an help intent request
      return askForClarification(input);
    } else {
      // nothing else to do, end the session
      return input.getResponseBuilder().withShouldEndSession(true).build();
    }
  }

  private Optional<Response> askForClarification(final HandlerInput input) {
    // get request attributes
    var requestAttribs = input.getAttributesManager().getRequestAttributes();
    return input
        .getResponseBuilder()
        .withSpeech(textor.getText(requestAttribs, DEFAULT_PLEASE_CLARIFY))
        .withReprompt(textor.getText(requestAttribs, DEFAULT_REPROMPT))
        .withShouldEndSession(false)
        .build();
  }

  private Optional<Response> countrySelectedFollowUp(
      final HandlerInput input, final Map<String, Object> sessionAttribs) {
    if (!sessionAttribs.containsKey(COUNTRY.toString())) {
      // nothing else to do, end the session
      return input.getResponseBuilder().withShouldEndSession(true).build();
    }
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
