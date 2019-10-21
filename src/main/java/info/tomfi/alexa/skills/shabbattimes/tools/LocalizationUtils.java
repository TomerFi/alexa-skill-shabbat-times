package info.tomfi.alexa.skills.shabbattimes.tools;

import static info.tomfi.alexa.skills.shabbattimes.enums.Attributes.L10N_BUNDLE;
import static lombok.AccessLevel.PRIVATE;

import java.time.DayOfWeek;
import java.util.Map;
import java.util.ResourceBundle;

import info.tomfi.alexa.skills.shabbattimes.enums.BundleKeys;
import lombok.NoArgsConstructor;
import lombok.val;

/**
 * Utility class for retrieving text date from the locale resource bundle properties file.
 *
 * @author Tomer Figenblat {@literal <tomer.figenblat@gmail.com>}
 */
@NoArgsConstructor(access = PRIVATE)
public final class LocalizationUtils
{
    /**
     * A static tool for retrieving text from the bundle resource object stored as request attributes.
     * @param attributes attributes storing the bundle resource object with the key of {@value info.tomfi.alexa.skills.shabbattimes.enums.Attributes#L10N_BUNDLE}.
     * @param key a {@link info.tomfi.alexa.skills.shabbattimes.enums.BundleKeys} member corresponding to the locale properties file key.
     * @return the text retrieved as String.
     */
    public static String getFromBundle(final Map<String, Object> attributes, final BundleKeys key)
    {
        val bundle = (ResourceBundle) attributes.get(L10N_BUNDLE.getName());
        return bundle.getString(key.toString());
    }

    /**
     * Get the 'shabbat starts at' presentation text from the resource bundle locale properties file based on the current day of week.
     * @param requestAttributes attributes storing the bundle resource object with the key of {@value info.tomfi.alexa.skills.shabbattimes.enums.Attributes#L10N_BUNDLE}.
     * @param dow the day of week memeber to retrieve the text for.
     * @return the text retrieved as String.
     */
    public static String getStartsAtPresentation(final Map<String, Object> requestAttributes, final DayOfWeek dow)
    {
        return getFromBundle(requestAttributes,
            dow.equals(DayOfWeek.THURSDAY) ? BundleKeys.SHABBAT_START_TOMORROW :
            dow.equals(DayOfWeek.FRIDAY) ? BundleKeys.SHABBAT_START_TODAY :
            dow.equals(DayOfWeek.SATURDAY) ? BundleKeys.SHABBAT_START_YESTERDAY :
            BundleKeys.SHABBAT_START_FRIDAY
        );
    }

    /**
     * Get the 'shabbat ends at' presentation text from the resource bundle locale properties file based on the current day of week.
     * @param requestAttributes attributes storing the bundle resource object with the key of {@value info.tomfi.alexa.skills.shabbattimes.enums.Attributes#L10N_BUNDLE}.
     * @param dow the day of week memeber to retrieve the text for.
     * @return the text retrieved as String.
     */
    public static String getEndsAtPresentation(final Map<String, Object> requestAttributes, final DayOfWeek dow)
    {
        return getFromBundle(requestAttributes,
            dow.equals(DayOfWeek.FRIDAY) ? BundleKeys.SHABBAT_END_TOMORROW :
            dow.equals(DayOfWeek.SATURDAY) ? BundleKeys.SHABBAT_END_TODAY :
            BundleKeys.SHABBAT_END_SATURDAY
        );
    }
}
