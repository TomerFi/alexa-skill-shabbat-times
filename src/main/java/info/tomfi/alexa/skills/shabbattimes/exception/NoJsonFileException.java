package info.tomfi.alexa.skills.shabbattimes.exception;

import com.amazon.ask.exception.AskSdkException;

/**
 * Extension of com.amazon.ask.exception.AskSdkException. Used when json backend file was found.
 *
 * @author Tomer Figenblat {@literal <tomer.figenblat@gmail.com>}
 */
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
