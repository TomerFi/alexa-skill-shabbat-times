package info.tomfi.alexa.skills.shabbattimes.api.enums;

public enum GeoTypes
{
    CITY("city"),
    GEO_NAME("geoname"),
    POSITIONAL("pos"),
    ZIP("zip");

    public final String type;

    GeoTypes(final String setType)
    {
        type = setType;
    }
}
