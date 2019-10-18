package info.tomfi.alexa.skills.shabbattimes.api.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum FlagStates
{
    OFF("off"),
    ON("on");

    @Getter private final String state;
}
