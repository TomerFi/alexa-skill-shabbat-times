/**
 * Copyright Tomer Figenblat
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
package info.tomfi.alexa.skills.shabbattimes.request.interceptors;

import static info.tomfi.alexa.skills.shabbattimes.enums.Attributes.L10N_BUNDLE;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.interceptor.RequestInterceptor;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import lombok.NoArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Component;

/**
 * Implemenation of com.amazon.ask.dispatcher.request.interceptor.RequestInterceptor, intercept
 * requests prior to their handling and plant the reference for the appropriate resource bundle
 * object based on the requests' locale.
 *
 * @author Tomer Figenblat {@literal <tomer.figenblat@gmail.com>}
 */
@Component
@NoArgsConstructor
public final class SetLocaleBundleResource implements RequestInterceptor {
  private static final String L10N_BASE_NAME = "locales/Responses";

  @Override
  public void process(final HandlerInput input) {
    ResourceBundle bundle;
    try {
      bundle = ResourceBundle.getBundle(L10N_BASE_NAME, new Locale(input.getRequest().getLocale()));
    } catch (MissingResourceException exc) {
      bundle = ResourceBundle.getBundle(L10N_BASE_NAME, Locale.US);
    }

    val attribs = input.getAttributesManager().getRequestAttributes();
    attribs.put(L10N_BUNDLE.getName(), bundle);
    input.getAttributesManager().setRequestAttributes(attribs);
  }
}
