/*
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
import info.tomfi.alexa.shabbattimes.TextService;
import java.util.Locale;
import org.springframework.stereotype.Component;

/** Request inteceptor loading the resource bundle for the request locale as a request attribute. */
@Component
public final class SetLocaleBundleResource implements RequestInterceptor {
  private final TextService textor;

  public SetLocaleBundleResource(final TextService setTextor) {
    textor = setTextor;
  }

  @Override
  public void process(final HandlerInput input) {
    var bundle = textor.getResource(new Locale(input.getRequest().getLocale()));
    var attribs = input.getAttributesManager().getRequestAttributes();
    attribs.put(L10N_BUNDLE.toString(), bundle);
    input.getAttributesManager().setRequestAttributes(attribs);
  }
}
