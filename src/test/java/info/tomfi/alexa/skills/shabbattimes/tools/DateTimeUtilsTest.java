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
import static info.tomfi.alexa.skills.shabbattimes.assertions.Assertions.assertThat;
import static info.tomfi.alexa.skills.shabbattimes.assertions.Assertions.assertThatExceptionOfType;
import static info.tomfi.alexa.skills.shabbattimes.tools.DateTimeUtils.getShabbatStartLocalDate;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;
import static java.time.format.DateTimeFormatter.ISO_OFFSET_DATE_TIME;

import static org.mockito.Mockito.when;

import info.tomfi.alexa.skills.shabbattimes.api.response.items.ResponseItem;
import info.tomfi.alexa.skills.shabbattimes.exception.NoItemFoundForDateException;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Arrays;

import lombok.val;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public final class DateTimeUtilsTest
{
    @Mock private ResponseItem candlesHolidayItem;
    @Mock private ResponseItem havdalaHolidayItem;
    @Mock private ResponseItem candlesShabbatItem;
    @Mock private ResponseItem havdalaShabbatItem;

    private static final ZonedDateTime SHABBAT_START = ZonedDateTime.parse("2019-10-11T18:00:00+03:00", ISO_OFFSET_DATE_TIME);
    private static final ZonedDateTime SHABBAT_END = ZonedDateTime.parse("2019-10-12T19:00:00+03:00", ISO_OFFSET_DATE_TIME);

    @BeforeEach
    public void initialize()
    {
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
    @DisplayName("test the retrieval of this week friday when current is sunday")
    public void getShabbatStartLocalDate_currentIsSunday_returnsThisWeekFriday()
    {
        val sundayDate = SHABBAT_START.minusDays(5).toLocalDate();
        assertThat(getShabbatStartLocalDate(sundayDate)).isEqualTo(SHABBAT_START.toLocalDate());
    }

    @Test
    @DisplayName("test the retrieval of this week friday when current is monday")
    public void getShabbatStartLocalDate_currentIsMonday_returnsThisWeekFriday()
    {
        val mondayDate = SHABBAT_START.minusDays(4).toLocalDate();
        assertThat(getShabbatStartLocalDate(mondayDate)).isEqualTo(SHABBAT_START.toLocalDate());
    }

    @Test
    @DisplayName("test the retrieval of this week friday when current is tuesday")
    public void getShabbatStartLocalDate_currentIsTuesday_returnsThisWeekFriday()
    {
        val tuesdayDate = SHABBAT_START.minusDays(3).toLocalDate();
        assertThat(getShabbatStartLocalDate(tuesdayDate)).isEqualTo(SHABBAT_START.toLocalDate());
    }

    @Test
    @DisplayName("test the retrieval of this week friday when current is wednesday")
    public void getShabbatStartLocalDate_currentIsWednesday_returnsThisWeekFriday()
    {
        val wednesdayDate = SHABBAT_START.minusDays(2).toLocalDate();
        assertThat(getShabbatStartLocalDate(wednesdayDate)).isEqualTo(SHABBAT_START.toLocalDate());
    }

    @Test
    @DisplayName("test the retrieval of this week friday when current is thursday")
    public void getShabbatStartLocalDate_currentIsThursday_returnsThisWeekFriday()
    {
        val thursdayDate = SHABBAT_START.minusDays(1).toLocalDate();
        assertThat(getShabbatStartLocalDate(thursdayDate)).isEqualTo(SHABBAT_START.toLocalDate());
    }

    @Test
    @DisplayName("test the retrieval of this week friday when current is friday")
    public void getShabbatStartLocalDate_currentIsFriday_returnsThisWeekFriday()
    {
        val fridayDate = SHABBAT_START.toLocalDate();
        assertThat(getShabbatStartLocalDate(fridayDate)).isEqualTo(SHABBAT_START.toLocalDate());
    }

    @Test
    @DisplayName("test the retrieval of this week friday when current is saturday")
    public void getShabbatStartLocalDate_currentIsSaturday_returnsThisWeekFriday()
    {
        val saturdayDate = SHABBAT_END.toLocalDate();
        assertThat(getShabbatStartLocalDate(saturdayDate)).isEqualTo(SHABBAT_START.toLocalDate());
    }

    @Test
    @DisplayName("test the retrieval of the correct shabbat candles item by local date")
    public void getStartDateTime_listContainingCorrectItem_getCorrectItem()
    {
        val listOfItems = Arrays.asList(candlesHolidayItem, havdalaHolidayItem, candlesShabbatItem, havdalaShabbatItem);
        val shabbatStartDate = LocalDate.parse("2019-10-18", ISO_LOCAL_DATE);
        assertThat(DateTimeUtils.getStartDateTime(listOfItems, shabbatStartDate).toString()).isEqualTo("2019-10-18T17:47+03:00");
    }

    @Test
    @DisplayName("test exception throwing when the retrieval of the correct shabbat candles item by local date fails due to date incompatibility")
    public void getStartDateTime_listNotContainingCorrectItem_throwsException()
    {
        val listOfItems = Arrays.asList(candlesHolidayItem, havdalaHolidayItem);
        val shabbatStartDate = LocalDate.parse("2019-10-18", ISO_LOCAL_DATE);
        assertThatExceptionOfType(NoItemFoundForDateException.class)
            .isThrownBy(() -> DateTimeUtils.getStartDateTime(listOfItems, shabbatStartDate));
    }

    @Test
    @DisplayName("test the retrieval of the correct shabbat havdalah item by local date")
    public void getEndDateTime_listContainingCorrectItem_getCorrectItem()
    {
        val listOfItems = Arrays.asList(candlesHolidayItem, havdalaHolidayItem, candlesShabbatItem, havdalaShabbatItem);
        val shabbatEndDate = LocalDate.parse("2019-10-19", ISO_LOCAL_DATE);
        assertThat(DateTimeUtils.getEndDateTime(listOfItems, shabbatEndDate).toString()).isEqualTo("2019-10-19T18:54+03:00");
    }

    @Test
    @DisplayName("test exception throwing when the retrieval of the correct shabbat havdalah item by local date fails due to date incompatibility")
    public void getEndDateTime_listNotContainingCorrectItem_throwsException()
    {
        val listOfItems = Arrays.asList(candlesHolidayItem, havdalaHolidayItem);
        val shabbatEndDate = LocalDate.parse("2019-10-19", ISO_LOCAL_DATE);
        assertThatExceptionOfType(NoItemFoundForDateException.class)
            .isThrownBy(() -> DateTimeUtils.getEndDateTime(listOfItems, shabbatEndDate));
    }
}
