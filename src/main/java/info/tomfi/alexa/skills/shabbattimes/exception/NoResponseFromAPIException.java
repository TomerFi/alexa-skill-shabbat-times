package info.tomfi.alexa.skills.shabbattimes.exception;

import com.amazon.ask.exception.AskSdkException;

public final class NoResponseFromAPIException extends AskSdkException
{
    private final static long serialVersionUID = 24L;

    public NoResponseFromAPIException(final String message) {
        super(message);
    }

    public NoResponseFromAPIException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
