/**
 * Copyright Tomer Figenblat Licensed under the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in
 * writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific
 * language governing permissions and limitations under the License.
 */
package info.tomfi.alexa.shabbattimes.internal;

import static info.tomfi.alexa.shabbattimes.SlotName.COUNTRY_SLOT;
import static info.tomfi.alexa.shabbattimes.internal.CityLocator.locate;
import static java.util.stream.Collectors.toList;

import com.amazon.ask.attributes.AttributesManager;
import com.amazon.ask.model.Slot;
import com.fasterxml.jackson.databind.ObjectMapper;
import info.tomfi.alexa.shabbattimes.AttributeKey;
import info.tomfi.alexa.shabbattimes.City;
import info.tomfi.alexa.shabbattimes.Country;
import info.tomfi.alexa.shabbattimes.SlotName.CitySlot;
import info.tomfi.alexa.shabbattimes.exceptions.NoCitySlotException;
import info.tomfi.alexa.shabbattimes.exceptions.NoJsonFileException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/** Utility class for working with the skill City objects. */
public final class SkillTools {
  private SkillTools() {
    //
  }

  /**
   * A static tool for retrieving the populated city slot from the request slot map.
   *
   * @param slots the map of slots retrieved from the request.
   * @return the populated slot holding the selected city.
   */
  public static Slot getCitySlotFromMap(final Map<String, Slot> slots) {
    var slotKeys = Arrays.stream(CitySlot.values()).map(Object::toString).collect(toList());
    var selectedKey =
        slots.keySet().stream()
            .filter(slotKeys::contains)
            .map(slots::get)
            .map(Slot::getValue)
            .filter(Objects::nonNull)
            .findFirst()
            .orElseThrow(NoCitySlotException::new);

    return slots.get(selectedKey);
  }

  /**
   * A static tool for creating a List of {@link info.tomfi.alexa.shabbattimes.city.City} objects
   * based on the backend json files.
   *
   * @param countryAbbreviation country abbreviation for constructing the json files name
   *     %abbreviation%_Cities.json.
   * @return a List of {@link info.tomfi.alexa.shabbattimes.city.City} objects.
   */
  public static List<City> getCityListFromJsonFile(final String countryAbbreviation) {
    var jsonFileName = String.format("cities/%s_Cities.json", countryAbbreviation);
    try (var json = SkillTools.class.getClassLoader().getResourceAsStream(jsonFileName)) {
      return Arrays.asList(new ObjectMapper().readValue(json, City[].class));
    } catch (IOException | NullPointerException exc) {
      throw new NoJsonFileException("No json file found", exc);
    }
  }

  /**
   * A static tool for retrieving the {@link info.tomfi.alexa.shabbattimes.city.City} object from
   * the slots map and saving the appropriate information as session attributes.
   *
   * @param slots the map of slots retrieved from the request.
   * @param attribManager the AttributesManager object for retrieving and saving the session
   *     attributes.
   * @return the {@link info.tomfi.alexa.shabbattimes.city.City} object.
   */
  public static City getCityFromSlots(
      final Map<String, Slot> slots,
      final AttributesManager attribManager,
      final List<Country> countries) {
    var selectedCity = locate(slots.get(COUNTRY_SLOT), getCitySlotFromMap(slots), countries);
    var sessionAttributes = attribManager.getSessionAttributes();
    sessionAttributes.put(AttributeKey.COUNTRY.toString(), selectedCity.countryAbbreviation());
    sessionAttributes.put(AttributeKey.CITY.toString(), selectedCity.cityName());
    attribManager.setSessionAttributes(sessionAttributes);
    return selectedCity;
  }
}
