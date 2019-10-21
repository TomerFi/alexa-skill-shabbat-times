package info.tomfi.alexa.skills.shabbattimes.api.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Enum helper for constructing the api request, used for setting the consumed response type.
 *
 * @author Tomer Figenblat {@literal <tomer.figenblat@gmail.com>}
 */
@RequiredArgsConstructor
public enum OutputTypes
{
    JSON("json"),
    RSS("r");

    @Getter private final String type;
}
