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

import info.tomfi.hebcal.shabbat.ShabbatAPI;
import java.lang.reflect.InvocationTargetException;
import java.util.ServiceLoader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Lazy;

@Lazy
@Configuration
@Import(DiProdConfiguration.class)
public class DiLocalApiConfiguration {
  @Bean
  public ShabbatAPI getShabbatAPI() throws InstantiationException, IllegalAccessException, IllegalArgumentException,
      InvocationTargetException, NoSuchMethodException, SecurityException {
    var apiType = ServiceLoader.load(ShabbatAPI.class).stream().findFirst().get().type();
    return apiType.getDeclaredConstructor(String.class).newInstance("http://localhost:1234/shabbat");
  }
}