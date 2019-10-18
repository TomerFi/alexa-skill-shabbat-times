package info.tomfi.alexa.skills.shabbattimes.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Slots
{
    CITY_GB("City_GB"),
    CITY_IL("City_IL"),
    CITY_US("City_US"),
    COUNTRY("Country");

    @Getter private final String name;
}
