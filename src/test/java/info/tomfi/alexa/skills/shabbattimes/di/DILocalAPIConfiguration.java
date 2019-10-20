package info.tomfi.alexa.skills.shabbattimes.di;

import com.google.api.client.http.GenericUrl;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Lazy;

@Lazy
@Configuration
@Import(DIProdConfiguration.class)
@ComponentScan(basePackages = {
    "info.tomfi.alexa.skills.shabbattimes.api",
    "info.tomfi.alexa.skills.shabbattimes.request.handlers"
})
public class DILocalAPIConfiguration
{
    @Bean
    public GenericUrl getApiUrl()
    {
        return new GenericUrl("http://localhost:1234/shabbat");
    }
}
