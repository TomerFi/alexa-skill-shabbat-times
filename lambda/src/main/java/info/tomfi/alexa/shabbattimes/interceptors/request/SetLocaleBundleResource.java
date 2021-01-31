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
package info.tomfi.alexa.shabbattimes.interceptors.request;

import static info.tomfi.alexa.shabbattimes.AttributeKey.L10N_BUNDLE;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.interceptor.RequestInterceptor;
import java.util.Locale;
import java.util.ResourceBundle;
import org.springframework.stereotype.Component;

/**
 * Implemenation of com.amazon.ask.dispatcher.request.interceptor.RequestInterceptor, intercept
 * requests prior to their handling and plant the reference for the appropriate resource bundle
 * object based on the requests' locale.
 */
@Component
public final class SetLocaleBundleResource implements RequestInterceptor {
  private static final String L10N_BASE_NAME = "locales/Responses";

  public SetLocaleBundleResource() {
    //
  }

  @Override
  public void process(final HandlerInput input) {
    var bundle =
        ResourceBundle.getBundle(L10N_BASE_NAME, new Locale(input.getRequest().getLocale()));
    var attribs = input.getAttributesManager().getRequestAttributes();
    attribs.put(L10N_BUNDLE.toString(), bundle);
    input.getAttributesManager().setRequestAttributes(attribs);
  }
}
