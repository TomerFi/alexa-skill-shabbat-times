package info.tomfi.alexa.skills.shabbattimes.tools;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;

import com.google.gson.GsonBuilder;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import info.tomfi.alexa.skills.shabbattimes.api.APIRequestMaker;
import info.tomfi.alexa.skills.shabbattimes.api.response.APIResponse;

@Configuration
@ComponentScan(basePackages = "info.tomfi.alexa.skills.shabbattimes")
public class DITestingConfiguration
{
    @Bean
    public APIRequestMaker getAPIRequestMaker() throws IllegalStateException, IOException, URISyntaxException
    {
        APIResponse fakeResponse;
        try (
            BufferedReader breader = Files.newBufferedReader(
                Paths.get(DITestingConfiguration.class.getClassLoader().getResource("api-responses/response_real.json").toURI())
            )
        )
        {
            fakeResponse = new GsonBuilder().create().fromJson(breader, APIResponse.class);
        }

        final APIRequestMaker mockedMaker = mock(APIRequestMaker.class);
        when(mockedMaker.setGeoId(anyInt())).thenReturn(mockedMaker);
        when(mockedMaker.setSpecificDate(any(LocalDate.class))).thenReturn(mockedMaker);
        when(mockedMaker.send()).thenReturn(fakeResponse);
        return mockedMaker;
    }
}
