package info.tomfi.alexa.skills.shabbattimes.di;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDate;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.annotation.Order;

import info.tomfi.alexa.skills.shabbattimes.api.ApiRequestMaker;

import lombok.val;

@Lazy
@Configuration
@Import(DIMockAPIConfiguration.class)
@Order(HIGHEST_PRECEDENCE)
public class DIBreakAPIConfiguration
{
    @Bean
    public ApiRequestMaker getRequestMaker() throws IllegalStateException, IOException, URISyntaxException
    {
        val mockedMaker = mock(ApiRequestMaker.class);
        when(mockedMaker.setGeoId(anyInt())).thenReturn(mockedMaker);
        when(mockedMaker.setSpecificDate(any(LocalDate.class))).thenReturn(mockedMaker);
        when(mockedMaker.send()).thenThrow(new IOException("mocking exception throwing"));
        return mockedMaker;
    }
}
