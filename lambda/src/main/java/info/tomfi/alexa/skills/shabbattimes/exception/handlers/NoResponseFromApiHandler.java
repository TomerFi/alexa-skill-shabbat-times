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
package info.tomfi.alexa.skills.shabbattimes.exception.handlers;

import static info.tomfi.alexa.skills.shabbattimes.enums.BundleKeys.EXC_UNRECOVERABLE_ERROR;
import static info.tomfi.alexa.skills.shabbattimes.tools.LocalizationUtils.getFromBundle;

import com.amazon.ask.dispatcher.exception.ExceptionHandler;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.Response;
import info.tomfi.alexa.skills.shabbattimes.exception.NoResponseFromApiException;
import java.util.Optional;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Extension of com.amazon.ask.dispatcher.exception.ExceptionHandler. Used for handling {@link
 * info.tomfi.alexa.skills.shabbattimes.exception.NoResponseFromApiException} exceptions.
 *
 * @author Tomer Figenblat {@literal <tomer.figenblat@gmail.com>}
 */
@Component
@NoArgsConstructor
public final class NoResponseFromApiHandler implements ExceptionHandler {
  @Override
  public boolean canHandle(final HandlerInput input, final Throwable throwable) {
    return throwable instanceof NoResponseFromApiException;
  }

  @Override
  public Optional<Response> handle(final HandlerInput input, final Throwable throwable) {
    return input
        .getResponseBuilder()
        .withSpeech(
            getFromBundle(
                input.getAttributesManager().getRequestAttributes(), EXC_UNRECOVERABLE_ERROR))
        .withShouldEndSession(true)
        .build();
  }
}
