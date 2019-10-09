package info.tomfi.alexa.skills.shabbattimes.exception;

import com.amazon.ask.exception.AskSdkException;

public final class NoCityInCountryException extends AskSdkException
{
    private final static long serialVersionUID = 20L;

    public NoCityInCountryException(final String message) {
        super(message);
    }

    public NoCityInCountryException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
