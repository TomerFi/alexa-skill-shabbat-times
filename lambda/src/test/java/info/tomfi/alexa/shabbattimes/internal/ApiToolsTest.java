package info.tomfi.alexa.shabbattimes.internal;

import static info.tomfi.hebcal.shabbat.response.ItemCategories.CANDLES;
import static info.tomfi.hebcal.shabbat.response.ItemCategories.HAVDALAH;
import static info.tomfi.hebcal.shabbat.response.ItemCategories.HOLIDAY;
import static java.time.ZonedDateTime.parse;
import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;
import static java.time.format.DateTimeFormatter.ISO_OFFSET_DATE_TIME;
import static org.assertj.core.api.BDDAssertions.assertThat;
import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.when;
import static org.mockito.quality.Strictness.LENIENT;

import info.tomfi.hebcal.shabbat.response.Response;
import info.tomfi.hebcal.shabbat.response.ResponseItem;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;

@MockitoSettings(strictness = LENIENT)
final class ApiToolsTest {
  @Mock private ResponseItem holidayItem;
  @Mock private ResponseItem candlesHolidayItem;
  @Mock private ResponseItem havdalaHolidayItem;
  @Mock private ResponseItem candlesShabbatItem;
  @Mock private ResponseItem havdalaShabbatItem;

  private List<ResponseItem> allItems;
  private List<ResponseItem> holidayOnlyItems;

  private LocalDate fridayDate;
  private LocalDate saturdayDate;

  @BeforeEach
  void initialize() {
    // populate item lists
    allItems = List.of(candlesHolidayItem, havdalaHolidayItem, candlesShabbatItem, havdalaShabbatItem);
    holidayOnlyItems = List.of(candlesHolidayItem, havdalaHolidayItem);
    // create local date objects for invoking the methods
    fridayDate = LocalDate.parse("2019-10-18", ISO_LOCAL_DATE);
    saturdayDate = LocalDate.parse("2019-10-19", ISO_LOCAL_DATE);
    // stub holiday item with type
    when(holidayItem.category()).thenReturn(HOLIDAY.category());
    // stub holiday candles itesm with type, date and time
    when(candlesHolidayItem.category()).thenReturn(CANDLES.category());
    when(candlesHolidayItem.date()).thenReturn("2019-10-13T17:53:00+03:00");
    // stub holiday havdalah itesm with type, date and time
    when(havdalaHolidayItem.category()).thenReturn(HAVDALAH.category());
    when(havdalaHolidayItem.date()).thenReturn("2019-10-14T19:00:00+03:00");
    // stub shabbat candles itesm with type, date and time
    when(candlesShabbatItem.category()).thenReturn(CANDLES.category());
    when(candlesShabbatItem.date()).thenReturn("2019-10-18T17:47:00+03:00");
    // stub shabbat havdalah itesm with type, date and time
    when(havdalaShabbatItem.category()).thenReturn(HAVDALAH.category());
    when(havdalaShabbatItem.date()).thenReturn("2019-10-19T18:54:00+03:00");
  }

  @TestFactory
  Collection<DynamicTest> retrieving_shabbat_candles_and_havdalah_items_from_predefined_lists() {
    return List.of(
      dynamicTest(
        "the nearest shabbat candles item for friday is friday and not holiday sunday",
        () -> assertThat(ApiTools.getShabbatCandlesItem(allItems, fridayDate)).hasValue(candlesShabbatItem)),
      dynamicTest(
        "when no shabbat candles item found, returning optional and not failing",
        () -> assertThat(ApiTools.getShabbatCandlesItem(holidayOnlyItems, fridayDate)).isEmpty()),
      dynamicTest(
        "the nearest shabbat havdalah item for saturday is saturday and not holiday monday",
        () -> assertThat(ApiTools.getShabbatHavdalahItem(allItems, saturdayDate)).hasValue(havdalaShabbatItem)),
      dynamicTest(
        "when no shabbat havdalah item found, returning optional and not failing",
        () -> assertThat(ApiTools.getShabbatHavdalahItem(holidayOnlyItems, saturdayDate)).isEmpty())
      );
  }

  @Test
  void reducing_and_sorting_a_full_items_list_returns_only_sorted_candles_and_havdalah_items(@Mock Response response) {
    // stub the response object with an optional list of all items
    given(response.items()).willReturn(Optional.of(allItems));
    // when invoking the getCandlesAndHavdalahItems tool
    var items = ApiTools.getCandlesAndHavdalahItems(response);
    // then the items returned doesn't include the holiday item, its not a candles nor havdala item
    // and the items in the list is sorted by the date of the item
    then(items)
        .containsExactly(
            candlesHolidayItem, havdalaHolidayItem, candlesShabbatItem, havdalaShabbatItem)
        .isSortedAccordingTo(
            (prevItem, currentItem) ->
                parse(prevItem.date(), ISO_OFFSET_DATE_TIME)
                    .compareTo(parse(currentItem.date(), ISO_OFFSET_DATE_TIME)));
  }
}
