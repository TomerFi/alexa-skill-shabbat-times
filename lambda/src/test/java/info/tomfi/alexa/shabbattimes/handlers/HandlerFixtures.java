package info.tomfi.alexa.shabbattimes.handlers;

import static info.tomfi.alexa.shabbattimes.AttributeKey.L10N_BUNDLE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.when;
import static org.mockito.quality.Strictness.LENIENT;

import com.amazon.ask.attributes.AttributesManager;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import info.tomfi.alexa.shabbattimes.BundleKey;
import info.tomfi.alexa.shabbattimes.TextService;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;

/** Various testing fixtures for testing the handler classes. */
@MockitoSettings(strictness = LENIENT)
public class HandlerFixtures {
  @Mock protected HandlerInput input;
  @Mock protected AttributesManager attribMngr;
  @Mock protected TextService textor;

  private final Map<String, Object> requestAttribs;
  private final ResourceBundle bundle;

  /** Default contructor loads testing resource bundle into testing request attributes. */
  public HandlerFixtures() {
    // load bundle resource for text phrases
    bundle = ResourceBundle.getBundle("locales/Responses", new Locale("te_ST"));
    // create a map of request attributes
    requestAttribs = Map.of(L10N_BUNDLE.toString(), bundle);
  }

  @BeforeEach
  void initilalizeFixtures() {
    // stub the input mock with a mocked attributes manager stubbed with the request attributes
    when(attribMngr.getRequestAttributes()).thenReturn(requestAttribs);
    when(input.getAttributesManager()).thenReturn(attribMngr);
    // short-circuit the text service to use this local getText method
    when(textor.getText(anyMap(), any(BundleKey.class)))
        .thenAnswer(inv -> getText(inv.getArgument(1)));
  }

  /**
   * Helper method for short-circuting the text service and retrieving values from resource bundles.
   *
   * @param key the bundle key to look for.
   * @return the the value for the key.
   */
  protected String getText(final BundleKey key) {
    return bundle.getString(key.toString());
  }
}
