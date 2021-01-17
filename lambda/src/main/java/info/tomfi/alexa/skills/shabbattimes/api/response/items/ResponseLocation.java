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
package info.tomfi.alexa.skills.shabbattimes.api.response.items;

import com.google.api.client.util.Key;

/**
 * Pojo for consuming a json location from the api response location object.
 *
 * @author Tomer Figenblat {@literal <tomer.figenblat@gmail.com>}
 */
public final class ResponseLocation {
  @Key private String admin1;
  @Key private String asciiname;
  @Key private String city;
  @Key private String country;
  @Key private String geo;
  @Key private int geonameid;
  @Key private Double latitude;
  @Key private Double longitude;
  @Key private String title;
  @Key private String tzid;

  public ResponseLocation() {
    //
  }

  public String getAdmin1() {
    return admin1;
  }

  public String getAsciiname() {
    return asciiname;
  }

  public String getCity() {
    return city;
  }

  public String getCountry() {
    return country;
  }

  public String getGeo() {
    return geo;
  }

  public int getGeonameid() {
    return geonameid;
  }

  public Double getLatitude() {
    return latitude;
  }

  public Double getLongitude() {
    return longitude;
  }

  public String getTitle() {
    return this.title;
  }

  public String getTzid() {
    return this.tzid;
  }
}
