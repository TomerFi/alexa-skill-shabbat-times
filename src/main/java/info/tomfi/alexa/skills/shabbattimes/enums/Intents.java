package info.tomfi.alexa.skills.shabbattimes.enums;

public enum Intents
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

    private final String name;

    Intents(final String setName)
    {
        name = setName;
    }

    public String getName()
    {
        return name;
    }
}
