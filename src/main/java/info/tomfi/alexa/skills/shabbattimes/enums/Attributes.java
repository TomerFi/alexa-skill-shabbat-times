package info.tomfi.alexa.skills.shabbattimes.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Enum helper for setting attribute keys.
 *
 * @author Tomer Figenblat {@literal <tomer.figenblat@gmail.com>}
 */
@RequiredArgsConstructor
public enum Attributes
{
    CITY("city"),
    COUNTRY("country"),
    L10N_BUNDLE("l10nBunble"),
    LAST_INTENT("lastIntent");

    @Getter private final String name;
}
