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

import static org.springframework.core.Ordered.LOWEST_PRECEDENCE;

import info.tomfi.shabbattimes.skill.ShabbatTimesSkillCreator;
import info.tomfi.hebcal.shabbat.ShabbatAPI;
import java.util.ServiceLoader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.annotation.Order;

/**
 * Main Spring-Context dependency injection annotated configuration class.
 *
 * <p>This configuration class takes the lowest precedence for multiple configuration classes.
 * Component scaning is based on:
 *
 * <ul>
 *   <li>info.tomfi.shabbattimes.skill.exception.handlers
 *   <li>info.tomfi.shabbattimes.skill.request.handlers
 *   <li>info.tomfi.shabbattimes.skill.request.interceptors
 *   <li>info.tomfi.shabbattimes.skill.response.interceptors
 * </ul>
 *
 * @author Tomer Figenblat {@literal <tomer.figenblat@gmail.com>}
 */
@Lazy
@Configuration
@ComponentScan(
    basePackages = {
      "info.tomfi.shabbattimes.skill.exception.handlers",
      "info.tomfi.shabbattimes.skill.request.handlers",
      "info.tomfi.shabbattimes.skill.request.interceptors",
      "info.tomfi.shabbattimes.skill.response.interceptors"
    })
@Order(LOWEST_PRECEDENCE)
public class DiProdConfiguration {
  public DiProdConfiguration() {
    //
  }

  @Bean
  public ShabbatTimesSkillCreator getShabbatTimesSkillCreator() {
    return new ShabbatTimesSkillCreator();
  }

  @Bean
  public ShabbatAPI getShabbatAPI() {
    return ServiceLoader.load(ShabbatAPI.class).stream().findFirst().get().get();
  }
}
