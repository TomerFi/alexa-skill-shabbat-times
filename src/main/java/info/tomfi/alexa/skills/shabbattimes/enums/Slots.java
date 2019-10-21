package info.tomfi.alexa.skills.shabbattimes.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Enum helper for identifying the request slots.
 *
 * @author Tomer Figenblat {@literal <tomer.figenblat@gmail.com>}
 */
@RequiredArgsConstructor
public enum Slots
{
    CITY_GB("City_GB"),
    CITY_IL("City_IL"),
    CITY_US("City_US"),
    COUNTRY("Country");

    @Getter private final String name;
}
