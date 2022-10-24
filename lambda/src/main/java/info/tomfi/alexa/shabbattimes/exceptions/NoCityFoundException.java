package info.tomfi.alexa.shabbattimes.exceptions;

import com.amazon.ask.exception.AskSdkException;

/** Exception to throw when the requested city was not found. */
public final class NoCityFoundException extends AskSdkException {
  private static final long serialVersionUID = 22L;

  public NoCityFoundException() {
    super("the requested city was not found");
  }
}
