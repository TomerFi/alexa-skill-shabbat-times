package info.tomfi.alexa.shabbattimes.it;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.amazon.ask.Skill;
import com.fasterxml.jackson.databind.ObjectMapper;
import info.tomfi.alexa.shabbattimes.SkillConfig;
import info.tomfi.shabbat.APIRequest;
import info.tomfi.shabbat.APIResponse;
import info.tomfi.shabbat.ShabbatAPI;
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

  /**
   * Bean for mocking the ShabbatAPI for avoiding real calls.
   *
   * @return a ShabbatAPI mock stubbed for returning the fixed response for the date under test.
   */
  @Bean
  ShabbatAPI getShabbatAPI() {
    // mock api
    mockApi = mock(ShabbatAPI.class);
    // instantiate object mapper
    var mapper = new ObjectMapper();
    // stub mock api to return real api response
    when(mockApi.sendAsync(any(APIRequest.class))).thenAnswer(a -> {
      var resp = mapper.readValue(
          getClass().getClassLoader().getResourceAsStream(
            "responses/real_hebcal_api_response.json"),
          APIResponse.class);
      var cf = new CompletableFuture<APIResponse>();
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
