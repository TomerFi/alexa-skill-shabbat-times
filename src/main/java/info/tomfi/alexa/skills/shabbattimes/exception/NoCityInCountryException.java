package info.tomfi.alexa.skills.shabbattimes.exception;

import com.amazon.ask.exception.AskSdkException;

/**
 * Extension of com.amazon.ask.exception.AskSdkException. Used when the requested city in the
 * requested country was not found.
 *
 * @author Tomer Figenblat {@literal <tomer.figenblat@gmail.com>}
 */
public final class NoCityInCountryException extends AskSdkException
{
    private static final long serialVersionUID = 20L;

    public NoCityInCountryException(final String message)
    {
        super(message);
    }

    public NoCityInCountryException(final String message, final Throwable cause)
    {
        super(message, cause);
    }
}
