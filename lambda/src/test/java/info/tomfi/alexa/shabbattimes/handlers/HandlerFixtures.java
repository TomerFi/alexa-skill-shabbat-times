package info.tomfi.alexa.shabbattimes.handlers;

import static info.tomfi.alexa.shabbattimes.AttributeKey.L10N_BUNDLE;

import info.tomfi.alexa.shabbattimes.BundleKey;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

public class HandlerFixtures {
  private final Map<String, Object> requestAttribs;
  private final ResourceBundle bundle;

  public HandlerFixtures() {
    bundle = ResourceBundle.getBundle("locales/Responses", new Locale("te_ST"));
    requestAttribs = Map.of(L10N_BUNDLE.toString(), bundle);
  }

  protected Map<String, Object> getRequestAttribs() {
    return requestAttribs;
  }

  protected String getFromBundle(final BundleKey key) {
    return bundle.getString(key.toString());
  }
}
