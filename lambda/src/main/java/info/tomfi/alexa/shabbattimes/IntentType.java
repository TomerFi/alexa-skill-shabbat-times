package info.tomfi.alexa.shabbattimes;

/** Enum for identifying the the intent names. */
public enum IntentType {
  CANCEL("AMAZON.CancelIntent"),
  COUNTRY_SELECTED("CountrySelected"),
  FALLBACK("AMAZON.FallbackIntent"),
  GET_CITY("GetCityIntent"),
  HELP("AMAZON.HelpIntent"),
  NO("AMAZON.NoIntent"),
  STOP("AMAZON.StopIntent"),
  THANKS("ThanksIntent"),
  YES("AMAZON.YesIntent");

  private final String type;

  IntentType(final String setType) {
    type = setType;
  }

  @Override
  public String toString() {
    return type;
  }
}
