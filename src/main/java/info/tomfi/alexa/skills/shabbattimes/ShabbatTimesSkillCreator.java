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

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public final class ShabbatTimesSkillCreator
{
    private final List<GenericRequestHandler<HandlerInput, Optional<Response>>> requestHandlers;
    private final List<GenericExceptionHandler<HandlerInput, Optional<Response>>> exceptionHandlers;
    private final List<GenericRequestInterceptor<HandlerInput>> requestInterceptors;
    private final List<GenericResponseInterceptor<HandlerInput, Optional<Response>>> responseInterceptors;

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
