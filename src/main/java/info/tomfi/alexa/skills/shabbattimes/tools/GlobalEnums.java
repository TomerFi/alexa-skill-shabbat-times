package info.tomfi.alexa.skills.shabbattimes.tools;

public final class GlobalEnums
{
    private GlobalEnums()
    {
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

    public static enum Attributes
    {
        CITY("city"),
        COUNTRY("country"),
        L10N_BUNDLE("l10nBunble"),
        LAST_INTENT("lastIntent");

        public final String name;

        Attributes(final String setName)
        {
            name = setName;
        }
    }

    public static enum BundleKeys
    {
        ASK_FOR_ANOTHER_REPROMPT,
        CITIES_IN_COUNTRY_FMT,
        DEFAULT_ASK_FOR_CITY,
        DEFAULT_ERROR,
        DEFAULT_OK,
        DEFAULT_REPROMPT,
        HELP_REPROMPT,
        HELP_SPEECH,
        NOT_FOUND_FMT,
        NOT_FOUND_IN_ISRAEL,
        NOT_FOUND_IN_UK,
        NOT_FOUND_IN_US,
        SHABBAT_END_SATURDAY,
        SHABBAT_END_TOMORROW,
        SHABBAT_END_TODAY,
        SHABBAT_INFORMATION_FMT,
        SHABBAT_START_FRIDAY,
        SHABBAT_START_TODAY,
        SHABBAT_START_TOMORROW,
        SHABBAT_START_YESTERDAY,
        THANKS_AND_BYE,
        WELCOME_SPEECH;
    }
}
