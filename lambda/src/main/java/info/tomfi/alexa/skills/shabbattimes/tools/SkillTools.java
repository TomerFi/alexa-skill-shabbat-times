/**
 * Copyright 2019 Tomer Figenblat
 *
 * <p>Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of the License at
 *
 * <p>http://www.apache.org/licenses/LICENSE-2.0
 *
 * <p>Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing permissions and
 * limitations under the License.
 */
package info.tomfi.alexa.skills.shabbattimes.tools;

import static info.tomfi.alexa.skills.shabbattimes.tools.CityLocator.getByCityAndCountry;
import static java.util.stream.Collectors.toList;
import static lombok.AccessLevel.PRIVATE;

import com.amazon.ask.attributes.AttributesManager;
import com.amazon.ask.model.Slot;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import info.tomfi.alexa.skills.shabbattimes.city.City;
import info.tomfi.alexa.skills.shabbattimes.enums.Attributes;
import info.tomfi.alexa.skills.shabbattimes.enums.Slots;
import info.tomfi.alexa.skills.shabbattimes.exception.NoCitySlotException;
import info.tomfi.alexa.skills.shabbattimes.exception.NoJsonFileException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import lombok.Cleanup;
import lombok.NoArgsConstructor;
import lombok.val;

/**
 * Utility class for working with the skill City objects.
 *
 * @author Tomer Figenblat {@literal <tomer.figenblat@gmail.com>}
 */
@NoArgsConstructor(access = PRIVATE)
public final class SkillTools {
  private static final Gson GSNO_PARSER = new GsonBuilder().create();

  /**
   * A static tool for retrieving the populated city slot from the request slot map.
   *
   * @param slots the map of slots retrieved from the request.
   * @return the populated slot holding the selected city.
   * @throws NoCitySlotException when no populated slot was found.
   */
  public static Slot getCitySlotFromMap(final Map<String, Slot> slots) throws NoCitySlotException {
    val cityKeys =
        Arrays.stream(Slots.City.values()).map(memebr -> memebr.getName()).collect(toList());
    val slotKey =
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
   * A static tool for creating a List of {@link info.tomfi.alexa.skills.shabbattimes.city.City}
   * objects based on the backend json files.
   *
   * @param countryAbbreviation country abbreviation for constructing the json files name
   *     %abbreviation%_Cities.json.
   * @return a List of {@link info.tomfi.alexa.skills.shabbattimes.city.City} objects.
   * @throws NoJsonFileException when the backend json file was not found.
   */
  @SuppressWarnings({"PMD.AvoidCatchingGenericException", "PMD.AvoidCatchingNPE"})
  public static List<City> getCityListFromJsonFile(final String countryAbbreviation)
      throws NoJsonFileException {
    val jsonFileName = String.format("cities/%s_Cities.json", countryAbbreviation);
    try {
      @Cleanup
      val breader =
          Files.newBufferedReader(
              Paths.get(
                  Thread.currentThread()
                      .getContextClassLoader()
                      .getResource(jsonFileName)
                      .toURI()));
      val cityArray = GSNO_PARSER.fromJson(breader, City[].class);
      return Arrays.asList(cityArray);
    } catch (IOException | NullPointerException | URISyntaxException exc) {
      throw new NoJsonFileException("No json file found", exc);
    }
  }

  /**
   * A static tool for retrieving the {@link info.tomfi.alexa.skills.shabbattimes.city.City} object
   * from the slots map and saving the appropriate information as session attributes.
   *
   * @param slots the map of slots retrieved from the request.
   * @param attribManager the AttributesManager object for retrieving and saving the session
   *     attributes.
   * @return the {@link info.tomfi.alexa.skills.shabbattimes.city.City} object.
   */
  public static City getCityFromSlots(
      final Map<String, Slot> slots, final AttributesManager attribManager) {
    val selectedCity = getByCityAndCountry(slots.get(Slots.COUNTRY), getCitySlotFromMap(slots));
    val sessionAttributes = attribManager.getSessionAttributes();
    sessionAttributes.put(Attributes.COUNTRY.getName(), selectedCity.getCountryAbbreviation());
    sessionAttributes.put(Attributes.CITY.getName(), selectedCity.getCityName());
    attribManager.setSessionAttributes(sessionAttributes);
    return selectedCity;
  }
}
