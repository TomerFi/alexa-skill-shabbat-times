package info.tomfi.alexa.skills.shabbattimes.exception;

import com.amazon.ask.exception.AskSdkException;

public final class NoJsonFileException extends AskSdkException
{
    private final static long serialVersionUID = 14L;

    public NoJsonFileException(final String message) {
        super(message);
    }

    public NoJsonFileException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
