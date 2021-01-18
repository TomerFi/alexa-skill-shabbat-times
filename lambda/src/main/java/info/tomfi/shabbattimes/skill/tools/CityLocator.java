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
package info.tomfi.shabbattimes.skill.tools;

import com.amazon.ask.model.Slot;
import info.tomfi.shabbattimes.skill.city.City;
import info.tomfi.shabbattimes.skill.country.Country;
import info.tomfi.shabbattimes.skill.country.CountryFactory;
import info.tomfi.shabbattimes.skill.enums.CountryInfo;
import info.tomfi.shabbattimes.skill.exception.NoCityFoundException;
import info.tomfi.shabbattimes.skill.exception.NoCityInCountryException;
import java.util.Iterator;
import java.util.Optional;

/**
 * Utility class for locating a city within the backend country and cities data.
 *
 * @author Tomer Figenblat {@literal <tomer.figenblat@gmail.com>}
 */
public final class CityLocator {
  private CityLocator() {
    //
  }

  /**
   * A static tool for creating the country and city objects.
   *
   * @param countrySlot the Slot holding the country value collected from the customer.
   * @param citySlot the Slot holding the city value collected from the customer.
   * @return the {@link info.tomfi.shabbattimes.skill.city.City} object.
   * @throws NoCityFoundException when an unknown city was provided with no country specification.
   * @throws NoCityInCountryException when an unknown city within a country was encountered.
   */
  public static City getByCityAndCountry(final Slot countrySlot, final Slot citySlot)
      throws NoCityFoundException, NoCityInCountryException {
    if (countrySlot.getValue() == null) {
      return getByCity(citySlot);
    }
    final Country country = CountryFactory.getCountry(countrySlot.getValue());
    final Optional<City> cityOpt = findCityInCountry(country, citySlot.getValue());
    if (cityOpt.isPresent()) {
      return cityOpt.get();
    }
    throw new NoCityInCountryException(
        String.format("no city %s in %s.", citySlot.getValue(), countrySlot.getValue()));
  }

  private static City getByCity(final Slot citySlot) {
    for (final CountryInfo member : CountryInfo.values()) {
      final Country country = CountryFactory.getCountry(member);
      final  Optional<City> cityOpt = findCityInCountry(country, citySlot.getValue());
      if (cityOpt.isPresent()) {
        return cityOpt.get();
      }
    }
    throw new NoCityFoundException(String.format("city %s not found", citySlot.getValue()));
  }

  private static Optional<City> findCityInCountry(final Country country, final String cityName) {
    final Iterator<City> cities = country.iterator();
    while (cities.hasNext()) {
      final City currentCity = cities.next();
      final Iterator<String> aliases = currentCity.iterator();
      while (aliases.hasNext()) {
        final String currentAlias = aliases.next();
        if (currentAlias.equalsIgnoreCase(cityName)) {
          return Optional.of(currentCity);
        }
      }
    }
    return Optional.empty();
  }
}
