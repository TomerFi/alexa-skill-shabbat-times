package info.tomfi.alexa.skills.shabbattimes;

import java.util.List;
import java.util.Optional;

import com.amazon.ask.Skill;
import com.amazon.ask.Skills;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.Response;
import com.amazon.ask.request.exception.handler.GenericExceptionHandler;
import com.amazon.ask.request.handler.GenericRequestHandler;
import com.amazon.ask.request.interceptor.GenericRequestInterceptor;
import com.amazon.ask.request.interceptor.GenericResponseInterceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public final class ShabbatTimesSkillCreator
{
    private final List<GenericRequestHandler<HandlerInput, Optional<Response>>> requestHandlers;
    private final List<GenericExceptionHandler<HandlerInput, Optional<Response>>> exceptionHandlers;
    private final List<GenericRequestInterceptor<HandlerInput>> requestInterceptors;
    private final List<GenericResponseInterceptor<HandlerInput, Optional<Response>>> responseInterceptors;

    @Autowired
    public ShabbatTimesSkillCreator(
        final List<GenericRequestHandler<HandlerInput, Optional<Response>>> setRequestHandlers,
        final List<GenericExceptionHandler<HandlerInput, Optional<Response>>> setExceptionHandlers,
        final List<GenericRequestInterceptor<HandlerInput>> setRequestInterceptors,
        final List<GenericResponseInterceptor<HandlerInput, Optional<Response>>> setResponseInterceptors
    )
    {
        requestHandlers = setRequestHandlers;
        exceptionHandlers = setExceptionHandlers;
        requestInterceptors = setRequestInterceptors;
        responseInterceptors = setResponseInterceptors;
    }

    public Skill getSkill() throws IllegalAccessException, InstantiationException
    {
        return Skills.standard()
            .addRequestInterceptors(requestInterceptors)
            .addResponseInterceptors(responseInterceptors)
            .addRequestHandlers(requestHandlers)
            .addExceptionHandlers(exceptionHandlers)
            .build();
    }
}
