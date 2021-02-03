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

import static info.tomfi.alexa.shabbattimes.BundleKey.SHABBAT_END_SATURDAY;
import static info.tomfi.alexa.shabbattimes.BundleKey.SHABBAT_END_TODAY;
import static info.tomfi.alexa.shabbattimes.BundleKey.SHABBAT_END_TOMORROW;
import static info.tomfi.alexa.shabbattimes.BundleKey.SHABBAT_START_FRIDAY;
import static info.tomfi.alexa.shabbattimes.BundleKey.SHABBAT_START_TODAY;
import static info.tomfi.alexa.shabbattimes.BundleKey.SHABBAT_START_TOMORROW;
import static info.tomfi.alexa.shabbattimes.BundleKey.SHABBAT_START_YESTERDAY;
import static java.time.DayOfWeek.FRIDAY;
import static java.time.DayOfWeek.SATURDAY;
import static java.time.DayOfWeek.THURSDAY;

import com.amazon.ask.model.Slot;
import info.tomfi.alexa.shabbattimes.exceptions.NoCitySlotException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.UnaryOperator;

public final class Tools {
  private Tools() {
    //
  }

  public static UnaryOperator<LocalDate> bumpToFriday =
      d -> {
        var dow = d.getDayOfWeek();
        var add =
            dow.equals(FRIDAY)
                ? 0
                : dow.equals(SATURDAY) ? -1 : FRIDAY.minus(dow.getValue()).getValue();
        return d.plusDays(add);
      };

  public static Function<DayOfWeek, BundleKey> endAtStmt =
      d ->
          d.equals(FRIDAY)
              ? SHABBAT_END_TOMORROW
              : d.equals(SATURDAY) ? SHABBAT_END_TODAY : SHABBAT_END_SATURDAY;

  public static Function<DayOfWeek, BundleKey> strtAtStmt =
      d ->
          d.equals(THURSDAY)
              ? SHABBAT_START_TOMORROW
              : d.equals(FRIDAY)
                  ? SHABBAT_START_TODAY
                  : d.equals(SATURDAY) ? SHABBAT_START_YESTERDAY : SHABBAT_START_FRIDAY;

  public static Function<Map<String, Slot>, Slot> findCitySlot(final List<String> keys) {
    return m -> {
      var k =
          m.keySet().stream()
              .filter(keys::contains)
              .filter(s -> !Objects.toString(m.get(s).getValue(), "").isBlank())
              .findFirst()
              .orElseThrow(NoCitySlotException::new);
      return m.get(k);
    };
  }
}
