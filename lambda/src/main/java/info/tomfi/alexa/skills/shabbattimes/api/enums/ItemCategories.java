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
package info.tomfi.alexa.skills.shabbattimes.api.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Enum helper for constructing the api request, used for parsing the response item list.
 *
 * @author Tomer Figenblat {@literal <tomer.figenblat@gmail.com>}
 */
@RequiredArgsConstructor
public enum ItemCategories {
  CANDLES("candles"),
  HAVDALAH("havdalah"),
  HOLIDAY("holiday"),
  PARASHAT("parashat");

  @Getter private final String value;
}
