package info.tomfi.alexa.skills.shabbattimes.exception;

import com.amazon.ask.exception.AskSdkException;

public final class NoItemFoundForDateExepion extends AskSdkException
{
    private final static long serialVersionUID = 26L;

    public NoItemFoundForDateExepion(final String message) {
        super(message);
    }

    public NoItemFoundForDateExepion(final String message, final Throwable cause) {
        super(message, cause);
    }
}
