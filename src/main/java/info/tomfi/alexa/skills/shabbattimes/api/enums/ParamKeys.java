package info.tomfi.alexa.skills.shabbattimes.api.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Enum helper for constructing the api request, used for setting the various keys in the param query.
 *
 * @author Tomer Figenblat {@literal <tomer.figenblat@gmail.com>}
 */
@RequiredArgsConstructor
public enum ParamKeys
{
    ASHKENAZIS_TRANSLITERATIONS("a"),
    CANDLE_LIGHTING("b"),
    CITY("city"), // used with GeoTypes.CITY
    GEO_ID("geonameid"), // used with GeoTypes.GEO_NAME
    GEO_TYPE("geo"),
    GREGORIAN_DAY("gd"),
    GREGORIAN_MONTH("gm"),
    GREGORIAN_YEAR("gy"),
    HAVDALAH("m"),
    INCLUDE_TURAH_HAFTARAH("leyning"),
    LATITUDE("latitude"), // used with GeoTypes.POSITIONAL
    LONGITUDE("longitude"), // used with GeoTypes.POSITIONAL
    OUTPUT_FORMAT("cfg"),
    TZID("tzid"), // used with GeoTypes.POSITIONAL
    ZIP("zip"); // used with GeoTypes.ZIP

    @Getter private final String key;
}
