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

/** Constants and Enums for identifying the request slot keys. */
public final class SlotName {
  /** Constant String for identifying the country slot. */
  public static final String COUNTRY_SLOT = "Country";

  private SlotName() {
    //
  }

  /** Enum for identifying the the city slots. */
  public enum CitySlot {
    GB("City_GB"),
    IL("City_IL"),
    US("City_US");

    private final String name;

    CitySlot(final String setName) {
      name = setName;
    }

    @Override
    public String toString() {
      return name;
    }
  }
}
