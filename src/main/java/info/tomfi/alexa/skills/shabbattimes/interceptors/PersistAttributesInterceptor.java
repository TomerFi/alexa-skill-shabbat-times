package info.tomfi.alexa.skills.shabbattimes.interceptors;

import static com.amazon.ask.request.Predicates.requestType;

import com.amazon.ask.attributes.AttributesManager;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.interceptor.ResponseInterceptor;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Response;

import java.util.HashMap;
import java.util.Optional;

public final class PersistAttributesInterceptor implements ResponseInterceptor
{
    @Override
    public void process(final HandlerInput input, final Optional<Response> response) {
        final AttributesManager attribs = input.getAttributesManager();
        if (input.matches(requestType(IntentRequest.class)) && !response.get().getShouldEndSession())
        {
            attribs.setPersistentAttributes(
                new HashMap<String, Object>()
                {
                    private static final long serialVersionUID = 1L;
                    {
                        put("lastIntent", ((IntentRequest) input.getRequest()).getIntent().getName());
                    }
                }
            );
        }

        try
        {
            attribs.savePersistentAttributes();
        } catch (IllegalStateException exc)
        {
        }
    }
}