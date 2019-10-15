package info.tomfi.alexa.skills.shabbattimes.tools;

import static info.tomfi.alexa.skills.shabbattimes.tools.GlobalEnums.Attributes;
import static info.tomfi.alexa.skills.shabbattimes.tools.GlobalEnums.BundleKeys;

import java.util.Map;
import java.util.ResourceBundle;

public final class LocalizationUtils
{
    private LocalizationUtils()
    {
    }

    public static ResourceBundle getBundleFromAttribures(final Map<String, Object> attributes)
    {
        return (ResourceBundle) attributes.get(Attributes.L10N_BUNDLE.name);
    }

    public static String getFromBundle(final ResourceBundle bundle, final BundleKeys key)
    {
        return bundle.getString(key.toString());
    }
}
