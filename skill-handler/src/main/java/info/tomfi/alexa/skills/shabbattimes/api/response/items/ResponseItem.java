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
import java.util.Optional;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Pojo for consuming a json item from the api response items list.
 *
 * @author Tomer Figenblat {@literal <tomer.figenblat@gmail.com>}
 */
@NoArgsConstructor
public final class ResponseItem {
  @Key @Getter private String hebrew;
  @Key @Getter private String date;
  @Key @Getter private String title;
  @Key @Getter private String category;

  @Key private String link;
  @Key private String memo;
  @Key private String subcat;

  @Key @Getter private boolean yomtov;

  public Optional<String> getLink() {
    return Optional.ofNullable(link);
  }

  public Optional<String> getMemo() {
    return Optional.ofNullable(memo);
  }

  public Optional<String> getSubcat() {
    return Optional.ofNullable(subcat);
  }
}
