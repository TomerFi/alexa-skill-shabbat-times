/**
 * Copyright Tomer Figenblat Licensed under the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in
 * writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific
 * language governing permissions and limitations under the License.
 */
package info.tomfi.alexa.shabbattimes.internal;

import static java.util.Objects.isNull;

import com.amazon.ask.model.Slot;
import info.tomfi.alexa.shabbattimes.City;
import info.tomfi.alexa.shabbattimes.Country;
import info.tomfi.alexa.shabbattimes.exceptions.NoCityFoundException;
import info.tomfi.alexa.shabbattimes.exceptions.NoCityInCountryException;
import info.tomfi.alexa.shabbattimes.exceptions.NoCountryFoundException;
import java.util.List;
import java.util.Optional;

/** Utility class for locating a city within the backend country and cities data. */
public final class CityLocator {
  private CityLocator() {
    //
  }

  /**
   * A static tool for creating the country and city objects.
   *
   * @param countrySlot the Slot holding the country value collected from the customer.
   * @param citySlot the Slot holding the city value collected from the customer.
   * @return the {@link info.tomfi.alexa.shabbattimes.city.City} object.
   */
  public static City locate(
      final Slot countrySlot, final Slot citySlot, final List<Country> countries) {
    return isNull(countrySlot.getValue())
        ? locateCity(citySlot.getValue(), countries)
        : locateCityInCountry(countrySlot.getValue(), citySlot.getValue(), countries);
  }

  private static City locateCityInCountry(
      final String countryUtterance, final String cityAlias, final List<Country> countries) {
    var country =
        countries.stream()
            .filter(c -> c.hasUtterance(countryUtterance))
            .findFirst()
            .orElseThrow(NoCountryFoundException::new);

    return country.getCity(cityAlias).orElseThrow(NoCityInCountryException::new);
  }

  private static City locateCity(final String cityAlias, final List<Country> countries) {
    return countries.stream()
        .map(c -> c.getCity(cityAlias))
        .flatMap(Optional<City>::stream)
        .findFirst()
        .orElseThrow(NoCityFoundException::new);
  }
}
