package info.tomfi.alexa.skills.shabbattimes.api.enums;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum ItemCategories
{
    CANDLES("candles"),
    HAVDALAH("havdalah"),
    HOLIDAY("holiday"),
    PARASHAT("parashat");

    private final String value;

    public String getValue()
    {
        return value;
    }
}
