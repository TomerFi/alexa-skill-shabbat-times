package info.tomfi.alexa.skills.shabbattimes.response.interceptors;

import static com.amazon.ask.request.Predicates.requestType;
import static info.tomfi.alexa.skills.shabbattimes.enums.Attributes.LAST_INTENT;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.interceptor.ResponseInterceptor;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Response;

import java.util.Optional;

import lombok.NoArgsConstructor;
import lombok.val;

import org.springframework.stereotype.Component;

/**
 * Implemenation of com.amazon.ask.dispatcher.request.interceptor.ResponseInterceptor,
 * intercept response object on their way out and manipulate the session attributes.
 *
 * @author Tomer Figenblat {@literal <tomer.figenblat@gmail.com>}
 */
@Component
@NoArgsConstructor
public final class PersistSessionAttributes implements ResponseInterceptor
{
    @Override
    public void process(final HandlerInput input, final Optional<Response> response)
    {
        if (
            input.matches(requestType(IntentRequest.class))
            && !response.get().getShouldEndSession()
        )
        {
            val attribs = input.getAttributesManager().getSessionAttributes();
            attribs.put(
                LAST_INTENT.getName(),
                ((IntentRequest) input.getRequest()).getIntent().getName()
            );
            input.getAttributesManager().setSessionAttributes(attribs);
        }
    }
}
