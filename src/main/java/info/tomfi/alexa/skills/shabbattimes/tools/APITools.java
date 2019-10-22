/**
 * Copyright 2019 Tomer Figenblat
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package info.tomfi.alexa.skills.shabbattimes.tools;

import static info.tomfi.alexa.skills.shabbattimes.api.enums.ItemCategories.CANDLES;
import static info.tomfi.alexa.skills.shabbattimes.api.enums.ItemCategories.HAVDALAH;
import static java.time.format.DateTimeFormatter.ISO_OFFSET_DATE_TIME;
import static java.util.stream.Collectors.toList;
import static lombok.AccessLevel.PRIVATE;

import info.tomfi.alexa.skills.shabbattimes.api.response.ApiResponse;
import info.tomfi.alexa.skills.shabbattimes.api.response.items.ResponseItem;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import lombok.NoArgsConstructor;

/**
 * Utility class hosting static helpers for using the api response items.
 *
 * @author Tomer Figenblat {@literal <tomer.figenblat@gmail.com>}
 */
@NoArgsConstructor(access = PRIVATE)
public final class ApiTools
{
    /**
     * A static tool for reducing a
     * {@link info.tomfi.alexa.skills.shabbattimes.api.response.items.ResponseItem} list to its
     * {@value info.tomfi.alexa.skills.shabbattimes.api.enums.ItemCategories#HAVDALAH} and
     * {@value info.tomfi.alexa.skills.shabbattimes.api.enums.ItemCategories#CANDLES} items only.
     *
     * @param response the original
     *     {@link info.tomfi.alexa.skills.shabbattimes.api.response.ApiResponse} object.
     * @return a sorted by date list of
     *     {@link info.tomfi.alexa.skills.shabbattimes.api.response.items.ResponseItem} objects.
     */
    public static List<ResponseItem> getCandlesAndHavdalahItems(final ApiResponse response)
    {
        return response.getItems().stream()
            .filter(
                item ->
                Arrays.asList(CANDLES.getValue(), HAVDALAH.getValue()).contains(item.getCategory())
            )
            .sorted(
                (prevItem, currentItem) ->
                ZonedDateTime.parse(prevItem.getDate(), ISO_OFFSET_DATE_TIME).compareTo(
                    ZonedDateTime.parse(currentItem.getDate(), ISO_OFFSET_DATE_TIME)
                )
            ).collect(toList());
    }

    /**
     * A static tool for retrieving the
     * {@value info.tomfi.alexa.skills.shabbattimes.api.enums.ItemCategories#CANDLES} item for a
     * specific date.
     *
     * @param items a list of
     *     {@link info.tomfi.alexa.skills.shabbattimes.api.response.items.ResponseItem}.
     * @param shabbatDate the date object for looking up the
     *     {@value info.tomfi.alexa.skills.shabbattimes.api.enums.ItemCategories#CANDLES} item.
     * @return an Optional
     *     {@link info.tomfi.alexa.skills.shabbattimes.api.response.items.ResponseItem}
     *     corresponding to the requested date.
     */
    public static Optional<ResponseItem> getShabbatCandlesItem(
        final List<ResponseItem> items, final LocalDate shabbatDate
    )
    {
        return items.stream()
            .filter(item -> item.getCategory().equals(CANDLES.getValue()))
            .filter(
                item ->
                ZonedDateTime.parse(item.getDate(), ISO_OFFSET_DATE_TIME)
                    .toLocalDate().equals(shabbatDate)
            )
            .findFirst();
    }

    /**
     * A static tool for retrieving the
     * {@value info.tomfi.alexa.skills.shabbattimes.api.enums.ItemCategories#HAVDALAH} item for a
     * specific date.
     *
     * @param items a list of
     *     {@link info.tomfi.alexa.skills.shabbattimes.api.response.items.ResponseItem}.
     * @param shabbatDate the date object for looking up the
     *     {@value info.tomfi.alexa.skills.shabbattimes.api.enums.ItemCategories#HAVDALAH} item.
     * @return an Optional
     *     {@link info.tomfi.alexa.skills.shabbattimes.api.response.items.ResponseItem}
     *     corresponding to the requested date.
     */
    public static Optional<ResponseItem> getShabbatHavdalahItem(
        final List<ResponseItem> items, final LocalDate shabbatDate
    )
    {
        return items.stream()
            .filter(item -> item.getCategory().equals(HAVDALAH.getValue()))
            .filter(
                item ->
                ZonedDateTime.parse(item.getDate(), ISO_OFFSET_DATE_TIME)
                    .toLocalDate().equals(shabbatDate)
            )
            .findFirst();
    }
}
