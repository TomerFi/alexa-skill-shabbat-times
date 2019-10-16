package info.tomfi.alexa.skills.shabbattimes.enums;

import java.util.Arrays;
import java.util.List;

public enum CountryInfo
{
    ISRAEL("IL", "Israel", "israel"),
    UNITED_STATES("US", "the United States", "united states"),
    UNITED_KINGDOM("GB", "the United Kingdom", "united kingdom", "great britain", "britain", "england");

    private final String abbreviation;
    private final String name;
    private final List<String> utterances;

    CountryInfo(final String setAbbreviation, final String setName, final String... setUtterances)
    {
        abbreviation = setAbbreviation;
        name = setName;
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

    public List<String> getUtterances()
    {
        return utterances;
    }
}
