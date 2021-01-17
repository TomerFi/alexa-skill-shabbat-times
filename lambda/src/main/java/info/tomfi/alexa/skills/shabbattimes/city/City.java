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

import info.tomfi.alexa.skills.shabbattimes.tools.DynTypeIterator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Pojo for creatign City objects from the backend json files.
 *
 * @author Tomer Figenblat {@literal <tomer.figenblat@gmail.com>}
 */
public final class City implements Iterable<String> {
  private String cityName;
  private String geoName;
  private int geoId;
  private String countryAbbreviation;

  private String[] aliases;

  protected City() {
    //
  }

  @Override
  public Iterator<String> iterator() {
    final List<String> nameList = new ArrayList<>(Arrays.asList(aliases));
    nameList.add(cityName);
    return new DynTypeIterator<>(nameList);
  }

  public String getCityName() {
    return cityName;
  }

  public String getGeoName() {
    return geoName;
  }

  public int getGeoId() {
    return geoId;
  }

  public String getCountryAbbreviation() {
    return countryAbbreviation;
  }
}
