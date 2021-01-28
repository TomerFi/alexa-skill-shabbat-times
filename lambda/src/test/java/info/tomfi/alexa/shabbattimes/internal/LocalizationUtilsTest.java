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
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

import com.github.javafaker.Faker;
import info.tomfi.alexa.shabbattimes.BundleKey;
import java.util.HashMap;
import java.util.ResourceBundle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@Tag("unit-tests")
final class LocalizationUtilsTest {
  private Faker faker;

  @BeforeEach
  void initialize() {
    faker = new Faker();
  }

  @Test
  void test_the_static_get_from_bundle_tool(@Mock ResourceBundle resource) {
    // create request attributes with mock bundled resource
    var attribs = new HashMap<String, Object>();
    attribs.put(L10N_BUNDLE.toString(), resource);
    // create random bundle key and value, and mock resource with it
    var key = faker.options().nextElement(BundleKey.values());
    var value = faker.lorem().word();
    given(resource.getString(eq(key.toString()))).willReturn(value);
    // when invoking the tool with the attributes and key
    var retrieved = LocalizationUtils.getFromBundle(attribs, key);
    // verify the return value
    then(retrieved).isEqualTo(value);
  }
}
