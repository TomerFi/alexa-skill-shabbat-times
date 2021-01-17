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
package info.tomfi.alexa.skills.shabbattimes.city;

import static lombok.AccessLevel.PROTECTED;

import info.tomfi.alexa.skills.shabbattimes.tools.DynTypeIterator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.val;

/**
 * Pojo for creatign City objects from the backend json files.
 *
 * @author Tomer Figenblat {@literal <tomer.figenblat@gmail.com>}
 */
@NoArgsConstructor(access = PROTECTED)
@SuppressWarnings("PMD.ShortClassName")
public final class City implements Iterable<String> {
  @Getter private String cityName;
  @Getter private String geoName;
  @Getter private int geoId;
  @Getter private String countryAbbreviation;

  private String[] aliases;

  @Override
  public Iterator<String> iterator() {
    val nameList = new ArrayList<>(Arrays.asList(aliases));
    nameList.add(cityName);
    return new DynTypeIterator<>(nameList);
  }
}
