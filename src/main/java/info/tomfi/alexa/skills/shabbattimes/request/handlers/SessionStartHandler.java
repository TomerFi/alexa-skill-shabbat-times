/**
 * Copyright 2019 Tomer Figenblat
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package info.tomfi.alexa.skills.shabbattimes.request.handlers;

import static info.tomfi.alexa.skills.shabbattimes.tools.LocalizationUtils.getFromBundle;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.impl.LaunchRequestHandler;
import com.amazon.ask.model.LaunchRequest;
import com.amazon.ask.model.Response;

import info.tomfi.alexa.skills.shabbattimes.enums.BundleKeys;

import java.util.Optional;

import lombok.NoArgsConstructor;
import lombok.val;

import org.springframework.stereotype.Component;

/**
 * Implementation of com.amazon.ask.dispatcher.request.handler.impl.LaunchRequestHandler,
 * handles requests of type com.amazon.ask.model.LaunchRequest.
 *
 * @author Tomer Figenblat {@literal <tomer.figenblat@gmail.com>}
 */
@Component
@NoArgsConstructor
public final class SessionStartHandler implements LaunchRequestHandler
{

    @Override
    public boolean canHandle(final HandlerInput input, final LaunchRequest request)
    {
        return true;
    }

    @Override
    public Optional<Response> handle(final HandlerInput input, final LaunchRequest request)
    {
        val attributes = input.getAttributesManager().getRequestAttributes();
        return input.getResponseBuilder()
                .withSpeech(getFromBundle(attributes, BundleKeys.WELCOME_SPEECH))
                .withReprompt(getFromBundle(attributes, BundleKeys.DEFAULT_REPROMPT))
                .withShouldEndSession(false)
                .build();
    }
}
