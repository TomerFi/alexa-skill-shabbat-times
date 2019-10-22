package info.tomfi.alexa.skills.shabbattimes.tools;

import static info.tomfi.alexa.skills.shabbattimes.tools.ApiTools.getShabbatCandlesItem;
import static info.tomfi.alexa.skills.shabbattimes.tools.ApiTools.getShabbatHavdalahItem;

import static lombok.AccessLevel.PRIVATE;

import info.tomfi.alexa.skills.shabbattimes.api.response.items.ResponseItem;
import info.tomfi.alexa.skills.shabbattimes.exception.NoItemFoundForDateException;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;

import lombok.NoArgsConstructor;
import lombok.val;

/**
 * Utility class for working with date and time objects.
 *
 * @author Tomer Figenblat {@literal <tomer.figenblat@gmail.com>}
 */
@NoArgsConstructor(access = PRIVATE)
public final class DateTimeUtils
{
    /**
     * Get the date of the Friday occuring in the same week of the requested date.
     *
     * @param requestDate the date to convert to a Friday date.
     * @return Friday's date.
     */
    public static LocalDate getShabbatStartLocalDate(final LocalDate requestDate)
    {
        val requestDow = requestDate.getDayOfWeek();
        val daysToAdd =
            requestDow == DayOfWeek.FRIDAY ? 0 :
            requestDow == DayOfWeek.SATURDAY ? -1 :
            DayOfWeek.FRIDAY.minus(requestDow.getValue()).getValue();
        return requestDate.plusDays(daysToAdd);
    }

    /**
     * Get the Shabbat start date time object by retrieving the candles item for a specific date
     * from the response items list and parsing it.
     *
     * @param items a list of
     *     {@link info.tomfi.alexa.skills.shabbattimes.api.response.items.ResponseItem} to retrieve
     *     the item from.
     * @param date the specific date to look for a candles item for.
     * @return a ZonedDateTime object represnting the start date time of the Shabbat.
     * @throws NoItemFoundForDateException when no corresponding candles item was found.
     */
    public static ZonedDateTime getStartDateTime(
        final List<ResponseItem> items, final LocalDate date
    ) throws NoItemFoundForDateException
    {
        val shabbatStartItem = getShabbatCandlesItem(items, date);
        if (!shabbatStartItem.isPresent())
        {
            throw new NoItemFoundForDateException("no candles item found for requested date");
        }
        return ZonedDateTime.parse(shabbatStartItem.get().getDate());
    }

    /**
     * Get the Shabbat end date time object by retrieving the havdalah item for a specific date
     * from the response items list and parsing it.
     *
     * @param items a list of
     *     {@link info.tomfi.alexa.skills.shabbattimes.api.response.items.ResponseItem} to retrieve
     *     the item from.
     * @param date the specific date to look for a havdalah item for.
     * @return a ZonedDateTime object represnting the end date time of the Shabbat.
     * @throws NoItemFoundForDateException when no corresponding candles item was found.
     */
    public static ZonedDateTime getEndDateTime(final List<ResponseItem> items, final LocalDate date)
        throws NoItemFoundForDateException
    {
        val shabbatEndItem = getShabbatHavdalahItem(items, date);
        if (!shabbatEndItem.isPresent())
        {
            throw new NoItemFoundForDateException("no havdalah item found for requested date");
        }
        return ZonedDateTime.parse(shabbatEndItem.get().getDate());
    }
}
