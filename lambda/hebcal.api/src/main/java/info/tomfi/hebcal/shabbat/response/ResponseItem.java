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
package info.tomfi.hebcal.shabbat.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.auto.value.AutoValue;
import java.util.Optional;

import javax.annotation.Nullable;

@AutoValue
@JsonDeserialize(builder = AutoValue_ResponseItem.Builder.class)
public abstract class ResponseItem {
  public abstract String hebrew();
  public abstract String date();
  public abstract String title();
  public abstract String category();

  public abstract Optional<String> titleOrig();
  public abstract Optional<String> link();
  public abstract Optional<String> memo();
  public abstract Optional<String> subcat();
  public abstract Optional<Boolean> yomtov();

  @AutoValue.Builder
  public abstract static class Builder {
    @JsonProperty("hebrew") public abstract Builder hebrew(final String hebrew);
    @JsonProperty("date") public abstract Builder date(final String date);
    @JsonProperty("title") public abstract Builder title(final String title);
    @JsonProperty("category") public abstract Builder category(final String category);

    @JsonProperty("title_orig") public abstract Builder titleOrig(@Nullable final String titleOrig);
    @JsonProperty("link") public abstract Builder link(@Nullable final String link);
    @JsonProperty("memo") public abstract Builder memo(@Nullable final String memo);
    @JsonProperty("subcat") public abstract Builder subcat(@Nullable final String subcat);

    @JsonProperty("yomtov") public abstract Builder yomtov(@Nullable final Boolean yomtov);

    public abstract ResponseItem build();
  }
}
