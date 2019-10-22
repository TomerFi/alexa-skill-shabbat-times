package info.tomfi.alexa.skills.shabbattimes.di;

import static org.springframework.core.Ordered.LOWEST_PRECEDENCE;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.gson.GsonFactory;

import info.tomfi.alexa.skills.shabbattimes.ShabbatTimesSkillCreator;
import info.tomfi.alexa.skills.shabbattimes.api.ApiRequestMaker;

import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.annotation.Order;

/**
 * Main Spring-Context dependency injection annotated configuration class.
 *
 * This configuration class takes the lowest precedence for multiple configuration classes.
 * Component scaning is based on:
 * <ul>
 *     <li>info.tomfi.alexa.skills.shabbattimes.exception.handlers</li>
 *     <li>info.tomfi.alexa.skills.shabbattimes.request.handlers</li>
 *     <li>info.tomfi.alexa.skills.shabbattimes.request.interceptors</li>
 *     <li>info.tomfi.alexa.skills.shabbattimes.response.interceptors</li>
 * </ul>
 *
 * @author Tomer Figenblat {@literal <tomer.figenblat@gmail.com>}
 */
@Lazy
@Configuration
@ComponentScan(basePackages = {
    "info.tomfi.alexa.skills.shabbattimes.exception.handlers",
    "info.tomfi.alexa.skills.shabbattimes.request.handlers",
    "info.tomfi.alexa.skills.shabbattimes.request.interceptors",
    "info.tomfi.alexa.skills.shabbattimes.response.interceptors"
})
@Order(LOWEST_PRECEDENCE)
public class DiProdConfiguration
{
    @Bean
    public ShabbatTimesSkillCreator getShabbatTimesSkillCreator()
    {
        return new ShabbatTimesSkillCreator();
    }

    @Bean
    public ApiRequestMaker getRequestMaker()
    {
        return new ApiRequestMaker();
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
}
