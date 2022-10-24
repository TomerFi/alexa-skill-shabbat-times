package info.tomfi.alexa.shabbattimes;

import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

/** Service for working with the locales resource bundles. */
public interface TextService {
  /**
   * Get a text from the bundle resource by a BundleKey.
   *
   * @param attributes map storing the resource bundle with the L10N_BUNDLE key.
   * @param key the BundleKey to retrieve from the bundle resource.
   * @return the text retreived.
   */
  String getText(Map<String, Object> attributes, BundleKey key);

  /**
   * Load a resource bundle by a Locale.
   *
   * @param locale the Locale to retrieve the resource bundle by.
   * @return the ResourceBundle, can be stored as an attribute for later usage.
   */
  ResourceBundle getResource(Locale locale);
}
