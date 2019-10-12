package info.tomfi.alexa.skills.shabbattimes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.amazon.ask.Skill;
import com.amazon.ask.Skills;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.Response;
import com.amazon.ask.request.exception.handler.GenericExceptionHandler;
import com.amazon.ask.request.handler.GenericRequestHandler;
import com.amazon.ask.SkillStreamHandler;

import info.tomfi.alexa.skills.shabbattimes.annotation.IncludeRequestHandler;
import info.tomfi.alexa.skills.shabbattimes.exception.handlers.NoCityFoundHandler;
import info.tomfi.alexa.skills.shabbattimes.exception.handlers.NoCityInCountryHandler;
import info.tomfi.alexa.skills.shabbattimes.exception.handlers.NoCitySlotHandler;
import info.tomfi.alexa.skills.shabbattimes.exception.handlers.NoCountrySlotHandler;
import info.tomfi.alexa.skills.shabbattimes.exception.handlers.NoJsonFileHandler;
import info.tomfi.alexa.skills.shabbattimes.exception.handlers.NoResponseFromAPIHandler;
import info.tomfi.alexa.skills.shabbattimes.exception.handlers.SdkExceptionHandler;
import info.tomfi.alexa.skills.shabbattimes.exception.handlers.UnknownCountryHandler;
import info.tomfi.alexa.skills.shabbattimes.response.interceptors.PersistSessionAttributes;

import org.reflections.Reflections;

public class ShabbatTimesStreamHandler extends SkillStreamHandler
{
    private static final String HANDLERS_PACKAGE_NAME = "info.tomfi.alexa.skills.shabbattimes.request.handlers";

    @SuppressWarnings("unchecked")
    private static Skill getSkill() throws IllegalAccessException, InstantiationException
    {
        final Reflections reflections = new Reflections(HANDLERS_PACKAGE_NAME);
        final Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(IncludeRequestHandler.class);

        final List<GenericRequestHandler<HandlerInput, Optional<Response>>> requestHandlers = new ArrayList<>();
        for (Class<?> clazz : annotated)
        {
            requestHandlers.add((GenericRequestHandler<HandlerInput, Optional<Response>>) clazz.newInstance());
        }

        final List<GenericExceptionHandler<HandlerInput, Optional<Response>>> exceptionHandlers = new ArrayList<>();
        exceptionHandlers.add(new NoCountrySlotHandler());
        exceptionHandlers.add(new NoJsonFileHandler());
        exceptionHandlers.add(new UnknownCountryHandler());
        exceptionHandlers.add(new NoCitySlotHandler());
        exceptionHandlers.add(new NoCityInCountryHandler());
        exceptionHandlers.add(new NoCityFoundHandler());
        exceptionHandlers.add(new NoResponseFromAPIHandler());
        exceptionHandlers.add(new SdkExceptionHandler()); // keep last

        return Skills.standard()
            .addResponseInterceptor(new PersistSessionAttributes())
            .addRequestHandlers(requestHandlers)
            .addExceptionHandlers(exceptionHandlers)
            .build();
    }

    public ShabbatTimesStreamHandler() throws IllegalAccessException, InstantiationException
    {
        super(getSkill());
    }
}
