package info.tomfi.alexa.skills.shabbattimes.api.enums;

public enum FlagStates
{
    OFF("off"),
    ON("on");

    public final String state;

    FlagStates(final String setState)
    {
        state = setState;
    }
}
