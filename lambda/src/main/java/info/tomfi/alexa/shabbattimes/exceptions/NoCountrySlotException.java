package info.tomfi.alexa.shabbattimes.exceptions;

import com.amazon.ask.exception.AskSdkException;

/** Exception to throw when the country slot value was not found. */
public final class NoCountrySlotException extends AskSdkException {
  private static final long serialVersionUID = 12L;

  public NoCountrySlotException(final String message) {
    super(message);
  }
}
