package info.tomfi.alexa.skills.shabbattimes.di;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.gson.GsonBuilder;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Lazy;

import info.tomfi.alexa.skills.shabbattimes.api.ApiRequestMaker;
import info.tomfi.alexa.skills.shabbattimes.api.response.ApiResponse;

import lombok.Cleanup;
import lombok.val;

@Lazy
@Configuration
@Import(DiProdConfiguration.class)
public class DIMockAPIConfiguration
{
    @Bean
    public ApiRequestMaker getRequestMaker() throws IllegalStateException, IOException, URISyntaxException
    {
        @Cleanup val breader = Files.newBufferedReader(
            Paths.get(DIMockAPIConfiguration.class.getClassLoader().getResource("api-responses/response_real.json").toURI())
        );

        val fakeResponse = new GsonBuilder().create().fromJson(breader, ApiResponse.class);

        val mockedMaker = mock(ApiRequestMaker.class);
        when(mockedMaker.setGeoId(anyInt())).thenReturn(mockedMaker);
        when(mockedMaker.setSpecificDate(any(LocalDate.class))).thenReturn(mockedMaker);
        when(mockedMaker.send()).thenReturn(fakeResponse);
        return mockedMaker;
    }

    @Bean
    public GenericUrl getApiUrl()
    {
        return mock(GenericUrl.class);
    }

    @Bean
    public HttpTransport getTransport()
    {
        return mock(NetHttpTransport.class);
    }
}
