/**
 * Copyright Tomer Figenblat Licensed under the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in
 * writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific
 * language governing permissions and limitations under the License.
 */
package info.tomfi.alexa.shabbattimes.internal;

import static info.tomfi.alexa.shabbattimes.AttributeKey.L10N_BUNDLE;

import info.tomfi.alexa.shabbattimes.BundleKey;
import java.time.DayOfWeek;
import java.util.Map;
import java.util.ResourceBundle;

/** Utility class for retrieving text date from the locale resource bundle properties file. */
public final class LocalizationUtils {
  private LocalizationUtils() {
    //
  }

  /**
   * A static tool for retrieving text from the bundle resource object stored as request attributes.
   *
   * @param attributes attributes storing the bundle resource object with the key of {@linkplain
   *     info.tomfi.alexa.shabbattimes.enums.AttributeKey#L10N_BUNDLE}.
   * @param key a {@link info.tomfi.alexa.shabbattimes.enums.BundleKey} member corresponding to the
   *     locale properties file key.
   * @return the text retrieved as String.
   */
  public static String getFromBundle(final Map<String, Object> attributes, final BundleKey key) {
    return ((ResourceBundle) attributes.get(L10N_BUNDLE.toString())).getString(key.toString());
  }

  /**
   * Get the 'shabbat starts at' presentation text from the resource bundle locale properties file
   * based on the current day of week.
   *
   * @param requestAttributes attributes storing the bundle resource object with the key of
   *     {@linkplain info.tomfi.alexa.shabbattimes.enums.AttributeKey#L10N_BUNDLE}.
   * @param dow the day of week memeber to retrieve the text for.
   * @return the text retrieved as String.
   */
  public static String getStartsAtPresentation(
      final Map<String, Object> requestAttributes, final DayOfWeek dow) {
    return getFromBundle(
        requestAttributes,
        dow.equals(DayOfWeek.THURSDAY)
            ? BundleKey.SHABBAT_START_TOMORROW
            : dow.equals(DayOfWeek.FRIDAY)
                ? BundleKey.SHABBAT_START_TODAY
                : dow.equals(DayOfWeek.SATURDAY)
                    ? BundleKey.SHABBAT_START_YESTERDAY
                    : BundleKey.SHABBAT_START_FRIDAY);
  }

  /**
   * Get the 'shabbat ends at' presentation text from the resource bundle locale properties file
   * based on the current day of week.
   *
   * @param requestAttributes attributes storing the bundle resource object with the key of
   *     {@linkplain info.tomfi.alexa.shabbattimes.enums.AttributeKey#L10N_BUNDLE}.
   * @param dow the day of week memeber to retrieve the text for.
   * @return the text retrieved as String.
   */
  public static String getEndsAtPresentation(
      final Map<String, Object> requestAttributes, final DayOfWeek dow) {
    return getFromBundle(
        requestAttributes,
        dow.equals(DayOfWeek.FRIDAY)
            ? BundleKey.SHABBAT_END_TOMORROW
            : dow.equals(DayOfWeek.SATURDAY)
                ? BundleKey.SHABBAT_END_TODAY
                : BundleKey.SHABBAT_END_SATURDAY);
  }
}
