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
package info.tomfi.alexa.skills.shabbattimes.country;

import static lombok.AccessLevel.PRIVATE;

import info.tomfi.alexa.skills.shabbattimes.enums.CountryInfo;
import info.tomfi.alexa.skills.shabbattimes.exception.NoJsonFileException;
import info.tomfi.alexa.skills.shabbattimes.exception.UnknownCountryException;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.NoArgsConstructor;
import lombok.Synchronized;
import lombok.val;

/**
 * A factory for managing and creating {@link info.tomfi.alexa.skills.shabbattimes.country.Country}
 * objects.
 *
 * @author Tomer Figenblat {@literal <tomer.figenblat@gmail.com>}
 */
@NoArgsConstructor(access = PRIVATE)
public final class CountryFactory {
  private static final Map<CountryInfo, Country> COUNTRY_POOL = new ConcurrentHashMap<>();

  /**
   * Factory for creating a {@link info.tomfi.alexa.skills.shabbattimes.country.Country} object from
   * a String country name.
   *
   * <p>The country names are being validated and constructed using the enum class {@link
   * info.tomfi.alexa.skills.shabbattimes.enums.CountryInfo}.
   *
   * @param countryName the name of the country to create object for.
   * @return the created {@link info.tomfi.alexa.skills.shabbattimes.country.Country} object.
   * @throws NoJsonFileException when the appropriate backend json file was not found.
   * @throws UnknownCountryException when the country requested is not found within the {@link
   *     info.tomfi.alexa.skills.shabbattimes.enums.CountryInfo} enum members.
   */
  @Synchronized
  public static Country getCountry(final String countryName)
      throws NoJsonFileException, UnknownCountryException {
    val lowerCountry = countryName.toLowerCase(Locale.ENGLISH);
    for (val current : CountryInfo.values()) {
      if (current.getUtterances().contains(lowerCountry)) {
        return getCountry(current);
      }
    }
    throw new UnknownCountryException(String.join(" ", "unknown country name", countryName));
  }

  /**
   * Factory for creating a {@link info.tomfi.alexa.skills.shabbattimes.country.Country} object from
   * a {@link info.tomfi.alexa.skills.shabbattimes.enums.CountryInfo} member.
   *
   * @param member the {@link info.tomfi.alexa.skills.shabbattimes.enums.CountryInfo} for
   *     constucting the {@link info.tomfi.alexa.skills.shabbattimes.country.Country} object.
   * @return the created {@link info.tomfi.alexa.skills.shabbattimes.country.Country} object.
   * @throws NoJsonFileException when the appropriate backend json file was not found.
   */
  @Synchronized
  public static Country getCountry(final CountryInfo member) throws NoJsonFileException {
    return COUNTRY_POOL.computeIfAbsent(member, newMember -> new Country(newMember));
  }
}
