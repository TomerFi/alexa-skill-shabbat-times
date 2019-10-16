package info.tomfi.alexa.skills.shabbattimes.response.interceptors;

import static info.tomfi.alexa.skills.shabbattimes.enums.Attributes.LAST_INTENT;

import static com.amazon.ask.request.Predicates.requestType;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.interceptor.ResponseInterceptor;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Response;

import java.util.Map;
import java.util.Optional;

public final class PersistSessionAttributes implements ResponseInterceptor
{
    @Override
    public void process(final HandlerInput input, final Optional<Response> response) {
        if (input.matches(requestType(IntentRequest.class)) && !response.get().getShouldEndSession())
        {
            final Map<String, Object> attribs = input.getAttributesManager().getSessionAttributes();
            attribs.put(LAST_INTENT.getName(), ((IntentRequest) input.getRequest()).getIntent().getName());
            input.getAttributesManager().setSessionAttributes(attribs);
        }
    }
}
