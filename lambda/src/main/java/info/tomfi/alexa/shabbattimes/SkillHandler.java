/*
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
package info.tomfi.alexa.shabbattimes;

import com.amazon.ask.Skill;
import com.amazon.ask.SkillStreamHandler;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/** Main skill handler starting point extending SkillStreamHandler. */
public final class SkillHandler extends SkillStreamHandler {
  /**
   * Start the application context and retrieve the Skill from the context.
   *
   * @return the Skill instance.
   */
  private static Skill getSkill() {
    try (var context = new AnnotationConfigApplicationContext(SkillConfig.class)) {
      return context.getBean(Skill.class);
    }
  }

  public SkillHandler() {
    super(getSkill());
  }
}
