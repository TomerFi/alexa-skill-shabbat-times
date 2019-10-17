package info.tomfi.alexa.skills.shabbattimes.tools;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.Response;
import com.amazon.ask.request.exception.handler.GenericExceptionHandler;
import com.amazon.ask.request.handler.GenericRequestHandler;
import com.amazon.ask.request.interceptor.GenericRequestInterceptor;
import com.amazon.ask.request.interceptor.GenericResponseInterceptor;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import info.tomfi.alexa.skills.shabbattimes.api.APIRequestMaker;
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

@Lazy
@Configuration
@ComponentScan(basePackages = "info.tomfi.alexa.skills.shabbattimes")
public class DIConfiguration implements ApplicationContextAware
{
    private ApplicationContext context;

    public void setApplicationContext(ApplicationContext setContext)
    {
        context = setContext;
    }

    @Bean
    public APIRequestMaker getAPIRequestMaker()
    {
        return new APIRequestMaker("https://www.hebcal.com/shabbat/");
    }

    @Bean
    public List<GenericRequestHandler<HandlerInput, Optional<Response>>> getRequestHandlers()
    {
        return Arrays.asList(
            context.getBean(GetCityIntentHandler.class),
            new CancelIntentHandler(),
            new CountrySelectedIntentHandler(),
            new FallbackIntentHandler(),
            new HelpIntentHandler(),
            new NoIntentHandler(),
            new SessionEndHandler(),
            new SessionStartHandler(),
            new StopIntentHandler(),
            new ThanksIntentHandler(),
            new YesIntentHandler()
        );
    }

    @Bean
    public List<GenericExceptionHandler<HandlerInput, Optional<Response>>> getExceptionHandlers()
    {
        return Arrays.asList(
            new NoCountrySlotHandler(),
            new NoJsonFileHandler(),
            new UnknownCountryHandler(),
            new NoCitySlotHandler(),
            new NoCityInCountryHandler(),
            new NoCityFoundHandler(),
            new NoResponseFromAPIHandler(),
            new NoItemFoundForDateHandler(),
            new SdkExceptionHandler() // keep last
        );
    }

    @Bean
    public List<GenericRequestInterceptor<HandlerInput>> getRequestInterceptors()
    {
        return Arrays.asList(new SetLocaleBundleResource());
    }

    @Bean
    public List<GenericResponseInterceptor<HandlerInput, Optional<Response>>> getResponseInterceptors()
    {
        return Arrays.asList(new PersistSessionAttributes());
    }
}
