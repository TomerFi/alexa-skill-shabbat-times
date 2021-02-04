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
package info.tomfi.alexa.shabbattimes.handlers.exception;

import static info.tomfi.alexa.shabbattimes.BundleKey.DEFAULT_REPROMPT;
import static info.tomfi.alexa.shabbattimes.BundleKey.EXC_NO_COUNTRY_PROVIDED;

import com.amazon.ask.dispatcher.exception.ExceptionHandler;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.Response;
import info.tomfi.alexa.shabbattimes.TextService;
import info.tomfi.alexa.shabbattimes.exceptions.NoCountryFoundException;
import java.util.Optional;
import org.springframework.stereotype.Component;

/** Exception handler used for handling {@link NoCountryFoundException} cases. */
@Component
public final class NoCountryFoundHandler implements ExceptionHandler {
  private final TextService textor;

  public NoCountryFoundHandler(final TextService setTextor) {
    textor = setTextor;
  }

  @Override
  public boolean canHandle(final HandlerInput input, final Throwable throwable) {
    return throwable instanceof NoCountryFoundException;
  }

  @Override
  public Optional<Response> handle(final HandlerInput input, final Throwable throwable) {
    var requestAttributes = input.getAttributesManager().getRequestAttributes();
    return input
        .getResponseBuilder()
        .withSpeech(textor.getText(requestAttributes, EXC_NO_COUNTRY_PROVIDED))
        .withReprompt(textor.getText(requestAttributes, DEFAULT_REPROMPT))
        .withShouldEndSession(false)
        .build();
  }
}
