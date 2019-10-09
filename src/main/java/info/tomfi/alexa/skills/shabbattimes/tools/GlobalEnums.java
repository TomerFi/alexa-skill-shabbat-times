package info.tomfi.alexa.skills.shabbattimes.tools;

public final class GlobalEnums
{
    private GlobalEnums()
    {
    }

    public static enum Slots
    {
        CITY_GB("City_GB"),
        CITY_IL("City_IL"),
        CITY_US("City_US"),
        COUNTRY("Country");

        public final String name;

        Slots(final String setName)
        {
            name = setName;
        }
    }

    public static enum Intents
    {
        CANCEL("AMAZON.CancelIntent"),
        COUNTRY_SELECTED("CountrySelected"),
        FALLBACK("AMAZON.FallbackIntent"),
        GET_CITY("GetCityIntent"),
        HELP("AMAZON.HelpIntent"),
        NO("AMAZON.NoIntent"),
        STOP("AMAZON.StopIntent"),
        THANKS("ThanksIntent"),
        YES("AMAZON.YesIntent");

        public final String name;

        Intents(final String setName)
        {
            name = setName;
        }
    }

    public static enum CountryInfo
    {
        ISRAEL("IL", "Israel"),
        UNITED_STATES("US", "the United States"),
        UNITED_KINGDOM("GB", "the United Kingdom");

        public final String abbreviation;
        public final String name;

        CountryInfo(final String setAbbreviation, final String setName)
        {
            abbreviation = setAbbreviation;
            name = setName;
        }
    }

    public static enum Attributes
    {
        CITY("city"),
        COUNTRY("country"),
        GEOID("geoid"),
        GEONAME("geoname"),
        LAST_INTENT("lastIntent");

        public final String name;

        Attributes(final String setName)
        {
            name = setName;
        }
    }
}
