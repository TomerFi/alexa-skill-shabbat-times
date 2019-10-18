package info.tomfi.alexa.skills.shabbattimes.request.handlers;

import static info.tomfi.alexa.skills.shabbattimes.tools.LocalizationUtils.getFromBundle;

import java.util.Optional;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.impl.LaunchRequestHandler;
import com.amazon.ask.model.LaunchRequest;
import com.amazon.ask.model.Response;

import org.springframework.stereotype.Component;

import info.tomfi.alexa.skills.shabbattimes.enums.BundleKeys;

import lombok.val;

@Component
public class SessionStartHandler implements LaunchRequestHandler {

    @Override
    public boolean canHandle(final HandlerInput input, final LaunchRequest request)
    {
        return true;
    }

    @Override
    public Optional<Response> handle(final HandlerInput input, final LaunchRequest request) {
        val attributes = input.getAttributesManager().getRequestAttributes();
        return input.getResponseBuilder()
                .withSpeech(getFromBundle(attributes, BundleKeys.WELCOME_SPEECH))
                .withReprompt(getFromBundle(attributes, BundleKeys.DEFAULT_REPROMPT))
                .withShouldEndSession(false)
                .build();
    }
}
