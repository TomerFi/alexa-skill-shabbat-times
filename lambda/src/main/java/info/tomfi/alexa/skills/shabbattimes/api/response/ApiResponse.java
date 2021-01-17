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
package info.tomfi.alexa.skills.shabbattimes.api.response;

import com.google.api.client.util.Key;
import info.tomfi.alexa.skills.shabbattimes.api.response.items.ResponseItem;
import info.tomfi.alexa.skills.shabbattimes.api.response.items.ResponseLocation;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Pojo for consuming json response from the api.
 *
 * @author Tomer Figenblat {@literal <tomer.figenblat@gmail.com>}
 */
@NoArgsConstructor
public final class ApiResponse {
  @Key @Getter private String date;
  @Key @Getter private List<ResponseItem> items;
  @Key @Getter private String link;
  @Key @Getter private ResponseLocation location;
  @Key @Getter private String title;
}
