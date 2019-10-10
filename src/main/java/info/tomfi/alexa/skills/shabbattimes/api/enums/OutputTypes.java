package info.tomfi.alexa.skills.shabbattimes.api.enums;

public enum OutputTypes
{
    JSON("json"),
    RSS("r");

    public final String type;

    OutputTypes(final String setType)
    {
        type = setType;
    }
}
