package info.tomfi.alexa.skills.shabbattimes.enums;

import java.util.Arrays;
import java.util.List;

public enum CountryInfo
{
    ISRAEL("IL", "Israel", BundleKeys.NOT_FOUND_IN_ISRAEL, "israel"),
    UNITED_STATES("US", "the United States", BundleKeys.NOT_FOUND_IN_US, "united states"),
    UNITED_KINGDOM("GB", "the United Kingdom", BundleKeys.NOT_FOUND_IN_UK, "united kingdom", "great britain", "britain", "england");

    private final String abbreviation;
    private final String name;
    private final BundleKeys bundleKey;
    private final List<String> utterances;

    CountryInfo(final String setAbbreviation, final String setName, final BundleKeys setBundleKey, final String... setUtterances)
    {
        abbreviation = setAbbreviation;
        name = setName;
        bundleKey = setBundleKey;
        utterances = Arrays.asList(setUtterances);
    }

    public String getAbbreviation()
    {
        return abbreviation;
    }

    public String getName()
    {
        return name;
    }

    public BundleKeys getBundleKey()
    {
        return bundleKey;
    }

    public List<String> getUtterances()
    {
        return utterances;
    }
}
