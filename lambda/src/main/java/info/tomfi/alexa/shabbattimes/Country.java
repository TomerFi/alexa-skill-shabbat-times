/**
 * Copyright Tomer Figenblat Licensed under the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in
 * writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific
 * language governing permissions and limitations under the License.
 */
package info.tomfi.alexa.shabbattimes;

import static java.util.stream.Collectors.joining;

import com.google.auto.value.AutoValue;
import java.util.List;
import java.util.Optional;

@AutoValue
public abstract class Country {
  public abstract String abbreviation();

  public abstract String name();

  public abstract List<City> cities();

  public abstract BundleKey bundleKey();

  public abstract List<String> utterances();

  public String stringCities() {
    return cities().stream().map(cityObj -> cityObj.cityName()).collect(joining(", "));
  }

  public boolean hasUtterance(final String utterance) {
    return utterances().contains(utterance);
  }

  public Optional<City> getCity(final String alias) {
    for (var city : cities()) {
      if (city.aliases().contains(alias)) {
        return Optional.of(city);
      }
    }
    return Optional.empty();
  }

  public static final Builder builder() {
    return new AutoValue_Country.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {
    public abstract Builder abbreviation(String abbreviation);

    public abstract Builder name(String name);

    public abstract Builder cities(List<City> cities);

    public abstract Builder bundleKey(BundleKey bundleKey);

    public abstract Builder utterances(List<String> utterances);

    public abstract Country build();
  }
}
