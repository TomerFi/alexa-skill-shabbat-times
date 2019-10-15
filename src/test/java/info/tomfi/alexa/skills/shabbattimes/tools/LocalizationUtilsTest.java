package info.tomfi.alexa.skills.shabbattimes.tools;

import static org.assertj.core.api.Assertions.assertThat;

import static info.tomfi.alexa.skills.shabbattimes.tools.GlobalEnums.Attributes.L10N_BUNDLE;
import static info.tomfi.alexa.skills.shabbattimes.tools.GlobalEnums.BundleKeys.DEFAULT_OK;
import static info.tomfi.alexa.skills.shabbattimes.tools.LocalizationUtils.getBundleFromAttribures;
import static info.tomfi.alexa.skills.shabbattimes.tools.LocalizationUtils.getFromBundle;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public final class LocalizationUtilsTest
{
    private static ResourceBundle bundle;

    @BeforeAll
    public static void initialize()
    {
        bundle = ResourceBundle.getBundle("TestResponses");
    }

    @Test
    @DisplayName("test the retrieval of the bundle object from a the attributes map")
    public void getBundleFromAttribures_fakeAttributes_getBundle()
    {
        final Map<String, Object> attributes = new HashMap<>();
        attributes.put(L10N_BUNDLE.name, bundle);
        assertThat(getBundleFromAttribures(attributes).hashCode()).isEqualTo(bundle.hashCode());
    }

    @Test
    @DisplayName("test the retrieval of localized strings from bundle resource properties file")
    public void getFromBundle_testPropertiesFile_getCorrectValue()
    {
        assertThat(getFromBundle(bundle, DEFAULT_OK)).isEqualTo("testValue");
    }
}
