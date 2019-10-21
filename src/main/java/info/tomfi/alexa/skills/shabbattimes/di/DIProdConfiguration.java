package info.tomfi.alexa.skills.shabbattimes.di;

import static org.springframework.core.Ordered.LOWEST_PRECEDENCE;

import java.io.IOException;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.gson.GsonFactory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.annotation.Order;

import info.tomfi.alexa.skills.shabbattimes.ShabbatTimesSkillCreator;
import info.tomfi.alexa.skills.shabbattimes.api.APIRequestMaker;

@Lazy
@Configuration
@ComponentScan(basePackages = {
    "info.tomfi.alexa.skills.shabbattimes.exception.handlers",
    "info.tomfi.alexa.skills.shabbattimes.request.handlers",
    "info.tomfi.alexa.skills.shabbattimes.request.interceptors",
    "info.tomfi.alexa.skills.shabbattimes.response.interceptors"
})
@Order(LOWEST_PRECEDENCE)
public class DIProdConfiguration
{
    @Bean
    public ShabbatTimesSkillCreator getShabbatTimesSkillCreator()
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
}
