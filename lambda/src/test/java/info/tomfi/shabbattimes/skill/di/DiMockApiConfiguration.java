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
package info.tomfi.shabbattimes.skill.di;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import info.tomfi.hebcal.shabbat.ShabbatAPI;
import info.tomfi.hebcal.shabbat.request.Request;
import info.tomfi.hebcal.shabbat.response.Response;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.CompletableFuture;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Lazy;

@Lazy
@Configuration
@Import(DiProdConfiguration.class)
public class DiMockApiConfiguration {
  @Bean
  public ShabbatAPI getShabbatAPI()
      throws IllegalStateException, IOException, URISyntaxException {
    try (final BufferedReader breader =
        Files.newBufferedReader(
            Paths.get(
                Thread.currentThread()
                    .getContextClassLoader()
                    .getResource("api-responses/response_real.json")
                    .toURI()))) {
                      var fakeResponse = new ObjectMapper().readValue(breader, Response.class);
                      var future = new CompletableFuture<Response>();
                      future.complete(fakeResponse);

                      final ShabbatAPI mockAPI = mock(ShabbatAPI.class);
                      when(mockAPI.sendAsync(any(Request.class))).thenReturn(future);
                      return mockAPI;
                    }
  }
}
