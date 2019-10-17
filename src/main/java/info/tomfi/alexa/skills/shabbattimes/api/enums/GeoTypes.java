package info.tomfi.alexa.skills.shabbattimes.api.enums;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum GeoTypes
{
    CITY("city"),
    GEO_NAME("geoname"),
    POSITIONAL("pos"),
    ZIP("zip");

    private final String type;

    public String getType()
    {
        return type;
    }
}
