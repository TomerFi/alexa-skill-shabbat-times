package info.tomfi.alexa.skills.shabbattimes.tools;

import static info.tomfi.alexa.skills.shabbattimes.enums.Attributes.L10N_BUNDLE;
import static info.tomfi.alexa.skills.shabbattimes.enums.BundleKeys.DEFAULT_OK;
import static info.tomfi.alexa.skills.shabbattimes.tools.LocalizationUtils.getFromBundle;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.ResourceBundle;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import lombok.val;

public final class LocalizationUtilsTest
{
    @Test
    @DisplayName("test the retrieval of localized strings from bundle resource properties file")
    public void getFromBundle_testPropertiesFile_getCorrectValue()
    {
        val attributes = new HashMap<String, Object>();
        attributes.put(L10N_BUNDLE.getName(), ResourceBundle.getBundle("locales/TestResponses"));
        assertThat(getFromBundle(attributes, DEFAULT_OK)).isEqualTo("testValue");
    }
}
