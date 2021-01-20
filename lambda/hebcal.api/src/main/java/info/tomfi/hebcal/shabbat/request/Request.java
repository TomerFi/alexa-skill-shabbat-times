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
package info.tomfi.hebcal.shabbat.request;

import static java.util.Objects.requireNonNull;

import com.google.auto.value.AutoValue;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@AutoValue
public abstract class Request {
  public abstract Map<String, String> queryParams();

  public static Builder builder() {
    return new Builder();
  }

  public final static class Builder {
    private static final String DEFAULT_HAVDALAH = "50";
    private static final String DEFAULT_CANDLE_LIGHTING = "18";

    private Map<String, String> queryParams;

    public Builder() {
      queryParams = new HashMap<>();
      queryParams.put(ParamKeys.OUTPUT_FORMAT.getKey(), OutputTypes.JSON.getType());
      queryParams.put(ParamKeys.INCLUDE_TURAH_HAFTARAH.getKey(), FlagStates.OFF.getState());
      queryParams.put(ParamKeys.ASHKENAZIS_TRANSLITERATIONS.getKey(), FlagStates.OFF.getState());
      queryParams.put(ParamKeys.GEO_TYPE.getKey(), GeoTypes.GEO_NAME.getType());
      queryParams.put(ParamKeys.HAVDALAH.getKey(), DEFAULT_HAVDALAH);
      queryParams.put(ParamKeys.CANDLE_LIGHTING.getKey(), DEFAULT_CANDLE_LIGHTING);
    }

    public Request build() {
      if (queryParams.containsKey(ParamKeys.GEO_ID.getKey())) {
        return new AutoValue_Request(queryParams);
      }
      throw new IllegalStateException("geo id is mandatory for this request");
    }

    public Builder withMinutesAfterSundown(final int minutes) throws IllegalArgumentException {
      if (minutes <= 0) {
        throw new IllegalArgumentException("minutes after sundown should be a positive integer");
      }
      queryParams.put(ParamKeys.HAVDALAH.getKey(), String.valueOf(minutes));
      return this;
    }

    public Builder withMinutesBeforeSunset(final int minutes) throws IllegalArgumentException {
      if (minutes < 0) {
        throw new IllegalArgumentException(
          "minutes before sunset should be a non negative integer");
      }
      queryParams.put(ParamKeys.CANDLE_LIGHTING.getKey(), String.valueOf(minutes));
      return this;
    }

    public Builder forGeoId(final int geoId) throws IllegalArgumentException {
      if (geoId <= 0) {
        throw new IllegalArgumentException("geo id should be a positive integer");
      }
      queryParams.put(ParamKeys.GEO_ID.getKey(), String.valueOf(geoId));
      return this;
    }

    public Builder forDate(final LocalDate dateTime) {
      requireNonNull(dateTime, "Null dateTime");
      final String year = String.valueOf(dateTime.getYear());
      final String month = String.format("0%s", String.valueOf(dateTime.getMonthValue()));
      final String day = String.format("0%s", String.valueOf(dateTime.getDayOfMonth()));

      queryParams.put(ParamKeys.GREGORIAN_YEAR.getKey(), year);
      queryParams.put(ParamKeys.GREGORIAN_MONTH.getKey(), month.substring(month.length() - 2));
      queryParams.put(ParamKeys.GREGORIAN_DAY.getKey(), day.substring(month.length() - 2));
      return this;
    }
  }
}
