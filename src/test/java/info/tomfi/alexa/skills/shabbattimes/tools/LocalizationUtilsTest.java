package info.tomfi.alexa.skills.shabbattimes.tools;

import static info.tomfi.alexa.skills.shabbattimes.enums.Attributes.L10N_BUNDLE;
import static info.tomfi.alexa.skills.shabbattimes.enums.BundleKeys.DEFAULT_OK;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.DayOfWeek;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public final class LocalizationUtilsTest
{
    private static Map<String, Object> attributes;

    @BeforeAll
    public static void initialize()
    {
        attributes = new HashMap<String, Object>();
        attributes.put(L10N_BUNDLE.getName(), ResourceBundle.getBundle("locales/TestResponses"));
    }

    @Test
    @DisplayName("test the retrieval of localized strings from bundle resource properties file")
    public void getFromBundle_testPropertiesFile_getCorrectValue()
    {
        assertThat(LocalizationUtils.getFromBundle(attributes, DEFAULT_OK)).isEqualTo("testValue");
    }

    @Test
    @DisplayName("test the retrieval of the start-at presentation text on a thursday")
    public void getStartsAtPresentation_dowThursday_validateText()
    {
        assertThat(LocalizationUtils.getStartsAtPresentation(attributes, DayOfWeek.THURSDAY))
            .isEqualTo("test_start_tomorrow");
    }

    @Test
    @DisplayName("test the retrieval of the start-at presentation text on a friday")
    public void getStartsAtPresentation_dowFriday_validateText()
    {
        assertThat(LocalizationUtils.getStartsAtPresentation(attributes, DayOfWeek.FRIDAY))
            .isEqualTo("test_start_today");
    }

    @Test
    @DisplayName("test the retrieval of the start-at presentation text on a saturday")
    public void getStartsAtPresentation_dowSaturday_validateText()
    {
        assertThat(LocalizationUtils.getStartsAtPresentation(attributes, DayOfWeek.SATURDAY))
            .isEqualTo("test_start_yesterday");
    }

    @Test
    @DisplayName("test the retrieval of the start-at presentation text on a sunday")
    public void getStartsAtPresentation_dowSunday_validateText()
    {
        assertThat(LocalizationUtils.getStartsAtPresentation(attributes, DayOfWeek.SUNDAY))
            .isEqualTo("test_start_friday");
    }

    @Test
    @DisplayName("test the retrieval of the end-at presentation text on a friday")
    public void getEndsAtPresentation_dowFriday_validateText()
    {
        assertThat(LocalizationUtils.getEndsAtPresentation(attributes, DayOfWeek.FRIDAY))
            .isEqualTo("test_end_tomorrow");
    }

    @Test
    @DisplayName("test the retrieval of the end-at presentation text on a saturday")
    public void getEndsAtPresentation_dowSaturday_validateText()
    {
        assertThat(LocalizationUtils.getEndsAtPresentation(attributes, DayOfWeek.SATURDAY))
            .isEqualTo("test_end_today");
    }

    @Test
    @DisplayName("test the retrieval of the end-at presentation text on a sunday")
    public void getEndsAtPresentation_dowSunday_validateText()
    {
        assertThat(LocalizationUtils.getEndsAtPresentation(attributes, DayOfWeek.SUNDAY))
            .isEqualTo("test_end_saturday");
    }
}
