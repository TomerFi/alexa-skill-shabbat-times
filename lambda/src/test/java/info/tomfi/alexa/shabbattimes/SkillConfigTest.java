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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.mockStatic;
import static org.mockito.BDDMockito.then;

import com.amazon.ask.Skill;
import com.amazon.ask.Skills;
import com.amazon.ask.builder.StandardSkillBuilder;
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
import java.util.ServiceLoader.Provider;
import java.util.stream.Stream;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/** Context dependency configuration test cases. */
@ExtendWith(MockitoExtension.class)
@Tag("unit-tests")
final class SkillConfigTest {
  @InjectMocks private SkillConfig sut;

  @Test
  void instantiating_the_skill_implementation_should_invoke_the_static_skills_factory(
      @Mock final GenericRequestHandler<HandlerInput, Optional<Response>> requestHandler,
      @Mock final GenericExceptionHandler<HandlerInput, Optional<Response>> exceptionHandler,
      @Mock final GenericRequestInterceptor<HandlerInput> requestInterceptor,
      @Mock final GenericResponseInterceptor<HandlerInput, Optional<Response>> responseInterceptor,
      @Mock final StandardSkillBuilder skillBuilder,
      @Mock final Skill expectedSkill) {
    // stub the skill builder steps with the builder mock
    given(skillBuilder.addRequestHandlers(eq(List.of(requestHandler)))).willReturn(skillBuilder);
    given(skillBuilder.addResponseInterceptors(eq(List.of(responseInterceptor))))
        .willReturn(skillBuilder);
    given(skillBuilder.addRequestInterceptors(eq(List.of(requestInterceptor))))
        .willReturn(skillBuilder);
    given(skillBuilder.addExceptionHandlers(eq(List.of(exceptionHandler))))
        .willReturn(skillBuilder);
    // stub the skill builder build with the mocked skill
    given(skillBuilder.build()).willReturn(expectedSkill);
    // mock Skills class
    try (var skillsMock = mockStatic(Skills.class)) {
      // stub static standard factory with mocked builder
      skillsMock.when(Skills::standard).thenReturn(skillBuilder);
      // retrive the skill from the sut
      var skill =
          sut.getSkill(
              List.of(requestHandler),
              List.of(exceptionHandler),
              List.of(requestInterceptor),
              List.of(responseInterceptor));
      // validate returning skill
      assertThat(skill).isEqualTo(expectedSkill);
    }

    // verify correct builder steps were invoked
    then(skillBuilder).should().addRequestHandlers(eq(List.of(requestHandler)));
    then(skillBuilder).should().addResponseInterceptors(eq(List.of(responseInterceptor)));
    then(skillBuilder).should().addRequestInterceptors(eq(List.of(requestInterceptor)));
    then(skillBuilder).should().addExceptionHandlers(eq(List.of(exceptionHandler)));
    then(skillBuilder).should().build();
    then(skillBuilder).shouldHaveNoMoreInteractions();
  }

  @Test
  void instantiating_the_api_implementation_should_invoke_the_service_loader_api(
      @Mock final ServiceLoader<ShabbatAPI> apiLoader,
      @Mock final Provider<ShabbatAPI> apiProvider,
      @Mock final ShabbatAPI expectedApi) {
    // stub the service provider the the mocked api
    given(apiProvider.get()).willReturn(expectedApi);
    // stub the service loader with the mock provider
    given(apiLoader.stream()).willReturn(Stream.of(apiProvider));
    // mock ServiceLoader class
    try (var loaderMock = mockStatic(ServiceLoader.class)) {
      // stub static load factory with mocked service loader
      loaderMock.when(() -> ServiceLoader.load(eq(ShabbatAPI.class))).thenReturn(apiLoader);
      // retrieving the api from the sut yields the mocked api
      assertThat(sut.getShabbatAPI()).isEqualTo(expectedApi);
    }
  }
}
