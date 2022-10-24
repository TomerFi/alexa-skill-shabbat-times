package info.tomfi.alexa.shabbattimes.exceptions;

import com.amazon.ask.exception.AskSdkException;

/** Exception to throw when the requested country was not found. */
public final class NoCountryFoundException extends AskSdkException {
  private static final long serialVersionUID = 28L;

  public NoCountryFoundException() {
    super("the requested country was not found");
  }
}
