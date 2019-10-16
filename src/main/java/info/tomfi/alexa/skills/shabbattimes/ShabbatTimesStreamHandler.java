package info.tomfi.alexa.skills.shabbattimes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.amazon.ask.Skill;
import com.amazon.ask.Skills;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.Response;
import com.amazon.ask.request.exception.handler.GenericExceptionHandler;
import com.amazon.ask.request.handler.GenericRequestHandler;
import com.amazon.ask.SkillStreamHandler;

import info.tomfi.alexa.skills.shabbattimes.exception.handlers.NoCityFoundHandler;
import info.tomfi.alexa.skills.shabbattimes.exception.handlers.NoCityInCountryHandler;
import info.tomfi.alexa.skills.shabbattimes.exception.handlers.NoCitySlotHandler;
import info.tomfi.alexa.skills.shabbattimes.exception.handlers.NoCountrySlotHandler;
import info.tomfi.alexa.skills.shabbattimes.exception.handlers.NoItemFoundForDateHandler;
import info.tomfi.alexa.skills.shabbattimes.exception.handlers.NoJsonFileHandler;
import info.tomfi.alexa.skills.shabbattimes.exception.handlers.NoResponseFromAPIHandler;
import info.tomfi.alexa.skills.shabbattimes.exception.handlers.SdkExceptionHandler;
import info.tomfi.alexa.skills.shabbattimes.exception.handlers.UnknownCountryHandler;
import info.tomfi.alexa.skills.shabbattimes.request.handlers.CancelIntentHandler;
import info.tomfi.alexa.skills.shabbattimes.request.handlers.CountrySelectedIntentHandler;
import info.tomfi.alexa.skills.shabbattimes.request.handlers.FallbackIntentHandler;
import info.tomfi.alexa.skills.shabbattimes.request.handlers.GetCityIntentHandler;
import info.tomfi.alexa.skills.shabbattimes.request.handlers.HelpIntentHandler;
import info.tomfi.alexa.skills.shabbattimes.request.handlers.NoIntentHandler;
import info.tomfi.alexa.skills.shabbattimes.request.handlers.SessionEndHandler;
import info.tomfi.alexa.skills.shabbattimes.request.handlers.SessionStartHandler;
import info.tomfi.alexa.skills.shabbattimes.request.handlers.StopIntentHandler;
import info.tomfi.alexa.skills.shabbattimes.request.handlers.ThanksIntentHandler;
import info.tomfi.alexa.skills.shabbattimes.request.handlers.YesIntentHandler;
import info.tomfi.alexa.skills.shabbattimes.request.interceptors.SetLocaleBundleResource;
import info.tomfi.alexa.skills.shabbattimes.response.interceptors.PersistSessionAttributes;
import info.tomfi.alexa.skills.shabbattimes.tools.DIConfiguration;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ShabbatTimesStreamHandler extends SkillStreamHandler
{
    private static Skill getSkill() throws IllegalAccessException, InstantiationException
    {
        final List<GenericRequestHandler<HandlerInput, Optional<Response>>> requestHandlers = new ArrayList<>();
        try
        (
            AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(DIConfiguration.class)
        )
        {
            requestHandlers.add(context.getBean(GetCityIntentHandler.class));
        }
        requestHandlers.add(new CancelIntentHandler());
        requestHandlers.add(new CountrySelectedIntentHandler());
        requestHandlers.add(new FallbackIntentHandler());
        requestHandlers.add(new HelpIntentHandler());
        requestHandlers.add(new NoIntentHandler());
        requestHandlers.add(new SessionEndHandler());
        requestHandlers.add(new SessionStartHandler());
        requestHandlers.add(new StopIntentHandler());
        requestHandlers.add(new ThanksIntentHandler());
        requestHandlers.add(new YesIntentHandler());


        final List<GenericExceptionHandler<HandlerInput, Optional<Response>>> exceptionHandlers = new ArrayList<>();
        exceptionHandlers.add(new NoCountrySlotHandler());
        exceptionHandlers.add(new NoJsonFileHandler());
        exceptionHandlers.add(new UnknownCountryHandler());
        exceptionHandlers.add(new NoCitySlotHandler());
        exceptionHandlers.add(new NoCityInCountryHandler());
        exceptionHandlers.add(new NoCityFoundHandler());
        exceptionHandlers.add(new NoResponseFromAPIHandler());
        exceptionHandlers.add(new NoItemFoundForDateHandler());
        exceptionHandlers.add(new SdkExceptionHandler()); // keep last

        return Skills.standard()
            .addRequestInterceptor(new SetLocaleBundleResource())
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
