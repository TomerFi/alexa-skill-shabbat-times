package info.tomfi.alexa.skills.shabbattimes.enums;

public enum Slots
{
    CITY_GB("City_GB"),
    CITY_IL("City_IL"),
    CITY_US("City_US"),
    COUNTRY("Country");

    private final String name;

    Slots(final String setName)
    {
        name = setName;
    }

    public String getName()
    {
        return name;
    }
}
