package info.tomfi.alexa.shabbattimes.exceptions;

import com.amazon.ask.exception.AskSdkException;

/** Exception to throw when the json backend file was found. */
public final class NoJsonFileException extends AskSdkException {
  private static final long serialVersionUID = 14L;

  public NoJsonFileException(final Throwable cause) {
    super("No json file found", cause);
  }
}
