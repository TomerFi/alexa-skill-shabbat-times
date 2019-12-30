/**
 * Copyright 2019 Tomer Figenblat
 *
 * <p>Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of the License at
 *
 * <p>http://www.apache.org/licenses/LICENSE-2.0
 *
 * <p>Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing permissions and
 * limitations under the License.
 */
package info.tomfi.alexa.skills.shabbattimes.request.handlers;

import static info.tomfi.alexa.skills.shabbattimes.assertions.Assertions.assertThat;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.RequestEnvelope;
import com.amazon.ask.model.SessionEndedRequest;
import lombok.val;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public final class SessionEndHandlerTest {
  private static SessionEndedRequest fakeRequest;
  private static HandlerInput fakeInput;

  private static SessionEndHandler handlerInTest;

  @BeforeAll
  public static void initialize() {
    fakeRequest = SessionEndedRequest.builder().build();
    val fakeEnvelope = RequestEnvelope.builder().withRequest(fakeRequest).build();
    fakeInput = HandlerInput.builder().withRequestEnvelope(fakeEnvelope).build();

    handlerInTest = new SessionEndHandler();
  }

  @Test
  @DisplayName("test canHandle method implmentation")
  public void canHandle_fakeArgs_returnTrue() {
    assertThat(handlerInTest.canHandle(fakeInput, fakeRequest)).isTrue();
  }

  @Test
  @DisplayName("test handle method implementation")
  public void handle_fakeArgs_validateResponse() {
    assertThat(handlerInTest.handle(fakeInput, fakeRequest).isPresent()).isFalse();
  }
}
