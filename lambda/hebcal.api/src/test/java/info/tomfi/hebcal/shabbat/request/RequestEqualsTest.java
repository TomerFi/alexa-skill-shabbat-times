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
package info.tomfi.hebcal.shabbat.request;

import static nl.jqno.equalsverifier.Warning.INHERITED_DIRECTLY_FROM_OBJECT;
import static nl.jqno.equalsverifier.Warning.NULL_FIELDS;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

final class RequestEqualsTest {
  @Test
  void verify_request_object_equals_and_hashcode_implementations() {
    // verify abstraction, not implementing equals and hashcode
    EqualsVerifier.forClass(Request.class)
        .withRedefinedSubclass(AutoValue_Request.class)
        .usingGetClass()
        .suppress(INHERITED_DIRECTLY_FROM_OBJECT)
        .verify();
    // verify implementation, not verifying nulls
    EqualsVerifier.forClass(AutoValue_Request.class)
        .withRedefinedSuperclass()
        .suppress(NULL_FIELDS)
        .verify();
  }
}
