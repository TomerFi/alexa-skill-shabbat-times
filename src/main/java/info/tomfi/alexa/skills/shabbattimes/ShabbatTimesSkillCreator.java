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

import lombok.NoArgsConstructor;

@NoArgsConstructor
public final class ShabbatTimesSkillCreator
{
    @Autowired private List<GenericRequestHandler<HandlerInput, Optional<Response>>> requestHandlers;
    @Autowired private List<GenericExceptionHandler<HandlerInput, Optional<Response>>> exceptionHandlers;
    @Autowired private List<GenericRequestInterceptor<HandlerInput>> requestInterceptors;
    @Autowired private List<GenericResponseInterceptor<HandlerInput, Optional<Response>>> responseInterceptors;

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
