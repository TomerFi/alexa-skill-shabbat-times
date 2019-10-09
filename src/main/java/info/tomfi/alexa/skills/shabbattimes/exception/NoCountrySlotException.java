package info.tomfi.alexa.skills.shabbattimes.exception;

import com.amazon.ask.exception.AskSdkException;

public final class NoCountrySlotException extends AskSdkException
{
    private final static long serialVersionUID = 12L;

    public NoCountrySlotException(final String message) {
        super(message);
    }

    public NoCountrySlotException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
