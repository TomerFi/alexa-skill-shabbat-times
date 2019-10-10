package info.tomfi.alexa.skills.shabbattimes.api.enums;

public enum ItemCategories
{
    CANDLES("candles"),
    HAVDALAH("havdalah"),
    HOLIDAY("holiday");

    public final String value;

    ItemCategories(final String setValue)
    {
        value = setValue;
    }
}
