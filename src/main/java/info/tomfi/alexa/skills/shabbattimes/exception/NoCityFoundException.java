package info.tomfi.alexa.skills.shabbattimes.exception;

import com.amazon.ask.exception.AskSdkException;

public final class NoCityFoundException extends AskSdkException
{
    private final static long serialVersionUID = 22L;

    public NoCityFoundException(final String message) {
        super(message);
    }

    public NoCityFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
