package info.tomfi.alexa.shabbattimes;

import java.util.List;

/** Service for loading the cities data. */
public interface LoaderService {
  /**
   * Load the city data for a specific country based on its abbreviation.
   *
   * @param abbreviation the abbreviation to load the cities by.
   * @return the List of City instances.
   */
  List<City> loadCities(String abbreviation);
}
