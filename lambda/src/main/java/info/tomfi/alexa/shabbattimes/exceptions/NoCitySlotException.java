package info.tomfi.alexa.shabbattimes.exceptions;

import com.amazon.ask.exception.AskSdkException;

/** Exception to throw when a city slot value was not found. */
public final class NoCitySlotException extends AskSdkException {
  private static final long serialVersionUID = 18L;

  public NoCitySlotException() {
    super("city slot was not found in request context");
  }
}
