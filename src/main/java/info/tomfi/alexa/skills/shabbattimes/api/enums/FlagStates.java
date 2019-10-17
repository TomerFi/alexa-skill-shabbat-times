package info.tomfi.alexa.skills.shabbattimes.api.enums;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum FlagStates
{
    OFF("off"),
    ON("on");

    private final String state;

    public String getState()
    {
        return state;
    }
}
