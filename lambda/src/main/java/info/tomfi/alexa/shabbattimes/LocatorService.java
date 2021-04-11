/*
 * Copyright Tomer Figenblat.
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
