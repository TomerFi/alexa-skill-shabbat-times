package info.tomfi.alexa.skills.shabbattimes.request.handlers;

import static info.tomfi.alexa.skills.shabbattimes.tools.GlobalEnums.BundleKeys;
import static info.tomfi.alexa.skills.shabbattimes.tools.LocalizationUtils.getBundleFromAttribures;
import static info.tomfi.alexa.skills.shabbattimes.tools.LocalizationUtils.getFromBundle;

import java.util.Optional;
import java.util.ResourceBundle;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.impl.LaunchRequestHandler;
import com.amazon.ask.model.LaunchRequest;
import com.amazon.ask.model.Response;

import info.tomfi.alexa.skills.shabbattimes.annotation.IncludeRequestHandler;

@IncludeRequestHandler
public class SessionStartHandler implements LaunchRequestHandler {

    @Override
    public boolean canHandle(final HandlerInput input, final LaunchRequest request)
    {
        return true;
    }

    @Override
    public Optional<Response> handle(final HandlerInput input, final LaunchRequest request) {
        final ResourceBundle bundle = getBundleFromAttribures(input.getAttributesManager().getRequestAttributes());
        return input.getResponseBuilder()
                .withSpeech(getFromBundle(bundle, BundleKeys.WELCOME_SPEECH))
                .withReprompt(getFromBundle(bundle, BundleKeys.DEFAULT_REPROMPT))
                .withShouldEndSession(false)
                .build();
    }
}
