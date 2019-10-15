package info.tomfi.alexa.skills.shabbattimes.tools;

import static java.time.format.DateTimeFormatter.ISO_OFFSET_DATE_TIME;
import static java.util.stream.Collectors.toList;

import static info.tomfi.alexa.skills.shabbattimes.api.enums.ItemCategories.CANDLES;
import static info.tomfi.alexa.skills.shabbattimes.api.enums.ItemCategories.HOLIDAY;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import info.tomfi.alexa.skills.shabbattimes.api.response.APIResponse;
import info.tomfi.alexa.skills.shabbattimes.api.response.items.ResponseItem;

public final class APITools
{
    private APITools()
    {
    }

    public static List<ResponseItem> getCandlesAndHavdalahItems(final APIResponse response)
    {
        return response.getItems().stream()
            .filter(item -> !item.getCategory().equals(HOLIDAY.value))
            .sorted(
                (prevItem, currentItem) ->
                ZonedDateTime.parse(prevItem.getDate(), ISO_OFFSET_DATE_TIME).compareTo(
                    ZonedDateTime.parse(currentItem.getDate(), ISO_OFFSET_DATE_TIME)
                )
            ).collect(toList());
    }

    public static Optional<ResponseItem> getShabbatCandlesItem(final List<ResponseItem> items, final LocalDate shabbatDate)
    {
        return items.stream()
            .filter(item -> item.getCategory().equals(CANDLES.value))
            .filter(item -> ZonedDateTime.parse(item.getDate(), ISO_OFFSET_DATE_TIME).toLocalDate().equals(shabbatDate))
            .findFirst();
    }
}
