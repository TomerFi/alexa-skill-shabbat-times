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
@JsonDeserialize(builder = AutoValue_ResponseLocation.Builder.class)
public abstract class ResponseLocation {
  public abstract String admin1();
  public abstract String asciiname();
  public abstract String city();
  public abstract String country();
  public abstract String geo();
  public abstract int geonameid();
  public abstract Double latitude();
  public abstract Double longitude();
  public abstract Optional<String> cc();
  public abstract String title();
  public abstract String tzid();

  @AutoValue.Builder
  public abstract static class Builder {
    @JsonProperty("admin1") public abstract Builder admin1(final String admin1);
    @JsonProperty("asciiname") public abstract Builder asciiname(final String asciiname);
    @JsonProperty("city") public abstract Builder city(final String city);
    @JsonProperty("country") public abstract Builder country(final String country);
    @JsonProperty("geo") public abstract Builder geo(final String geo);
    @JsonProperty("geonameid") public abstract Builder geonameid(final int geonameid);
    @JsonProperty("latitude") public abstract Builder latitude(final Double latitude);
    @JsonProperty("longitude") public abstract Builder longitude(final Double longitude);
    @JsonProperty("cc") public abstract Builder cc(@Nullable final String cc);
    @JsonProperty("title") public abstract Builder title(final String title);
    @JsonProperty("tzid") public abstract Builder tzid(final String tzid);

    public abstract ResponseLocation build();
  }
}
