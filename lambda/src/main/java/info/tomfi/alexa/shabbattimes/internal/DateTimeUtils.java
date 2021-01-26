/**
 * Copyright Tomer Figenblat Licensed under the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in
 * writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific
 * language governing permissions and limitations under the License.
 */
package info.tomfi.alexa.shabbattimes.internal;

import static info.tomfi.alexa.shabbattimes.internal.ApiTools.getShabbatCandlesItem;
import static info.tomfi.alexa.shabbattimes.internal.ApiTools.getShabbatHavdalahItem;
import static java.time.DayOfWeek.FRIDAY;
import static java.time.DayOfWeek.SATURDAY;
import static java.time.ZonedDateTime.parse;

import info.tomfi.alexa.shabbattimes.exceptions.NoItemFoundForDateException;
import info.tomfi.hebcal.shabbat.response.ResponseItem;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;

/** Utility class for working with date and time objects. */
public final class DateTimeUtils {
  private DateTimeUtils() {
    //
  }

  /**
   * Get Friday's date for the the same week of the requested date.
   *
   * @param requestDate the date to convert to a Friday date.
   * @return Friday's date.
   */
  public static LocalDate getShabbatStartLocalDate(final LocalDate requestDate) {
    var requestDow = requestDate.getDayOfWeek();
    var daysToAdd =
        requestDow == FRIDAY
            ? 0
            : requestDow == SATURDAY
                ? -1
                : FRIDAY.minus(requestDow.getValue()).getValue();
    return requestDate.plusDays(daysToAdd);
  }

  /**
   * Get the Shabbat start date time object by retrieving the candles item for a specific date from
   * the response items list and parsing it.
   *
   * @param items a list of {@link info.tomfi.hebcal.api.response.items.ResponseItem} to retrieve
   *     the item from.
   * @param date the specific date to look for a candles item for.
   * @return a ZonedDateTime object represnting the start date time of the Shabbat.
   */
  public static ZonedDateTime getStartDateTime(final List<ResponseItem> items, final LocalDate date) {
    return parse(
      getShabbatCandlesItem(items, date).orElseThrow(NoItemFoundForDateException::new).date());
  }

  /**
   * Get the Shabbat end date time object by retrieving the havdalah item for a specific date from
   * the response items list and parsing it.
   *
   * @param items a list of {@link info.tomfi.hebcal.api.response.items.ResponseItem} to retrieve
   *     the item from.
   * @param date the specific date to look for a havdalah item for.
   * @return a ZonedDateTime object represnting the end date time of the Shabbat.
   */
  public static ZonedDateTime getEndDateTime(final List<ResponseItem> items, final LocalDate date)
      throws NoItemFoundForDateException {
    return parse(
      getShabbatHavdalahItem(items, date).orElseThrow(NoItemFoundForDateException::new).date());
  }
}
