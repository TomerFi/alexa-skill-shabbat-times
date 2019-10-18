package info.tomfi.alexa.skills.shabbattimes.tools;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZonedDateTime;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DateTimeUtils
{
    public static LocalDate getShabbatStartLocalDate(final LocalDate requestDate)
    {
        final DayOfWeek requestDow = requestDate.getDayOfWeek();
        final int daysToAdd =
            requestDow == DayOfWeek.FRIDAY ? 0 :
            requestDow == DayOfWeek.SATURDAY ? -1 :
            DayOfWeek.FRIDAY.minus(requestDow.getValue()).getValue();
        return requestDate.plusDays(daysToAdd);
    }

    public static boolean isShabbatNow(final ZonedDateTime start, final ZonedDateTime current, final ZonedDateTime end)
    {
        return current.equals(start) || current.equals(end) || (current.isAfter(start) && current.isBefore(end));
    }

    public static boolean isShabbatStartsTommorow(final ZonedDateTime start, final ZonedDateTime current)
    {
        return current.getDayOfWeek().compareTo(start.getDayOfWeek().minus(1)) == 0;
    }

    public static boolean isShabbatStartsToday(final ZonedDateTime start, final ZonedDateTime current)
    {
        return current.getDayOfWeek().compareTo(start.getDayOfWeek()) == 0;
    }

    public static boolean isShabbatEndsToday(final ZonedDateTime current, final ZonedDateTime end)
    {
        return current.getDayOfWeek().compareTo(end.getDayOfWeek()) == 0;
    }
}
