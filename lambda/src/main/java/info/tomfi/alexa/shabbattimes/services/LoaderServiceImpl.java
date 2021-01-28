/**
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
package info.tomfi.alexa.shabbattimes.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import info.tomfi.alexa.shabbattimes.City;
import info.tomfi.alexa.shabbattimes.LoaderService;
import info.tomfi.alexa.shabbattimes.exceptions.NoJsonFileException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public final class LoaderServiceImpl implements LoaderService {
  private final ObjectMapper mapper;

  public LoaderServiceImpl() {
    mapper = new ObjectMapper();
  }

  @Override
  public List<City> loadCities(final String abbreviation) {
    var jsonFileName = String.format("cities/%s_Cities.json", abbreviation);
    try (var json = getClass().getClassLoader().getResourceAsStream(jsonFileName)) {
      return Arrays.asList(mapper.readValue(json, City[].class));
    } catch (IOException | NullPointerException exc) {
      throw new NoJsonFileException(exc);
    }
  }
}
