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

  protected String getText(final BundleKey key) {
    return bundle.getString(key.toString());
  }
}
