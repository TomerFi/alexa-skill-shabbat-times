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
package info.tomfi.alexa.shabbattimes;

import com.amazon.ask.Skill;
import com.amazon.ask.Skills;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.Response;
import com.amazon.ask.request.exception.handler.GenericExceptionHandler;
import com.amazon.ask.request.handler.GenericRequestHandler;
import com.amazon.ask.request.interceptor.GenericRequestInterceptor;
import com.amazon.ask.request.interceptor.GenericResponseInterceptor;
import info.tomfi.hebcal.shabbat.ShabbatAPI;
import java.util.List;
import java.util.Optional;
import java.util.ServiceLoader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/** Main dependency context configuration class. */
@Configuration
@Import(CountryBeans.class)
@ComponentScan(
    basePackages = {
      "info.tomfi.alexa.shabbattimes.handlers",
      "info.tomfi.alexa.shabbattimes.interceptors",
      "info.tomfi.alexa.shabbattimes.services"
    })
public class SkillConfig {
  public SkillConfig() {
    //
  }

  /**
   * Singleton typed bean for constructing and instantiating the Skill.
   *
   * @param requestHandlers the injected request handlers, typically
   *     info.tomfi.alexa.shabbattimes.handlers.intent/launch/session packages.
   * @param exceptionHandlers the injected exception handlers, typically
   *     info.tomfi.alexa.shabbattimes.handlers.exception package.
   * @param requestInterceptors the injected request interceptors, typically
   *     info.tomfi.alexa.shabbattimes.interceptors.request package.
   * @param responseInterceptors the injected request interceptors, typically
   *     info.tomfi.alexa.shabbattimes.interceptors.response package.
   * @return the Skill instance.
   */
  @Bean
  public Skill getSkill(
      final List<GenericRequestHandler<HandlerInput, Optional<Response>>> requestHandlers,
      final List<GenericExceptionHandler<HandlerInput, Optional<Response>>> exceptionHandlers,
      final List<GenericRequestInterceptor<HandlerInput>> requestInterceptors,
      final List<GenericResponseInterceptor<HandlerInput, Optional<Response>>>
          responseInterceptors) {
    return Skills.standard()
        .addRequestInterceptors(requestInterceptors)
        .addResponseInterceptors(responseInterceptors)
        .addRequestHandlers(requestHandlers)
        .addExceptionHandlers(exceptionHandlers)
        .build();
  }

  /**
   * Use the service loader to instantiate the ShabbatAPI service provider.
   *
   * @return the service provider instance.
   */
  @Bean
  public ShabbatAPI getShabbatAPI() {
    return ServiceLoader.load(ShabbatAPI.class).stream().findFirst().get().get();
  }
}
