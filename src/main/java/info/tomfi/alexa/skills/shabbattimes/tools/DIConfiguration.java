package info.tomfi.alexa.skills.shabbattimes.tools;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.amazon.ask.dispatcher.exception.ExceptionHandler;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.dispatcher.request.interceptor.RequestInterceptor;
import com.amazon.ask.dispatcher.request.interceptor.ResponseInterceptor;
import com.amazon.ask.model.Response;
import com.amazon.ask.request.exception.handler.GenericExceptionHandler;
import com.amazon.ask.request.handler.GenericRequestHandler;
import com.amazon.ask.request.interceptor.GenericRequestInterceptor;
import com.amazon.ask.request.interceptor.GenericResponseInterceptor;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;

import org.reflections.Reflections;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import info.tomfi.alexa.skills.shabbattimes.api.APIRequestInitializer;
import info.tomfi.alexa.skills.shabbattimes.api.APIRequestMaker;

@Lazy
@Configuration
@ComponentScan(basePackages = "info.tomfi.alexa.skills.shabbattimes")
public class DIConfiguration
{
    @Bean
    public APIRequestMaker getRequestMaker()
    {
        return new APIRequestMaker();
    }

    @Bean
    public GenericUrl getApiUrl()
    {
        return new GenericUrl("https://www.hebcal.com/shabbat/");
    }

    @Bean
    public HttpTransport getTransport()
    {
        return new NetHttpTransport();
    }

    @Bean
    public HttpRequestInitializer getInitializer()
    {
        return new APIRequestInitializer();
    }

    @Bean
    public List<GenericRequestHandler<HandlerInput, Optional<Response>>> getRequestHandlers()
        throws InstantiationException, IllegalAccessException
    {
        final Reflections reflections = new Reflections("info.tomfi.alexa.skills.shabbattimes.request.handlers");
        final List<GenericRequestHandler<HandlerInput, Optional<Response>>> requestHandlers = new ArrayList<>();
        for (Class<? extends RequestHandler> currentHandler : reflections.getSubTypesOf(RequestHandler.class))
        {
            requestHandlers.add(currentHandler.newInstance());
        }
        return requestHandlers;
    }

    @Bean
    public List<GenericExceptionHandler<HandlerInput, Optional<Response>>> getExceptionHandlers()
        throws InstantiationException, IllegalAccessException
    {
        final Reflections reflections = new Reflections("info.tomfi.alexa.skills.shabbattimes.exception.handlers");
        final List<GenericExceptionHandler<HandlerInput, Optional<Response>>> exceptionHandlers = new ArrayList<>();
        for (Class<? extends ExceptionHandler> currentHandler : reflections.getSubTypesOf(ExceptionHandler.class))
        {
            exceptionHandlers.add(currentHandler.newInstance());
        }
        return exceptionHandlers;
    }

    @Bean
    public List<GenericRequestInterceptor<HandlerInput>> getRequestInterceptors()
        throws InstantiationException, IllegalAccessException
    {
        final Reflections reflections = new Reflections("info.tomfi.alexa.skills.shabbattimes.request.interceptors");
        final List<GenericRequestInterceptor<HandlerInput>> requestInterceptors = new ArrayList<>();
        for (Class<? extends RequestInterceptor> currentHandler : reflections.getSubTypesOf(RequestInterceptor.class))
        {
            requestInterceptors.add(currentHandler.newInstance());
        }
        return requestInterceptors;
    }

    @Bean
    public List<GenericResponseInterceptor<HandlerInput, Optional<Response>>> getResponseInterceptors()
        throws InstantiationException, IllegalAccessException
    {
        final Reflections reflections = new Reflections("info.tomfi.alexa.skills.shabbattimes.response.interceptors");
        final List<GenericResponseInterceptor<HandlerInput, Optional<Response>>> responseInterceptors = new ArrayList<>();
        for (Class<? extends ResponseInterceptor> currentHandler : reflections.getSubTypesOf(ResponseInterceptor.class))
        {
            responseInterceptors.add(currentHandler.newInstance());
        }
        return responseInterceptors;
    }
}
