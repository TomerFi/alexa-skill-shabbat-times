package info.tomfi.alexa.skills.shabbattimes.enums;

public enum Attributes
{
    CITY("city"),
    COUNTRY("country"),
    L10N_BUNDLE("l10nBunble"),
    LAST_INTENT("lastIntent");

    private final String name;

    Attributes(final String setName)
    {
        name = setName;
    }

    public String getName()
    {
        return name;
    }
}
