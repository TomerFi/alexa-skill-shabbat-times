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
import static info.tomfi.alexa.skills.shabbattimes.api.enums.ItemCategories.HOLIDAY;
import static info.tomfi.alexa.skills.shabbattimes.assertions.Assertions.assertThat;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;
import static java.time.format.DateTimeFormatter.ISO_OFFSET_DATE_TIME;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import info.tomfi.alexa.skills.shabbattimes.api.response.ApiResponse;
import info.tomfi.alexa.skills.shabbattimes.api.response.items.ResponseItem;

import lombok.val;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public final class ApiToolsTest
{
    @Mock private ResponseItem holidayItem;
    @Mock private ResponseItem candlesHolidayItem;
    @Mock private ResponseItem havdalaHolidayItem;
    @Mock private ResponseItem candlesShabbatItem;
    @Mock private ResponseItem havdalaShabbatItem;

    @BeforeEach
    public void initialize()
    {
        when(holidayItem.getCategory()).thenReturn(HOLIDAY.getValue());
        when(holidayItem.getDate()).thenReturn("2019-10-13");

        when(candlesHolidayItem.getCategory()).thenReturn(CANDLES.getValue());
        when(candlesHolidayItem.getDate()).thenReturn("2019-10-13T17:53:00+03:00");

        when(havdalaHolidayItem.getCategory()).thenReturn(HAVDALAH.getValue());
        when(havdalaHolidayItem.getDate()).thenReturn("2019-10-14T19:00:00+03:00");

        when(candlesShabbatItem.getCategory()).thenReturn(CANDLES.getValue());
        when(candlesShabbatItem.getDate()).thenReturn("2019-10-18T17:47:00+03:00");

        when(havdalaShabbatItem.getCategory()).thenReturn(HAVDALAH.getValue());
        when(havdalaShabbatItem.getDate()).thenReturn("2019-10-19T18:54:00+03:00");
    }

    @Test
    @DisplayName("test the retrieval of the correct shabbat candles item by local date")
    public void getShabbatCandlesItem_listContainingCorrectItem_getCorrectItem()
    {
        val listOfItems = Arrays.asList(candlesHolidayItem, havdalaHolidayItem, candlesShabbatItem, havdalaShabbatItem);
        val shabbatStartDate = LocalDate.parse("2019-10-18", ISO_LOCAL_DATE);
        assertThat(ApiTools.getShabbatCandlesItem(listOfItems, shabbatStartDate).get()).isEqualTo(candlesShabbatItem);
    }

    @Test
    @DisplayName("test the empty optional value when trying to retrieve a non-existing candles item by local date")
    public void getShabbatCandlesItem_listNotContainingCorrectItem_optionalEmpty()
    {
        val listOfItems = Arrays.asList(candlesHolidayItem, havdalaHolidayItem);
        val shabbatStartDate = LocalDate.parse("2019-10-18", ISO_LOCAL_DATE);
        assertThat(ApiTools.getShabbatCandlesItem(listOfItems, shabbatStartDate).isPresent()).isFalse();
    }

    @Test
    @DisplayName("test the retrieval of the correct shabbat havdalah item by local date")
    public void getShabbatHavdalaItem_listContainingCorrectItem_getCorrectItem()
    {
        val listOfItems = Arrays.asList(candlesHolidayItem, havdalaHolidayItem, candlesShabbatItem, havdalaShabbatItem);
        val shabbatStartDate = LocalDate.parse("2019-10-19", ISO_LOCAL_DATE);
        assertThat(ApiTools.getShabbatHavdalahItem(listOfItems, shabbatStartDate).get()).isEqualTo(havdalaShabbatItem);
    }

    @Test
    @DisplayName("test the empty optional value when trying to retrieve a non-existing havdalah item by local date")
    public void getShabbatHavdalaItem_listNotContainingCorrectItem_optionalEmpty()
    {
        val listOfItems = Arrays.asList(candlesHolidayItem, havdalaHolidayItem);
        val shabbatStartDate = LocalDate.parse("2019-10-19", ISO_LOCAL_DATE);
        assertThat(ApiTools.getShabbatHavdalahItem(listOfItems, shabbatStartDate).isPresent()).isFalse();
    }

    @Test
    @DisplayName("test the reducing and sorting of the response items list")
    public void getCandlesAndHavdalahItems_unsortedFullList_sortedMinmizedList()
    {
        val response = mock(ApiResponse.class);
        when(response.getItems()).thenReturn(Arrays.asList(havdalaShabbatItem, candlesShabbatItem, havdalaHolidayItem, candlesHolidayItem, holidayItem));

        assertThat(ApiTools.getCandlesAndHavdalahItems(response))
            .containsExactly(candlesHolidayItem, havdalaHolidayItem, candlesShabbatItem, havdalaShabbatItem)
            .isSortedAccordingTo(
                (prevItem, currentItem) ->
                ZonedDateTime.parse(prevItem.getDate(), ISO_OFFSET_DATE_TIME).compareTo(
                    ZonedDateTime.parse(currentItem.getDate(), ISO_OFFSET_DATE_TIME)
                )
            );
    }
}
