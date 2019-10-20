package info.tomfi.alexa.skills.shabbattimes.di;

import static org.springframework.core.Ordered.LOWEST_PRECEDENCE;

import java.io.IOException;
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
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.gson.GsonFactory;

import org.reflections.Reflections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.annotation.Order;

import info.tomfi.alexa.skills.shabbattimes.ShabbatTimesSkillCreator;
import info.tomfi.alexa.skills.shabbattimes.api.APIRequestMaker;

import lombok.val;

@Lazy
@Configuration
@ComponentScan(basePackages = {
    "info.tomfi.alexa.skills.shabbattimes.api",
    "info.tomfi.alexa.skills.shabbattimes.request.handlers"
})
@Order(LOWEST_PRECEDENCE)
public class DIProdConfiguration
{
    @Bean ShabbatTimesSkillCreator getShabbatTimesSkillCreator()
    {
        return new ShabbatTimesSkillCreator();
    }

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
        return new HttpRequestInitializer()
        {
            @Override
            public void initialize(final HttpRequest request) throws IOException
            {
                request.setParser(new JsonObjectParser(new GsonFactory()));
            }
        };
    }

    @Bean
    public List<GenericRequestHandler<HandlerInput, Optional<Response>>> getRequestHandlers()
        throws InstantiationException, IllegalAccessException
    {
        val reflections = new Reflections("info.tomfi.alexa.skills.shabbattimes.request.handlers");
        val requestHandlers = new ArrayList<GenericRequestHandler<HandlerInput, Optional<Response>>>();
        for (val currentHandler : reflections.getSubTypesOf(RequestHandler.class))
        {
            requestHandlers.add(currentHandler.newInstance());
        }
        return requestHandlers;
    }

    @Bean
    public List<GenericExceptionHandler<HandlerInput, Optional<Response>>> getExceptionHandlers()
        throws InstantiationException, IllegalAccessException
    {
        val reflections = new Reflections("info.tomfi.alexa.skills.shabbattimes.exception.handlers");
        val exceptionHandlers = new ArrayList<GenericExceptionHandler<HandlerInput, Optional<Response>>>();
        for (val currentHandler : reflections.getSubTypesOf(ExceptionHandler.class))
        {
            exceptionHandlers.add(currentHandler.newInstance());
        }
        return exceptionHandlers;
    }

    @Bean
    public List<GenericRequestInterceptor<HandlerInput>> getRequestInterceptors()
        throws InstantiationException, IllegalAccessException
    {
        val reflections = new Reflections("info.tomfi.alexa.skills.shabbattimes.request.interceptors");
        val requestInterceptors = new ArrayList<GenericRequestInterceptor<HandlerInput>>();
        for (val currentHandler : reflections.getSubTypesOf(RequestInterceptor.class))
        {
            requestInterceptors.add(currentHandler.newInstance());
        }
        return requestInterceptors;
    }

    @Bean
    public List<GenericResponseInterceptor<HandlerInput, Optional<Response>>> getResponseInterceptors()
        throws InstantiationException, IllegalAccessException
    {
        val reflections = new Reflections("info.tomfi.alexa.skills.shabbattimes.response.interceptors");
        val responseInterceptors = new ArrayList<GenericResponseInterceptor<HandlerInput, Optional<Response>>>();
        for (val currentHandler : reflections.getSubTypesOf(ResponseInterceptor.class))
        {
            responseInterceptors.add(currentHandler.newInstance());
        }
        return responseInterceptors;
    }
}
