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
package info.tomfi.alexa.skills.shabbattimes.exception.handlers;

import static info.tomfi.alexa.skills.shabbattimes.enums.BundleKeys.DEFAULT_REPROMPT;
import static info.tomfi.alexa.skills.shabbattimes.enums.BundleKeys.EXC_NO_CITY_FOUND;
import static info.tomfi.alexa.skills.shabbattimes.tools.LocalizationUtils.getFromBundle;

import com.amazon.ask.dispatcher.exception.ExceptionHandler;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.Response;
import info.tomfi.alexa.skills.shabbattimes.exception.NoCitySlotException;
import java.util.Optional;
import lombok.NoArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Component;

/**
 * Extension of com.amazon.ask.dispatcher.exception.ExceptionHandler. Used for handling {@link
 * info.tomfi.alexa.skills.shabbattimes.exception.NoCitySlotException} exceptions.
 *
 * @author Tomer Figenblat {@literal <tomer.figenblat@gmail.com>}
 */
@Component
@NoArgsConstructor
public final class NoCitySlotHandler implements ExceptionHandler {
  @Override
  public boolean canHandle(final HandlerInput input, final Throwable throwable) {
    return throwable instanceof NoCitySlotException;
  }

  @Override
  public Optional<Response> handle(final HandlerInput input, final Throwable throwable) {
    val requestAttributes = input.getAttributesManager().getRequestAttributes();
    return input
        .getResponseBuilder()
        .withSpeech(getFromBundle(requestAttributes, EXC_NO_CITY_FOUND))
        .withReprompt(getFromBundle(requestAttributes, DEFAULT_REPROMPT))
        .withShouldEndSession(false)
        .build();
  }
}
