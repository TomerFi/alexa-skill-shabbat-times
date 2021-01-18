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
package info.tomfi.shabbattimes.skill;

import com.amazon.ask.SkillStreamHandler;
import info.tomfi.shabbattimes.skill.di.DiProdConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Main enterance class to be invoked as the aws lambda function.
 *
 * <p>Spring context object is created here.
 *
 * @author Tomer Figenblat {@literal <tomer.figenblat@gmail.com>}
 */
public class ShabbatTimesStreamHandler extends SkillStreamHandler {
  private static ShabbatTimesSkillCreator getCreator() {
    AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DiProdConfiguration.class);
    try {
      return context.getBean(ShabbatTimesSkillCreator.class);
    } finally {
      context.close();
    }
  }

  /** Main constructor, invokes the skill creation and pass the skill to the super constructor. */
  public ShabbatTimesStreamHandler() {
    super(getCreator().getSkill());
  }
}
