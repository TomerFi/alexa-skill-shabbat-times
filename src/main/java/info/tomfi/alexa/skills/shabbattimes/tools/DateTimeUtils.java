package info.tomfi.alexa.skills.shabbattimes.tools;

import java.time.DayOfWeek;
import java.time.LocalDate;

public final class DateTimeUtils
{
    private DateTimeUtils()
    {
    }

    public static LocalDate getShabbatStartLocalDate(final LocalDate requestDate)
    {
        final DayOfWeek requestDow = requestDate.getDayOfWeek();
        final int daysToAdd =
            requestDow == DayOfWeek.FRIDAY ? 0 :
            requestDow == DayOfWeek.SATURDAY ? -1 :
            DayOfWeek.FRIDAY.minus(requestDow.getValue()).getValue();
        return requestDate.plusDays(daysToAdd);
    }
}
