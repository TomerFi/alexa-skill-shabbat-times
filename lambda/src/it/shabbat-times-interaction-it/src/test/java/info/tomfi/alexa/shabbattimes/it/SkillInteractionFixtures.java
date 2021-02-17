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

import com.amazon.ask.Skill;
import info.tomfi.alexa.shabbattimes.SkillConfig;
import java.io.IOException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/** Integration test fixtures to use for the various interaction test cases. */
public class SkillInteractionFixtures {
  private AnnotationConfigApplicationContext context;
  protected Skill sut;

  @BeforeEach
  void initializeFixtures() {
    context = new AnnotationConfigApplicationContext(SkillConfig.class);
    sut = context.getBean(Skill.class);
  }

  @AfterEach
  void cleanupFixtures() {
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
