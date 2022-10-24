package info.tomfi.alexa.shabbattimes.exceptions;

import com.amazon.ask.exception.AskSdkException;

/** Exception to throw when the requested city in the requested country was not found. */
public final class NoCityInCountryException extends AskSdkException {
  private static final long serialVersionUID = 20L;

  public NoCityInCountryException() {
    super("the requeset city was not found within the requested country");
  }
}
