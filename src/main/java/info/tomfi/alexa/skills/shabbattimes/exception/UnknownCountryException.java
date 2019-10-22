package info.tomfi.alexa.skills.shabbattimes.exception;

import com.amazon.ask.exception.AskSdkException;

/**
 * Extension of com.amazon.ask.exception.AskSdkException. Used when an unknown country was asked
 * by the user.
 *
 * @author Tomer Figenblat {@literal <tomer.figenblat@gmail.com>}
 */
public final class UnknownCountryException extends AskSdkException
{
    private static final long serialVersionUID = 16L;

    public UnknownCountryException(final String message)
    {
        super(message);
    }

    public UnknownCountryException(final String message, final Throwable cause)
    {
        super(message, cause);
    }
}
