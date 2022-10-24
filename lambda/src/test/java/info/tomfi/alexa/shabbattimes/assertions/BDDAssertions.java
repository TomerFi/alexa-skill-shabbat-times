package info.tomfi.alexa.shabbattimes.assertions;

import info.tomfi.alexa.shabbattimes.City;
import info.tomfi.alexa.shabbattimes.Country;

/** Custom assertions starting point. */
public final class BDDAssertions {
  private BDDAssertions() {
    //
  }

  public static CityAssert then(final City actual) {
    return new CityAssert(actual);
  }

  public static CountryAssert then(final Country actual) {
    return new CountryAssert(actual);
  }
}
