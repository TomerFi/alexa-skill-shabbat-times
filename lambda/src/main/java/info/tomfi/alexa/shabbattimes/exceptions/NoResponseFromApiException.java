package info.tomfi.alexa.shabbattimes.exceptions;

import com.amazon.ask.exception.AskSdkException;

/** Exception to throw when no response was received from the api. */
public final class NoResponseFromApiException extends AskSdkException {
  private static final long serialVersionUID = 24L;

  public NoResponseFromApiException(final Throwable cause) {
    super("no response from shabbat api", cause);
  }
}
