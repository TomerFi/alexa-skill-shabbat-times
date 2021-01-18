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
package info.tomfi.hebcal.api.response.items;

import com.google.api.client.util.Key;
import java.util.Optional;

/**
 * Pojo for consuming a json item from the api response items list.
 *
 * @author Tomer Figenblat {@literal <tomer.figenblat@gmail.com>}
 */
public final class ResponseItem {
  @Key private String hebrew;
  @Key private String date;
  @Key private String title;
  @Key private String category;

  @Key private String link;
  @Key private String memo;
  @Key private String subcat;

  @Key private boolean yomtov;

  public ResponseItem() {
    //
  }

  public String getHebrew() {
    return hebrew;
  }

  public String getDate() {
    return date;
  }

  public String getTitle() {
    return title;
  }

  public String getCategory() {
    return category;
  }

  public Optional<String> getLink() {
    return Optional.ofNullable(link);
  }

  public Optional<String> getMemo() {
    return Optional.ofNullable(memo);
  }

  public Optional<String> getSubcat() {
    return Optional.ofNullable(subcat);
  }

  public Boolean isYomtov() {
    return yomtov;
  }
}
