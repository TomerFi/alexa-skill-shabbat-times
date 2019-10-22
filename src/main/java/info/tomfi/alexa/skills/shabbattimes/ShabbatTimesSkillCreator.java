package info.tomfi.alexa.skills.shabbattimes;

import com.amazon.ask.Skill;
import com.amazon.ask.Skills;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.Response;
import com.amazon.ask.request.exception.handler.GenericExceptionHandler;
import com.amazon.ask.request.handler.GenericRequestHandler;
import com.amazon.ask.request.interceptor.GenericRequestInterceptor;
import com.amazon.ask.request.interceptor.GenericResponseInterceptor;

import java.util.List;
import java.util.Optional;

import lombok.NoArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * Class for constructing a Skill object with the injected handlers and interceptors.
 *
 * @author Tomer Figenblat {@literal <tomer.figenblat@gmail.com>}
 */
@NoArgsConstructor
public final class ShabbatTimesSkillCreator
{
    @Autowired
    private List<GenericRequestHandler<HandlerInput, Optional<Response>>> requestHandlers;
    @Autowired
    private List<GenericExceptionHandler<HandlerInput, Optional<Response>>> exceptionHandlers;
    @Autowired
    private List<GenericRequestInterceptor<HandlerInput>> requestInterceptors;
    @Autowired
    private List<GenericResponseInterceptor<HandlerInput, Optional<Response>>> responseInterceptors;

    /**
     * Build the Shabbat Times Skill object.
     * @return the Shabbat Time Skill object.
     */
    public Skill getSkill()
    {
        return Skills.standard()
            .addRequestInterceptors(requestInterceptors)
            .addResponseInterceptors(responseInterceptors)
            .addRequestHandlers(requestHandlers)
            .addExceptionHandlers(exceptionHandlers)
            .build();
    }
}
