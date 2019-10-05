package info.tomfi.alexa.skills.shabbattimes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.amazon.ask.Skill;
import com.amazon.ask.Skills;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.Response;
import com.amazon.ask.request.handler.GenericRequestHandler;
import com.amazon.ask.SkillStreamHandler;

import info.tomfi.alexa.skills.shabbattimes.annotation.IncludeHandler;
import info.tomfi.alexa.skills.shabbattimes.exceptionhandlers.SdkExceptionHandler;
import info.tomfi.alexa.skills.shabbattimes.interceptors.PersistAttributesInterceptor;

import org.reflections.Reflections;

public class ShabbatTimesStreamHandler extends SkillStreamHandler
{
    @SuppressWarnings("unchecked")
    private static Skill getSkill() throws IllegalAccessException, InstantiationException
    {
        final Reflections reflections = new Reflections("info.tomfi.alexa.skills.shabbattimes.handlers");
        final Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(IncludeHandler.class);
        final List<GenericRequestHandler<HandlerInput, Optional<Response>>> handlers = new ArrayList<>();
        for (Class<?> clazz : annotated)
        {
            handlers.add((GenericRequestHandler<HandlerInput, Optional<Response>>) clazz.newInstance());
        }
        return Skills.standard()
            .addRequestHandlers(handlers)
            .addResponseInterceptor(new PersistAttributesInterceptor())
            .addExceptionHandler(new SdkExceptionHandler())
            .build();
    }

    public ShabbatTimesStreamHandler() throws IllegalAccessException, InstantiationException
    {
        super(getSkill());
    }
}
