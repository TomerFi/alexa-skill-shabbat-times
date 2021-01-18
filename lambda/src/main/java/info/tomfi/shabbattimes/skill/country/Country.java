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
package info.tomfi.shabbattimes.skill.country;

import static info.tomfi.shabbattimes.skill.tools.SkillTools.getCityListFromJsonFile;
import static java.util.stream.Collectors.joining;

import info.tomfi.shabbattimes.skill.city.City;
import info.tomfi.shabbattimes.skill.enums.CountryInfo;
import info.tomfi.shabbattimes.skill.exception.NoJsonFileException;
import info.tomfi.shabbattimes.skill.tools.DynTypeIterator;
import java.util.Iterator;
import java.util.List;

/**
 * Pojo for constructing the Country object from the backend json files.
 *
 * @author Tomer Figenblat {@literal <tomer.figenblat@gmail.com>}
 */
public final class Country implements Iterable<City> {
  private final String abbreviation;
  private final String name;

  private final List<City> citiesList;

  /**
   * Main and only constructor, call for the static tool creating the list of cities.
   *
   * @param country the {@link info.tomfi.shabbattimes.skill.enums.CountryInfo} member
   *     describing the country.
   * @throws NoJsonFileException when a country corresponding backend json file was not found.
   */
  protected Country(final CountryInfo country) throws NoJsonFileException {
    abbreviation = country.getAbbreviation();
    name = country.getName();
    citiesList = getCityListFromJsonFile(abbreviation);
  }

  public String getAbbreviation() {
    return abbreviation;
  }

  public String getName() {
    return name;
  }

  @Override
  public Iterator<City> iterator() {
    return new DynTypeIterator<>(citiesList);
  }

  /**
   * Get a string represnting all the city names in to this country.
   *
   * @return a string containg the cities list joined by a comma.
   */
  public String getPrettyCityNames() {
    return citiesList.stream().map(cityObj -> cityObj.getCityName()).collect(joining(", "));
  }
}
