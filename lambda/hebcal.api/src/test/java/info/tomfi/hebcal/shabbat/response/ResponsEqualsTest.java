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
package info.tomfi.hebcal.shabbat.response;

import static nl.jqno.equalsverifier.Warning.INHERITED_DIRECTLY_FROM_OBJECT;
import static nl.jqno.equalsverifier.Warning.NULL_FIELDS;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import java.util.stream.Stream;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

final class ResponsEqualsTest {
  @ParameterizedTest
  @MethodSource
  @SuppressWarnings({"rawtypes", "unchecked"})
  void verify_response_objects_equals_and_hashcode_implementations(final Class abstraction, final Class implementation) {
    // verify abstraction, not implementing equals and hashcode
    EqualsVerifier.forClass(abstraction)
        .withRedefinedSubclass(implementation)
        .usingGetClass()
        .suppress(INHERITED_DIRECTLY_FROM_OBJECT)
        .verify();
    // verify implementation, not verifying nulls
    EqualsVerifier.forClass(implementation)
        .withRedefinedSuperclass()
        .suppress(NULL_FIELDS)
        .verify();
  }

  static Stream<Arguments> verify_response_objects_equals_and_hashcode_implementations() {
    return Stream.of(
      arguments(Response.class, AutoValue_Response.class),
      arguments(ResponseItem.class, AutoValue_ResponseItem.class),
      arguments(ResponseLocation.class, AutoValue_ResponseLocation.class)
    );
  }
}
