package info.tomfi.alexa.skills.shabbattimes.tools;

import static info.tomfi.alexa.skills.shabbattimes.tools.DateTimeUtils.getShabbatStartLocalDate;
import static java.time.format.DateTimeFormatter.ISO_OFFSET_DATE_TIME;
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
}
