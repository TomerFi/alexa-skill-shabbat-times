package info.tomfi.alexa.skills.shabbattimes.exception;

import com.amazon.ask.exception.AskSdkException;

public final class NoCitySlotException extends AskSdkException
{
    private final static long serialVersionUID = 18L;

    public NoCitySlotException(final String message) {
        super(message);
    }

    public NoCitySlotException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
