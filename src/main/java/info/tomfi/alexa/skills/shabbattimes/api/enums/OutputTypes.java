package info.tomfi.alexa.skills.shabbattimes.api.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum OutputTypes
{
    JSON("json"),
    RSS("r");

    @Getter private final String type;
}
