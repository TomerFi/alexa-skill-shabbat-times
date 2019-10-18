package info.tomfi.alexa.skills.shabbattimes.api.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum GeoTypes
{
    CITY("city"),
    GEO_NAME("geoname"),
    POSITIONAL("pos"),
    ZIP("zip");

    @Getter private final String type;
}
