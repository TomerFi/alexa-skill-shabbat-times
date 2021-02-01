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
import static info.tomfi.alexa.shabbattimes.BundleKey.DEFAULT_OK;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

import com.github.javafaker.Faker;
import info.tomfi.alexa.shabbattimes.BundleKey;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@Tag("unit-tests")
final class TextServiceImplTest {
  private Faker faker;
  private TextServiceImpl sut;

  @BeforeEach
  void initialize() {
    faker = new Faker();
    sut = new TextServiceImpl();
  }

  @Test
  void extract_resource_from_bundle_in_attributes(@Mock ResourceBundle resource) {
    // create request attributes with mock bundled resource
    var attribs = Map.of(L10N_BUNDLE.toString(), (Object) resource);
    // create random bundle key and value, and mock resource with it
    var key = faker.options().nextElement(BundleKey.values());
    var value = faker.lorem().word();
    given(resource.getString(eq(key.toString()))).willReturn(value);
    // when invoking the tool with the attributes and key
    var retrieved = sut.getText(attribs, key);
    // verify the return value
    then(retrieved).isEqualTo(value);
  }

  @Test
  void using_the_service_to_load_a_resource_bundle_for_a_known_locale_returns_correct_bundle() {
    // load testing resource bundle
    var bundle = sut.getResource(new Locale("te_ST"));
    // verify te_ST locale bundle was loaded
    then(bundle.getString(DEFAULT_OK.toString())).isEqualTo("DEFAULT_OK test text.");
  }

  @Test
  void using_the_service_to_load_a_resource_bundle_for_an_unknown_locale_returns_default_bundle() {
    // load testing resource bundle for unknown locale
    var bundle = sut.getResource(new Locale("ff_TT"));
    // verify en_US locale bundle was loaded
    then(bundle.getString(DEFAULT_OK.toString())).isEqualTo("Ok.");
  }
}
