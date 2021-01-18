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
package info.tomfi.shabbattimes.skill.assertions;

import info.tomfi.hebcal.api.response.items.ResponseItem;
import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;

public final class ResponseItemAssert extends AbstractAssert<ResponseItemAssert, ResponseItem> {
  protected ResponseItemAssert(final ResponseItem actual) {
    super(actual, ResponseItemAssert.class);
  }

  public ResponseItemAssert hebrewIs(final String testHebrew) {
    isNotNull();
    Assertions.assertThat(actual.getHebrew()).isEqualTo(testHebrew);
    return this;
  }

  public ResponseItemAssert dateIs(final String testDate) {
    isNotNull();
    Assertions.assertThat(actual.getDate()).isEqualTo(testDate);
    return this;
  }

  public ResponseItemAssert titleIs(final String testTitle) {
    isNotNull();
    Assertions.assertThat(actual.getTitle()).isEqualTo(testTitle);
    return this;
  }

  public ResponseItemAssert categoryIs(final String testCategory) {
    isNotNull();
    Assertions.assertThat(actual.getCategory()).isEqualTo(testCategory);
    return this;
  }

  public ResponseItemAssert linkIs(final String testLink) {
    isNotNull();
    Assertions.assertThat(actual.getLink().get()).isEqualTo(testLink);
    return this;
  }

  public ResponseItemAssert linkIsEmpty() {
    isNotNull();
    Assertions.assertThat(actual.getLink().isPresent()).isFalse();
    return this;
  }

  public ResponseItemAssert memoIs(final String testMemo) {
    isNotNull();
    Assertions.assertThat(actual.getMemo().get()).isEqualTo(testMemo);
    return this;
  }

  public ResponseItemAssert memoIsEmpty() {
    isNotNull();
    Assertions.assertThat(actual.getMemo().isPresent()).isFalse();
    return this;
  }

  public ResponseItemAssert subcatIs(final String testSubcat) {
    isNotNull();
    Assertions.assertThat(actual.getSubcat().get()).isEqualTo(testSubcat);
    return this;
  }

  public ResponseItemAssert subcatIsEmpty() {
    isNotNull();
    Assertions.assertThat(actual.getSubcat().isPresent()).isFalse();
    return this;
  }

  public ResponseItemAssert isYomtov() {
    isNotNull();
    Assertions.assertThat(actual.isYomtov()).isTrue();
    return this;
  }

  public ResponseItemAssert isNotYomtov() {
    isNotNull();
    Assertions.assertThat(actual.isYomtov()).isFalse();
    return this;
  }
}
