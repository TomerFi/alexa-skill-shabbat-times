package info.tomfi.alexa.skills.shabbattimes.tools;

import static info.tomfi.alexa.skills.shabbattimes.api.enums.ItemCategories.CANDLES;
import static info.tomfi.alexa.skills.shabbattimes.api.enums.ItemCategories.HAVDALAH;
import static info.tomfi.alexa.skills.shabbattimes.api.enums.ItemCategories.HOLIDAY;

import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import info.tomfi.alexa.skills.shabbattimes.api.response.APIResponse;
import info.tomfi.alexa.skills.shabbattimes.api.response.items.ResponseItem;

public final class APIToolsTest
{
    private static ResponseItem holidayItem;
    private static ResponseItem candlesHolidayItem;
    private static ResponseItem havdalaHolidayItem;
    private static ResponseItem candlesShabbatItem;
    private static ResponseItem havdalaShabbatItem;

    @BeforeAll
    public static void initialize()
    {
        holidayItem = mock(ResponseItem.class);
        when(holidayItem.getCategory()).thenReturn(HOLIDAY.value);
        when(holidayItem.getDate()).thenReturn("2019-10-13");

        candlesHolidayItem = mock(ResponseItem.class);
        when(candlesHolidayItem.getCategory()).thenReturn(CANDLES.value);
        when(candlesHolidayItem.getDate()).thenReturn("2019-10-13T17:53:00+03:00");

        havdalaHolidayItem = mock(ResponseItem.class);
        when(havdalaHolidayItem.getCategory()).thenReturn(HAVDALAH.value);
        when(havdalaHolidayItem.getDate()).thenReturn("2019-10-14T19:00:00+03:00");

        candlesShabbatItem = mock(ResponseItem.class);
        when(candlesShabbatItem.getCategory()).thenReturn(CANDLES.value);
        when(candlesShabbatItem.getDate()).thenReturn("2019-10-18T17:47:00+03:00");

        havdalaShabbatItem = mock(ResponseItem.class);
        when(havdalaShabbatItem.getCategory()).thenReturn(HAVDALAH.value);
        when(havdalaShabbatItem.getDate()).thenReturn("2019-10-19T18:54:00+03:00");
    }

    @Test
    @DisplayName("test the retrieval of the correct shabbat candles item by local date")
    public void getShabbatCandlesItem_listContainingCorrectItem_getCorrectItem()
    {
        final List<ResponseItem> listOfItems = Arrays.asList(candlesHolidayItem, havdalaHolidayItem, candlesShabbatItem, havdalaShabbatItem);
        final LocalDate shabbatStartDate = LocalDate.parse("2019-10-18", DateTimeFormatter.ISO_LOCAL_DATE);
        assertThat(APITools.getShabbatCandlesItem(listOfItems, shabbatStartDate).get()).isEqualTo(candlesShabbatItem);
    }

    @Test
    @DisplayName("test the empty optional value when trying to retrieve a non-existing candles item by local date")
    public void getShabbatCandlesItem_listNotContainingCorrectItem_optionalEmpty()
    {
        final List<ResponseItem> listOfItems = Arrays.asList(candlesHolidayItem, havdalaHolidayItem);
        final LocalDate shabbatStartDate = LocalDate.parse("2019-10-18", DateTimeFormatter.ISO_LOCAL_DATE);
        assertThat(APITools.getShabbatCandlesItem(listOfItems, shabbatStartDate).isPresent()).isFalse();
    }

    @Test
    @DisplayName("test")
    public void getCandlesAndHavdalahItems_unsortedFullList_sortedMinmizedList()
    {
        final APIResponse response = mock(APIResponse.class);
        when(response.getItems()).thenReturn(Arrays.asList(havdalaShabbatItem, candlesShabbatItem, havdalaHolidayItem, candlesHolidayItem, holidayItem));

        assertThat(APITools.getCandlesAndHavdalahItems(response)).containsExactly(candlesHolidayItem, havdalaHolidayItem, candlesShabbatItem, havdalaShabbatItem);
    }
}
