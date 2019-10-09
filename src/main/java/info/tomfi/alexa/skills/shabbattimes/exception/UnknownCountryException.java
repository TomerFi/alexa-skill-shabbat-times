package info.tomfi.alexa.skills.shabbattimes.exception;

import com.amazon.ask.exception.AskSdkException;

public final class UnknownCountryException extends AskSdkException
{
    private final static long serialVersionUID = 16L;

    public UnknownCountryException(final String message) {
        super(message);
    }

    public UnknownCountryException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
