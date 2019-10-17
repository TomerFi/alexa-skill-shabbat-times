package info.tomfi.alexa.skills.shabbattimes.exception;

import com.amazon.ask.exception.AskSdkException;

public final class NoItemFoundForDateException extends AskSdkException
{
    private final static long serialVersionUID = 26L;

    public NoItemFoundForDateException(final String message) {
        super(message);
    }

    public NoItemFoundForDateException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
