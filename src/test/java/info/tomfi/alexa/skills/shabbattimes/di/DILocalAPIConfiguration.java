package info.tomfi.alexa.skills.shabbattimes.di;

import com.google.api.client.http.GenericUrl;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Lazy;

@Lazy
@Configuration
@Import(DiProdConfiguration.class)
public class DILocalAPIConfiguration
{
    @Bean
    public GenericUrl getApiUrl()
    {
        return new GenericUrl("http://localhost:1234/shabbat");
    }
}
