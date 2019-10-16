package info.tomfi.alexa.skills.shabbattimes.enums;

public enum CountryInfo
{
    ISRAEL("IL", "Israel"),
    UNITED_STATES("US", "the United States"),
    UNITED_KINGDOM("GB", "the United Kingdom");

    private final String abbreviation;
    private final String name;

    CountryInfo(final String setAbbreviation, final String setName)
    {
        abbreviation = setAbbreviation;
        name = setName;
    }

    public String getAbbreviation()
    {
        return abbreviation;
    }

    public String getName()
    {
        return name;
    }
}
