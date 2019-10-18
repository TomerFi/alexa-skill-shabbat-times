package info.tomfi.alexa.skills.shabbattimes.tools;

import static java.time.format.DateTimeFormatter.ISO_OFFSET_DATE_TIME;
import static java.time.temporal.ChronoUnit.HALF_DAYS;

import static info.tomfi.alexa.skills.shabbattimes.tools.DateTimeUtils.getShabbatStartLocalDate;
import static info.tomfi.alexa.skills.shabbattimes.tools.DateTimeUtils.isShabbatEndsToday;
import static info.tomfi.alexa.skills.shabbattimes.tools.DateTimeUtils.isShabbatNow;
import static info.tomfi.alexa.skills.shabbattimes.tools.DateTimeUtils.isShabbatStartsToday;
import static info.tomfi.alexa.skills.shabbattimes.tools.DateTimeUtils.isShabbatStartsTommorow;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.ZonedDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import lombok.val;

public final class DateTimeUtilsTest
{
    private static final ZonedDateTime SHABBAT_START = ZonedDateTime.parse("2019-10-11T18:00:00+03:00", ISO_OFFSET_DATE_TIME);
    private static final ZonedDateTime SHABBAT_END = ZonedDateTime.parse("2019-10-12T19:00:00+03:00", ISO_OFFSET_DATE_TIME);

    @Test
    @DisplayName("test the evaluation of a current date that is bettween the shabbat start and end datetime")
    public void isShabbatNow_currentIsBetween_returnTrue()
    {
        val currentDateTime = SHABBAT_START.plus(1, HALF_DAYS);
        assertThat(isShabbatNow(SHABBAT_START, currentDateTime, SHABBAT_END)).isTrue();
    }

    @Test
    @DisplayName("test the evaluation of a current date that is identical to the shabbat start datetime")
    public void isShabbatNow_currentIsShabbatStart_returnTrue()
    {
        assertThat(isShabbatNow(SHABBAT_START, SHABBAT_START, SHABBAT_END)).isTrue();
    }

    @Test
    @DisplayName("test the evaluation of a current date that is identical to the shabbat end datetime")
    public void isShabbatNow_currentIsShabbatEnd_returnTrue()
    {
        assertThat(isShabbatNow(SHABBAT_START, SHABBAT_END, SHABBAT_END)).isTrue();
    }

    @Test
    @DisplayName("test the evaluation of a current date that is before the shabbat start datetime")
    public void isShabbatNow_currentIsBeforeShabbat_returnFalse()
    {
        val currentDateTime = SHABBAT_START.minus(1, HALF_DAYS);
        assertThat(isShabbatNow(SHABBAT_START, currentDateTime, SHABBAT_END)).isFalse();
    }

    @Test
    @DisplayName("test the evaluation of a current date that is after the shabbat end datetime")
    public void isShabbatNow_currentIsAfterShabbat_returnFalse()
    {
        val currentDateTime = SHABBAT_END.plus(1, HALF_DAYS);
        assertThat(isShabbatNow(SHABBAT_START, currentDateTime, SHABBAT_END)).isFalse();
    }

    @Test
    @DisplayName("test the evaluation of a current date as one day before the shabbat start datetime")
    public void isShabbatTommorow_currentIsOneDayBefore_returnTrue()
    {
        val currentDateTime = SHABBAT_START.minusDays(1);
        assertThat(isShabbatStartsTommorow(SHABBAT_START, currentDateTime)).isTrue();
    }

    @Test
    @DisplayName("test the evaluation of a current date as not one day before the shabbat start datetime")
    public void isShabbatTommorow_currentIsTwoDaysBefore_returnFalse()
    {
        val currentDateTime = SHABBAT_START.minusDays(2);
        assertThat(isShabbatStartsTommorow(SHABBAT_START, currentDateTime)).isFalse();
    }

    @Test
    @DisplayName("test the evaluation of a current date day of week as the same as the shabbat start datetime")
    public void isShabbatStartsToday_currentDowIsShaabatStart_returnTrue()
    {
        val currentDateTime = SHABBAT_START.minusHours(1);
        assertThat(isShabbatStartsToday(SHABBAT_START, currentDateTime)).isTrue();
    }

    @Test
    @DisplayName("test the evaluation of a current date day of week as not the same as the shabbat start datetime")
    public void isShabbatStartsToday_currentDowIsShaabatEnd_returnFalse()
    {
        val currentDateTime = SHABBAT_END.plusHours(1);
        assertThat(isShabbatStartsToday(SHABBAT_START, currentDateTime)).isFalse();
    }

    @Test
    @DisplayName("test the evaluation of a current date day of week as the not same as the shabbat end datetime")
    public void isShabbatEndsToday_currentDowIsShaabatStart_returnFalse()
    {
        val currentDateTime = SHABBAT_START.minusHours(1);
        assertThat(isShabbatEndsToday(currentDateTime, SHABBAT_END)).isFalse();
    }

    @Test
    @DisplayName("test the evaluation of a current date day of week as the same as the shabbat end datetime")
    public void isShabbatEndsToday_currentDowIsShaabatEnd_returnTrue()
    {
        val currentDateTime = SHABBAT_END.plusHours(1);
        assertThat(isShabbatEndsToday(currentDateTime, SHABBAT_END)).isTrue();
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
        assertThat(getShabbatStartLocalDate(SHABBAT_START.toLocalDate())).isEqualTo(SHABBAT_START.toLocalDate());
    }

    @Test
    @DisplayName("test the retrieval of this week friday when current is saturday")
    public void getShabbatStartLocalDate_currentIsSaturday_returnsThisWeekFriday()
    {
        assertThat(getShabbatStartLocalDate(SHABBAT_END.toLocalDate())).isEqualTo(SHABBAT_START.toLocalDate());
    }
}
