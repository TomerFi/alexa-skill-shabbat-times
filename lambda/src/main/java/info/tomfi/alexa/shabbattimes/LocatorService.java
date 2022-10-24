package info.tomfi.alexa.shabbattimes;

import com.amazon.ask.model.Slot;

/** Service for locating city slots. */
public interface LocatorService {
  /**
   * Use for locating a city without stating the country to locate in.
   *
   * @param citySlot the city Slot for locating the city by.
   * @return the City instance located.
   */
  City locate(Slot citySlot);

  /**
   * Use for locating a city within a specific country.
   *
   * @param countrySlot the country Slot for locating the country by.
   * @param citySlot the city Slot for locating the city by.
   * @return the City instance located.
   */
  City locate(Slot countrySlot, Slot citySlot);
}
