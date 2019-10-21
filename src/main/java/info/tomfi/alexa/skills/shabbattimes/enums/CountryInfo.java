package info.tomfi.alexa.skills.shabbattimes.enums;

import java.util.Arrays;
import java.util.List;

import lombok.Getter;

/**
 * Enum helper for creating {@link info.tomfi.alexa.skills.shabbattimes.country.Country} objects.
 *
 * @author Tomer Figenblat {@literal <tomer.figenblat@gmail.com>}
 */
public enum CountryInfo
{
    ISRAEL("IL", "Israel", BundleKeys.NOT_FOUND_IN_ISRAEL, "israel"),
    UNITED_STATES("US", "the United States", BundleKeys.NOT_FOUND_IN_US, "united states"),
    UNITED_KINGDOM("GB", "the United Kingdom", BundleKeys.NOT_FOUND_IN_UK, "united kingdom", "great britain", "britain", "england");

    @Getter private final String abbreviation;
    @Getter private final String name;
    @Getter private final BundleKeys bundleKey;
    @Getter private final List<String> utterances;

    CountryInfo(final String setAbbreviation, final String setName, final BundleKeys setBundleKey, final String... setUtterances)
    {
        abbreviation = setAbbreviation;
        name = setName;
        bundleKey = setBundleKey;
        utterances = Arrays.asList(setUtterances);
    }
}
