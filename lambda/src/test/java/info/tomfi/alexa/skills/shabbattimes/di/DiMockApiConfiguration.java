/**
 * Copyright Tomer Figenblat
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package info.tomfi.alexa.skills.shabbattimes.di;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.gson.GsonBuilder;
import info.tomfi.alexa.skills.shabbattimes.api.ApiRequestMaker;
import info.tomfi.alexa.skills.shabbattimes.api.response.ApiResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Lazy;

@Lazy
@Configuration
@Import(DiProdConfiguration.class)
public class DiMockApiConfiguration {
  @Bean
  public ApiRequestMaker getRequestMaker()
      throws IllegalStateException, IOException, URISyntaxException {
    try (final BufferedReader breader =
        Files.newBufferedReader(
            Paths.get(
                Thread.currentThread()
                    .getContextClassLoader()
                    .getResource("api-responses/response_real.json")
                    .toURI()))) {
                      final ApiResponse fakeResponse = new GsonBuilder().create().fromJson(breader, ApiResponse.class);

                      final ApiRequestMaker mockedMaker = mock(ApiRequestMaker.class);
                      when(mockedMaker.setGeoId(anyInt())).thenReturn(mockedMaker);
                      when(mockedMaker.setSpecificDate(any(LocalDate.class))).thenReturn(mockedMaker);
                      when(mockedMaker.send()).thenReturn(fakeResponse);
                      return mockedMaker;
                    }
  }

  @Bean
  public GenericUrl getApiUrl() {
    return mock(GenericUrl.class);
  }

  @Bean
  public HttpTransport getTransport() {
    return mock(NetHttpTransport.class);
  }
}
