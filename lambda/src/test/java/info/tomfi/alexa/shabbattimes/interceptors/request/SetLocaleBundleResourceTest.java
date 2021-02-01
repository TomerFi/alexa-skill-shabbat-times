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
import static info.tomfi.alexa.shabbattimes.BundleKey.DEFAULT_OK;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import com.amazon.ask.attributes.AttributesManager;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.Request;
import info.tomfi.alexa.shabbattimes.TextService;
import java.util.HashMap;
import java.util.Locale;
import java.util.ResourceBundle;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
final class SetLocaleBundleResourceTest {
  @Mock TextService textor;
  @InjectMocks private SetLocaleBundleResource sut;

  @Test
  void intercepting_a_request_with_a_known_locale_loads_the_correct_bundle(
      @Mock final AttributesManager attribMngr,
      @Mock final Request request,
      @Mock final HandlerInput input) {
    // short-circuit text service to load resource bundle
    given(textor.getResource(eq(new Locale("te_ST"))))
        .willAnswer(
            inv -> ResourceBundle.getBundle("locales/Responses", (Locale) inv.getArgument(0)));
    // stub attribute manager with fake attributes map, and stub input with manager
    var requestAttribs = new HashMap<String, Object>();
    given(attribMngr.getRequestAttributes()).willReturn(requestAttribs);
    given(input.getAttributesManager()).willReturn(attribMngr);
    // stub the request locale for predefined to_FI, and stub input with the request
    given(request.getLocale()).willReturn("te_ST");
    given(input.getRequest()).willReturn(request);
    // when intercepting of a request is invoked
    sut.process(input);
    // then the attribute manager should save the resource bundle as a request attribute
    then(attribMngr).should().setRequestAttributes(eq(requestAttribs));
    // then the bundle should pick up predefined locales/Responses_to_FI.properties resource
    var bundle = (ResourceBundle) requestAttribs.get(L10N_BUNDLE.toString());
    assertThat(bundle.getString(DEFAULT_OK.toString())).isEqualTo("DEFAULT_OK test text.");
  }

  @Test
  void intercepting_a_request_with_an_unknown_locale_loads_the_default_en_us_resource(
      @Mock final AttributesManager attribMngr,
      @Mock final Request request,
      @Mock final HandlerInput input) {
    // short-circuit text service to load resource bundle
    given(textor.getResource(eq(new Locale("ff_TT"))))
        .willAnswer(
            inv -> ResourceBundle.getBundle("locales/Responses", (Locale) inv.getArgument(0)));
    // stub attribute manager with fake attributes map, and stub input with manager
    var requestAttribs = new HashMap<String, Object>();
    given(attribMngr.getRequestAttributes()).willReturn(requestAttribs);
    given(input.getAttributesManager()).willReturn(attribMngr);
    // stub the request locale for undefined to_FI, and stub input with the request
    given(request.getLocale()).willReturn("ff_TT");
    given(input.getRequest()).willReturn(request);
    // when intercepting of a request is invoked
    sut.process(input);
    // then the attribute manager should save the resource bundle as a request attribute
    then(attribMngr).should().setRequestAttributes(eq(requestAttribs));
    // then the bundle should pick up default locales/Responses_en_US.properties resource
    var bundle = (ResourceBundle) requestAttribs.get(L10N_BUNDLE.toString());
    assertThat(bundle.getString(DEFAULT_OK.toString())).isEqualTo("Ok.");
  }
}
