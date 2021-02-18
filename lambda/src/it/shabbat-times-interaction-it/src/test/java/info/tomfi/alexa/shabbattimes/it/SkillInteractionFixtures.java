/**
 * Copyright Tomer Figenblat.
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
package info.tomfi.alexa.shabbattimes.it;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.amazon.ask.Skill;
import com.fasterxml.jackson.databind.ObjectMapper;
import info.tomfi.alexa.shabbattimes.SkillConfig;
import info.tomfi.hebcal.shabbat.ShabbatAPI;
import info.tomfi.hebcal.shabbat.request.Request;
import info.tomfi.hebcal.shabbat.response.Response;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/** Integration test fixtures to use for the various interaction test cases. */
@Configuration
@Import(SkillConfig.class)
public class SkillInteractionFixtures {
  private AnnotationConfigApplicationContext context;
  protected ShabbatAPI mockApi;
  protected Skill sut;

  @Bean // removing this bean annotation will make the tests send requests to hebcal's real api
  ShabbatAPI getShabbatAPI() {
    // mock api
    mockApi = mock(ShabbatAPI.class);
    // instantiate object mapper
    var mapper = new ObjectMapper();
    // stub mock api to return real hebcal api response
    when(mockApi.sendAsync(any(Request.class))).thenAnswer(a -> {
      var resp = mapper.readValue(
        getClass().getClassLoader().getResourceAsStream("responses/real_hebcal_api_response.json"),
        Response.class);
      var cf = new CompletableFuture<Response>();
      cf.complete(resp);
      return cf;
    });
    // return mocked api as the context bean
    return mockApi;
  }

  @BeforeEach
  void initializeFixtures() {
    // create the di context with a patched configuration
    context = new AnnotationConfigApplicationContext(SkillInteractionFixtures.class);
    // retrive the skill from the context
    sut = context.getBean(Skill.class);
  }

  @AfterEach
  void cleanupFixtures() {
    // close the di context
    context.close();
  }

  /**
   * Utility method for loading a resource file as byte array. Used to load the json request files.
   *
   * @param resource the file path and name to load.
   * @return the reprenstation of the file content in byte array format.
   * @throws IOException if an I/O error occurs reading from the file.
   */
  protected byte[] getResource(final String resource) throws IOException {
    return getClass().getClassLoader().getResourceAsStream(resource).readAllBytes();
  }
}
