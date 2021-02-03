/**
 * Copyright Tomer Figenblat.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
