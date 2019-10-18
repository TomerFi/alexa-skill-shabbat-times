package info.tomfi.alexa.skills.shabbattimes.api.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ItemCategories
{
    CANDLES("candles"),
    HAVDALAH("havdalah"),
    HOLIDAY("holiday"),
    PARASHAT("parashat");

    @Getter private final String value;
}
