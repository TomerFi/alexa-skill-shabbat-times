package info.tomfi.alexa.skills.shabbattimes.exception;

import com.amazon.ask.exception.AskSdkException;

/**
 * Extension of com.amazon.ask.exception.AskSdkException. Used when no appropriate respones item
 * was found for the requested date.
 *
 * @author Tomer Figenblat {@literal <tomer.figenblat@gmail.com>}
 */
public final class NoItemFoundForDateException extends AskSdkException
{
    private static final long serialVersionUID = 26L;

    public NoItemFoundForDateException(final String message)
    {
        super(message);
    }

    public NoItemFoundForDateException(final String message, final Throwable cause)
    {
        super(message, cause);
    }
}
