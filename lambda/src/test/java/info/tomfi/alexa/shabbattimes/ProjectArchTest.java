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

import static com.tngtech.archunit.core.domain.JavaModifier.FINAL;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.library.Architectures.layeredArchitecture;
import static com.tngtech.archunit.library.GeneralCodingRules.NO_CLASSES_SHOULD_ACCESS_STANDARD_STREAMS;
import static com.tngtech.archunit.library.GeneralCodingRules.NO_CLASSES_SHOULD_THROW_GENERIC_EXCEPTIONS;

import com.amazon.ask.dispatcher.exception.ExceptionHandler;
import com.amazon.ask.dispatcher.request.handler.impl.IntentRequestHandler;
import com.amazon.ask.dispatcher.request.handler.impl.LaunchRequestHandler;
import com.amazon.ask.dispatcher.request.handler.impl.SessionEndedRequestHandler;
import com.amazon.ask.dispatcher.request.interceptor.RequestInterceptor;
import com.amazon.ask.dispatcher.request.interceptor.ResponseInterceptor;
import com.amazon.ask.exception.AskSdkException;
import com.google.auto.value.AutoValue;
import com.tngtech.archunit.core.importer.ImportOption.DoNotIncludeTests;
import com.tngtech.archunit.core.importer.ImportOption.DoNotIncludePackageInfos;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.jupiter.api.Tag;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/** Project architecture rules. */
@Tag("arch-tests")
@AnalyzeClasses(
    packages = "info.tomfi.alexa.shabbattimes",
    importOptions = {DoNotIncludeTests.class, DoNotIncludePackageInfos.class})
// CHECKSTYLE.OFF: VisibilityModifier
class ProjectArchTest {
  @ArchTest
  final ArchRule no_classes_should_access_standard_streams =
      NO_CLASSES_SHOULD_ACCESS_STANDARD_STREAMS;

  @ArchTest
  final ArchRule no_classes_should_throw_generic_exceptions =
      NO_CLASSES_SHOULD_THROW_GENERIC_EXCEPTIONS;

  @ArchTest
  final ArchRule exceptions_should_only_be_thrown_by_handlers_and_services =
      classes().that().resideInAPackage("..shabbattimes.exceptions..")
          .should().onlyBeAccessed().byClassesThat()
              .resideInAnyPackage("..shabbattimes.handlers..", "..shabbattimes.services..")
          .as("exceptions thrown are eventully cought and handled by the exception handlers");

  @ArchTest
  final ArchRule exceptions_should_be_public_final_and_extend_the_ask_sdk_exception =
      classes().that().resideInAPackage("..shabbattimes.exceptions..")
          .should().bePublic()
          .andShould().haveModifier(FINAL)
          .andShould().beAssignableTo(AskSdkException.class)
          .as("exception handling is mostly performed by the api");

  @ArchTest
  final ArchRule handlers_interceptors_and_services_should_be_marked_as_di_components =
      classes().that().resideInAnyPackage(
        "..shabbattimes.handlers..", "..shabbattimes.interceptors..", "..shabbattimes.services..")
          .should().beAnnotatedWith(Component.class)
          .as("loose coupling requires the components to be picked up by the di context");

  @ArchTest
  final ArchRule handlers_interceptors_and_services_should_not_be_manually_accessed =
      layeredArchitecture()
          .layer("Handlers").definedBy("..shabbattimes.handlers..")
          .layer("Interceptors").definedBy("..shabbattimes.interceptors..")
          .layer("Services").definedBy("..shabbattimes.services..")
          .layer("All").definedBy("..shabbattimes..")
          .whereLayer("Handlers").mayNotBeAccessedByAnyLayer()
          .whereLayer("Interceptors").mayNotBeAccessedByAnyLayer()
          .whereLayer("Services").mayNotBeAccessedByAnyLayer()
          .because("loose coupling requires only the di context to pick up the comonents");

  @ArchTest
  final ArchRule exception_handlers_should_be_public_final_and_implement_the_interface =
      classes().that().resideInAPackage("..shabbattimes.handlers.exception..")
          .should().bePublic()
          .andShould().haveModifier(FINAL)
          .andShould().implement(ExceptionHandler.class)
          .as("exception handlers should be accepted by the api");

  @ArchTest
  final ArchRule intent_handlers_should_be_public_final_and_implement_the_interface =
      classes().that().resideInAPackage("..shabbattimes.handlers.intent..")
          .should().bePublic()
          .andShould().haveModifier(FINAL)
          .andShould().implement(IntentRequestHandler.class)
          .as("intent request handlers should be accepted by the api");

  @ArchTest
  final ArchRule launch_handlers_should_be_public_final_and_implement_the_interface =
      classes().that().resideInAPackage("..shabbattimes.handlers.launch..")
          .should().bePublic()
          .andShould().haveModifier(FINAL)
          .andShould().implement(LaunchRequestHandler.class)
          .as("launch request handlers should be accepted by the api");

  @ArchTest
  final ArchRule session_ended_handlers_should_be_public_final_and_implemnt_the_intrfce =
      classes().that().resideInAPackage("..shabbattimes.handlers.session..")
          .should().bePublic()
          .andShould().haveModifier(FINAL)
          .andShould().implement(SessionEndedRequestHandler.class)
          .as("session ended request handlers should be accepted by the api");

  @ArchTest
  final ArchRule request_interceptors_should_be_public_final_and_implement_the_interface =
      classes().that().resideInAPackage("..shabbattimes.interceptors.request..")
          .should().bePublic()
          .andShould().haveModifier(FINAL)
          .andShould().implement(RequestInterceptor.class)
          .as("request interceptors should be accepted by the api");

  @ArchTest
  final ArchRule response_intercptors_should_be_public_final_and_implement_the_interface =
      classes().that().resideInAPackage("..shabbattimes.interceptors.response..")
          .should().bePublic()
          .andShould().haveModifier(FINAL)
          .andShould().implement(ResponseInterceptor.class)
          .as("response interceptors should be accepted by the api");

  @ArchTest
  final ArchRule services_should_be_public_final =
      classes().that().resideInAPackage("..shabbattimes.services..")
          .should().bePublic()
          .andShould().haveModifier(FINAL)
          .as("concrete services does not need to be extended");

  @ArchTest
  final ArchRule main_package_should_only_contain_interfaces_enums_builders_and_utility_classes =
      classes().that().resideInAPackage("..shabbattimes")
          .and().haveSimpleNameNotStartingWith("AutoValue_")
          .and().doNotHaveSimpleName("Builder")
          .should().beInterfaces()
          .orShould().beEnums()
          .orShould().haveOnlyFinalFields()
          .orShould().beAnnotatedWith(AutoValue.class)
          .orShould().beAnnotatedWith(Configuration.class)
          .as("layered architecture requires classes to be placed in the appropriate package");
}
