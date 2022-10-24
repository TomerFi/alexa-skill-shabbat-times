package info.tomfi.alexa.shabbattimes;

/** Constants and Enums for identifying the request slot keys. */
public final class SlotName {
  /** Constant String for identifying the country slot. */
  public static final String COUNTRY_SLOT = "Country";

  private SlotName() {
    //
  }

  /** Enum for identifying the the city slots. */
  public enum CitySlot {
    GB("City_GB"),
    IL("City_IL"),
    US("City_US");

    private final String name;

    CitySlot(final String setName) {
      name = setName;
    }

    @Override
    public String toString() {
      return name;
    }
  }
}
