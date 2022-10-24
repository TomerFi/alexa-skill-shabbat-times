package info.tomfi.alexa.shabbattimes.services;

import static info.tomfi.alexa.shabbattimes.AttributeKey.L10N_BUNDLE;

import info.tomfi.alexa.shabbattimes.BundleKey;
import info.tomfi.alexa.shabbattimes.TextService;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import org.springframework.stereotype.Component;

/** Service provider working with predefined resource bundles. */
@Component
public final class TextServiceImpl implements TextService {
  private static final String L10N_BASE_NAME = "locales/Responses";

  @Override
  public String getText(final Map<String, Object> attributes, final BundleKey key) {
    return ((ResourceBundle) attributes.get(L10N_BUNDLE.toString())).getString(key.toString());
  }

  @Override
  public ResourceBundle getResource(final Locale locale) {
    return ResourceBundle.getBundle(L10N_BASE_NAME, locale);
  }
}
