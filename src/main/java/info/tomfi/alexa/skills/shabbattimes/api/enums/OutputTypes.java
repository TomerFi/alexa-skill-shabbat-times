package info.tomfi.alexa.skills.shabbattimes.api.enums;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum OutputTypes
{
    JSON("json"),
    RSS("r");

    private final String type;

    public String getType()
    {
        return type;
    }
}
