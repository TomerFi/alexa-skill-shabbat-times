package info.tomfi.alexa.skills.shabbattimes.tools;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import info.tomfi.alexa.skills.shabbattimes.api.APIRequestMaker;

@Lazy
@Configuration
@ComponentScan(basePackages = "info.tomfi.alexa.skills.shabbattimes")
public class DIConfiguration
{
    @Bean
    public APIRequestMaker getAPIRequestMaker()
    {
        return new APIRequestMaker("https://www.hebcal.com/shabbat/");
    }
}
