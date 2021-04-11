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
