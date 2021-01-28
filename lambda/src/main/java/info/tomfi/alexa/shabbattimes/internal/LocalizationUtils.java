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
package info.tomfi.alexa.shabbattimes.internal;

import static info.tomfi.alexa.shabbattimes.AttributeKey.L10N_BUNDLE;

import info.tomfi.alexa.shabbattimes.BundleKey;
import java.util.Map;
import java.util.ResourceBundle;

public final class LocalizationUtils {
  private LocalizationUtils() {
    //
  }

  public static String getFromBundle(final Map<String, Object> attributes, final BundleKey key) {
    return ((ResourceBundle) attributes.get(L10N_BUNDLE.toString())).getString(key.toString());
  }
}
