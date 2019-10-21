package info.tomfi.alexa.skills.shabbattimes.api.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Enum helper for constructing the api request, used for setting retrieval geo type.
 *
 * @author Tomer Figenblat {@literal <tomer.figenblat@gmail.com>}
 */
@RequiredArgsConstructor
public enum GeoTypes
{
    CITY("city"),
    GEO_NAME("geoname"),
    POSITIONAL("pos"),
    ZIP("zip");

    @Getter private final String type;
}
