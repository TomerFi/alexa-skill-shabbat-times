package info.tomfi.alexa.shabbattimes;

/** Enum used for identifying the attribute keys. */
public enum AttributeKey {
  CITY("city"),
  COUNTRY("country"),
  L10N_BUNDLE("l10nBunble"),
  LAST_INTENT("lastIntent");

  private final String key;

  AttributeKey(final String setKey) {
    key = setKey;
  }

  @Override
  public String toString() {
    return key;
  }
}
