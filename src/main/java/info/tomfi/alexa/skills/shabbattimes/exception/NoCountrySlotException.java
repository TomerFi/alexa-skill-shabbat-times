package info.tomfi.alexa.skills.shabbattimes.exception;

import com.amazon.ask.exception.AskSdkException;

/**
 * Extension of com.amazon.ask.exception.AskSdkException. Used when no country slot value was not
 * found.
 *
 * @author Tomer Figenblat {@literal <tomer.figenblat@gmail.com>}
 */
public final class NoCountrySlotException extends AskSdkException
{
    private static final long serialVersionUID = 12L;

    public NoCountrySlotException(final String message)
    {
        super(message);
    }

    public NoCountrySlotException(final String message, final Throwable cause)
    {
        super(message, cause);
    }
}
