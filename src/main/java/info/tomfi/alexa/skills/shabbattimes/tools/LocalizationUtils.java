package info.tomfi.alexa.skills.shabbattimes.tools;

import static info.tomfi.alexa.skills.shabbattimes.enums.Attributes.L10N_BUNDLE;

import static lombok.AccessLevel.PRIVATE;

import java.util.Map;
import java.util.ResourceBundle;

import info.tomfi.alexa.skills.shabbattimes.enums.BundleKeys;

import lombok.NoArgsConstructor;
import lombok.val;

@NoArgsConstructor(access = PRIVATE)
public final class LocalizationUtils
{
    public static String getFromBundle(final Map<String, Object> attributes, final BundleKeys key)
    {
        val bundle = (ResourceBundle) attributes.get(L10N_BUNDLE.getName());
        return bundle.getString(key.toString());
    }
}
