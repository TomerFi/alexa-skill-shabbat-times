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
package info.tomfi.shabbattimes.skill.tools;

import static info.tomfi.shabbattimes.skill.tools.ApiTools.getShabbatCandlesItem;
import static info.tomfi.shabbattimes.skill.tools.ApiTools.getShabbatHavdalahItem;

import info.tomfi.hebcal.shabbat.response.ResponseItem;
import info.tomfi.shabbattimes.skill.exception.NoItemFoundForDateException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Utility class for working with date and time objects.
 *
 * @author Tomer Figenblat {@literal <tomer.figenblat@gmail.com>}
 */
public final class DateTimeUtils {
  private DateTimeUtils() {
    //
  }

  /**
   * Get the date of the Friday occuring in the same week of the requested date.
   *
   * @param requestDate the date to convert to a Friday date.
   * @return Friday's date.
   */
  public static LocalDate getShabbatStartLocalDate(final LocalDate requestDate) {
    final DayOfWeek requestDow = requestDate.getDayOfWeek();
    final int daysToAdd =
        requestDow == DayOfWeek.FRIDAY
            ? 0
            : requestDow == DayOfWeek.SATURDAY
                ? -1
                : DayOfWeek.FRIDAY.minus(requestDow.getValue()).getValue();
    return requestDate.plusDays(daysToAdd);
  }

  /**
   * Get the Shabbat start date time object by retrieving the candles item for a specific date from
   * the response items list and parsing it.
   *
   * @param items a list of {@link
   *     info.tomfi.hebcal.api.response.items.ResponseItem} to retrieve the item
   *     from.
   * @param date the specific date to look for a candles item for.
   * @return a ZonedDateTime object represnting the start date time of the Shabbat.
   * @throws NoItemFoundForDateException when no corresponding candles item was found.
   */
  public static ZonedDateTime getStartDateTime(final List<ResponseItem> items, final LocalDate date)
      throws NoItemFoundForDateException {
    final Optional<ResponseItem> shabbatStartItem = getShabbatCandlesItem(items, date);
    if (!shabbatStartItem.isPresent()) {
      throw new NoItemFoundForDateException("no candles item found for requested date");
    }
    return ZonedDateTime.parse(shabbatStartItem.get().date());
  }

  /**
   * Get the Shabbat end date time object by retrieving the havdalah item for a specific date from
   * the response items list and parsing it.
   *
   * @param items a list of {@link
   *     info.tomfi.hebcal.api.response.items.ResponseItem} to retrieve the item
   *     from.
   * @param date the specific date to look for a havdalah item for.
   * @return a ZonedDateTime object represnting the end date time of the Shabbat.
   * @throws NoItemFoundForDateException when no corresponding candles item was found.
   */
  public static ZonedDateTime getEndDateTime(final List<ResponseItem> items, final LocalDate date)
      throws NoItemFoundForDateException {
    final Optional<ResponseItem> shabbatEndItem = getShabbatHavdalahItem(items, date);
    if (!shabbatEndItem.isPresent()) {
      throw new NoItemFoundForDateException("no havdalah item found for requested date");
    }
    return ZonedDateTime.parse(shabbatEndItem.get().date());
  }
}
