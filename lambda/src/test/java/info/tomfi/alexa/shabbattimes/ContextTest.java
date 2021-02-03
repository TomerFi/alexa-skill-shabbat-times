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

import static org.assertj.core.api.Assertions.assertThat;

import com.amazon.ask.Skill;
import com.amazon.ask.request.exception.handler.GenericExceptionHandler;
import com.amazon.ask.request.handler.GenericRequestHandler;
import com.amazon.ask.request.interceptor.GenericRequestInterceptor;
import com.amazon.ask.request.interceptor.GenericResponseInterceptor;
import info.tomfi.hebcal.shabbat.ShabbatAPI;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/** Test the context life cycle for the various beans. */
@Tag("unit-tests")
final class ContextTest {
  private static AnnotationConfigApplicationContext context;

  @BeforeAll
  static void intializeContext() {
    context = new AnnotationConfigApplicationContext(SkillConfig.class);
  }

  @AfterAll
  static void cleanupContext() {
    context.close();
  }

  @Test
  void verify_skill_bean_is_instantiated() {
    assertThat(context.getBean(Skill.class)).isNotNull();
  }

  @Test
  void verify_shabbat_api_is_instantiated() {
    assertThat(context.getBean(ShabbatAPI.class)).isNotNull();
  }

  @Test
  void verify_request_handlers_are_instantiated() {
    var intntHndlrs =
        new Reflections("info.tomfi.alexa.shabbattimes.handlers.intent")
            .getTypesAnnotatedWith(Component.class);
    var sessHndlrs =
        new Reflections("info.tomfi.alexa.shabbattimes.handlers.session")
            .getTypesAnnotatedWith(Component.class);
    var lnchHndlrs =
        new Reflections("info.tomfi.alexa.shabbattimes.handlers.launch")
            .getTypesAnnotatedWith(Component.class);
    assertThat(context.getBeansOfType(GenericRequestHandler.class))
        .size()
        .isEqualTo(intntHndlrs.size() + sessHndlrs.size() + lnchHndlrs.size());
  }

  @Test
  void verify_exception_handlers_are_instantiated() {
    var excHndlrs =
        new Reflections("info.tomfi.alexa.shabbattimes.handlers.exception")
            .getTypesAnnotatedWith(Component.class);
    assertThat(context.getBeansOfType(GenericExceptionHandler.class))
        .size()
        .isEqualTo(excHndlrs.size());
  }

  @Test
  void verify_request_interceptors_are_instantiated() {
    var reqIntcptHndlrs =
        new Reflections("info.tomfi.alexa.shabbattimes.interceptors.request")
            .getTypesAnnotatedWith(Component.class);
    assertThat(context.getBeansOfType(GenericRequestInterceptor.class))
        .size()
        .isEqualTo(reqIntcptHndlrs.size());
  }

  @Test
  void verify_response_interceptors_are_instantiated() {
    var respIntcptHndlrs =
        new Reflections("info.tomfi.alexa.shabbattimes.interceptors.response")
            .getTypesAnnotatedWith(Component.class);
    assertThat(context.getBeansOfType(GenericResponseInterceptor.class))
        .size()
        .isEqualTo(respIntcptHndlrs.size());
  }

  @Test
  void verify_loader_service_is_instantiated() {
    assertThat(context.getBean(LoaderService.class)).isNotNull();
  }

  @Test
  void verify_locator_service_is_instantiated() {
    assertThat(context.getBean(LocatorService.class)).isNotNull();
  }

  @Test
  void verify_text_service_is_instantiated() {
    assertThat(context.getBean(TextService.class)).isNotNull();
  }

  @Test
  void verify_country_beans() {
    var reflections =
        new Reflections("info.tomfi.alexa.shabbattimes", new MethodAnnotationsScanner());
    var beanMethods = reflections.getMethodsAnnotatedWith(Bean.class);
    beanMethods.removeIf(m -> !m.getDeclaringClass().equals(CountryBeans.class));
    assertThat(context.getBeansOfType(Country.class)).size().isEqualTo(beanMethods.size());
  }
}
