package info.tomfi.alexa.skills.shabbattimes.tools;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;

import com.google.gson.GsonBuilder;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import info.tomfi.alexa.skills.shabbattimes.api.APIRequestMaker;
import info.tomfi.alexa.skills.shabbattimes.api.response.APIResponse;
import lombok.Cleanup;
import lombok.val;

@Lazy
@Configuration
@ComponentScan(basePackages = "info.tomfi.alexa.skills.shabbattimes")
public class DITestingConfiguration
{
    @Bean
    public APIRequestMaker getRequestMaker() throws IllegalStateException, IOException, URISyntaxException
    {
        @Cleanup val breader = Files.newBufferedReader(
            Paths.get(DITestingConfiguration.class.getClassLoader().getResource("api-responses/response_real.json").toURI())
        );

        val fakeResponse = new GsonBuilder().create().fromJson(breader, APIResponse.class);

        val mockedMaker = mock(APIRequestMaker.class);
        when(mockedMaker.setGeoId(anyInt())).thenReturn(mockedMaker);
        when(mockedMaker.setSpecificDate(any(LocalDate.class))).thenReturn(mockedMaker);
        when(mockedMaker.send()).thenReturn(fakeResponse);
        return mockedMaker;
    }
}
