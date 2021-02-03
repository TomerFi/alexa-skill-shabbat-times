/**
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

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.auto.value.AutoValue;
import java.util.List;

/** City value abstraction. */
@AutoValue
@JsonDeserialize(builder = AutoValue_City.Builder.class)
public abstract class City {
  public abstract String cityName();

  public abstract String geoName();

  public abstract int geoId();

  public abstract String countryAbbreviation();

  public abstract List<String> aliases();

  /** City value builder abstraction. */
  @AutoValue.Builder
  public abstract static class Builder {
    @JsonProperty("cityName")
    public abstract Builder cityName(String cityName);

    @JsonProperty("geoName")
    public abstract Builder geoName(String geoName);

    @JsonProperty("geoId")
    public abstract Builder geoId(int geoId);

    @JsonProperty("countryAbbreviation")
    public abstract Builder countryAbbreviation(String countryAbbreviation);

    @JsonProperty("aliases")
    public abstract Builder aliases(List<String> aliases);

    public abstract City build();
  }
}
