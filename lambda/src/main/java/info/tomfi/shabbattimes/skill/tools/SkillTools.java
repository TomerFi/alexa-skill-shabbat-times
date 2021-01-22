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

import static info.tomfi.shabbattimes.skill.tools.CityLocator.getByCityAndCountry;
import static java.util.stream.Collectors.toList;

import com.amazon.ask.attributes.AttributesManager;
import com.amazon.ask.model.Slot;
import com.fasterxml.jackson.databind.ObjectMapper;

import info.tomfi.shabbattimes.skill.city.City;
import info.tomfi.shabbattimes.skill.enums.Attributes;
import info.tomfi.shabbattimes.skill.enums.Slots;
import info.tomfi.shabbattimes.skill.exception.NoCitySlotException;
import info.tomfi.shabbattimes.skill.exception.NoJsonFileException;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Utility class for working with the skill City objects.
 *
 * @author Tomer Figenblat {@literal <tomer.figenblat@gmail.com>}
 */
public final class SkillTools {
  private static final ObjectMapper MAPPER = new ObjectMapper();

  private SkillTools() {
    //
  }

  /**
   * A static tool for retrieving the populated city slot from the request slot map.
   *
   * @param slots the map of slots retrieved from the request.
   * @return the populated slot holding the selected city.
   * @throws NoCitySlotException when no populated slot was found.
   */
  public static Slot getCitySlotFromMap(final Map<String, Slot> slots) throws NoCitySlotException {
    final List<String> cityKeys =
        Arrays.stream(Slots.City.values()).map(memebr -> memebr.getName()).collect(toList());
    final Optional<String> slotKey =
        slots.keySet().stream()
            .filter(key -> cityKeys.contains(key))
            .filter(key -> slots.get(key).getValue() != null)
            .findFirst();

    if (!slotKey.isPresent()) {
      throw new NoCitySlotException("No city slot found.");
    }
    return slots.get(slotKey.get());
  }

  /**
   * A static tool for creating a List of {@link info.tomfi.shabbattimes.skill.city.City}
   * objects based on the backend json files.
   *
   * @param countryAbbreviation country abbreviation for constructing the json files name
   *     %abbreviation%_Cities.json.
   * @return a List of {@link info.tomfi.shabbattimes.skill.city.City} objects.
   * @throws NoJsonFileException when the backend json file was not found.
   */
  public static List<City> getCityListFromJsonFile(final String countryAbbreviation)
      throws NoJsonFileException {
    final String jsonFileName = String.format("cities/%s_Cities.json", countryAbbreviation);
    try (
      final BufferedReader breader = Files.newBufferedReader(
        Paths.get(
            Thread.currentThread()
                .getContextClassLoader()
                .getResource(jsonFileName)
                .toURI()))
    ) {
      final City[] cityArray = MAPPER.readValue(breader, City[].class);
      return Arrays.asList(cityArray);
    } catch (IOException | NullPointerException | URISyntaxException exc) {
      throw new NoJsonFileException("No json file found", exc);
    }
  }

  /**
   * A static tool for retrieving the {@link info.tomfi.shabbattimes.skill.city.City} object
   * from the slots map and saving the appropriate information as session attributes.
   *
   * @param slots the map of slots retrieved from the request.
   * @param attribManager the AttributesManager object for retrieving and saving the session
   *     attributes.
   * @return the {@link info.tomfi.shabbattimes.skill.city.City} object.
   */
  public static City getCityFromSlots(
      final Map<String, Slot> slots, final AttributesManager attribManager) {
    final City selectedCity = getByCityAndCountry(slots.get(Slots.COUNTRY), getCitySlotFromMap(slots));
    final Map<String, Object> sessionAttributes = attribManager.getSessionAttributes();
    sessionAttributes.put(Attributes.COUNTRY.getName(), selectedCity.getCountryAbbreviation());
    sessionAttributes.put(Attributes.CITY.getName(), selectedCity.getCityName());
    attribManager.setSessionAttributes(sessionAttributes);
    return selectedCity;
  }
}