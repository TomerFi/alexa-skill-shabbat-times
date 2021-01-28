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
package info.tomfi.alexa.shabbattimes;

import static java.util.Objects.nonNull;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import java.util.function.Predicate;
import java.util.stream.Stream;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@Tag("unit-tests")
final class EnumStringTest {
  @ParameterizedTest
  @MethodSource
  <T> void verify_enum_values_to_string_implementation_is_not_null_nor_blank(
      final T[] values, final Predicate<T> matcher) {
    assertThat(values).hasSizeGreaterThan(0).allMatch(matcher::test);
  }

  static Stream<Arguments> verify_enum_values_to_string_implementation_is_not_null_nor_blank() {
    return Stream.of(
        arguments(
            AttributeKey.values(),
            (Predicate<AttributeKey>) v -> nonNull(v.toString()) && !v.toString().isBlank()),
        arguments(
            BundleKey.values(),
            (Predicate<BundleKey>) v -> nonNull(v.toString()) && !v.toString().isBlank()),
        arguments(
            IntentType.values(),
            (Predicate<IntentType>) v -> nonNull(v.toString()) && !v.toString().isBlank()),
        arguments(
            SlotName.CitySlot.values(),
            (Predicate<SlotName.CitySlot>) v -> nonNull(v.toString()) && !v.toString().isBlank()));
  }
}
