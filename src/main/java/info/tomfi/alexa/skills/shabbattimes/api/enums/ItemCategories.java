package info.tomfi.alexa.skills.shabbattimes.api.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Enum helper for constructing the api request, used for parsing the response item list.
 *
 * @author Tomer Figenblat {@literal <tomer.figenblat@gmail.com>}
 */
@RequiredArgsConstructor
public enum ItemCategories
{
    CANDLES("candles"),
    HAVDALAH("havdalah"),
    HOLIDAY("holiday"),
    PARASHAT("parashat");

    @Getter private final String value;
}
