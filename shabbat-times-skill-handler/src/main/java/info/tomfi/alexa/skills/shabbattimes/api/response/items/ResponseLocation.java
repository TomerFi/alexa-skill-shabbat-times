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
package info.tomfi.alexa.skills.shabbattimes.api.response.items;

import com.google.api.client.util.Key;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Pojo for consuming a json location from the api response location object.
 *
 * @author Tomer Figenblat {@literal <tomer.figenblat@gmail.com>}
 */
@NoArgsConstructor
public final class ResponseLocation {
  @Key @Getter private String admin1;
  @Key @Getter private String asciiname;
  @Key @Getter private String city;
  @Key @Getter private String country;
  @Key @Getter private String geo;
  @Key @Getter private int geonameid;
  @Key @Getter private Double latitude;
  @Key @Getter private Double longitude;
  @Key @Getter private String title;
  @Key @Getter private String tzid;
}
