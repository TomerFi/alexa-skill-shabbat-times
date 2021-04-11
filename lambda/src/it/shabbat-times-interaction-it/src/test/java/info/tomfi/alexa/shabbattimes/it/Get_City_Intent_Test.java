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
package info.tomfi.alexa.shabbattimes.it;

import static info.tomfi.alexa.skillstester.SkillsTester.givenSkill;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import java.io.IOException;
import java.util.stream.Stream;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * Integration test cases for the get city intent.
 *
 * <p>Please note: Most of the test cases here are based upon Jerusalem, Israel on
 * 10/01/2019 - 10/04/2019.</p>
 */
// CHECKSTYLE.OFF: LambdaBodyLength
@Tag("integration-tests")
final class Get_City_Intent_Test extends SkillInteractionFixtures {
  static Stream<Arguments> path_day_speech_matrix_provider() {
    return Stream.of("with_country", "without_country")
        .flatMap(path -> Stream.of(
          arguments(
            path,
            "tuesday",
            "Shabbat times in Jerusalem: starts on friday. 2019-10-04, at 17:40. "
                + "and ends on saturday. 2019-10-05, at 19:10."
                + "Would you like me to get the Shabbat times in another city?"),
          arguments(
            path,
            "thursday",
            "Shabbat times in Jerusalem: starts tomorrow. 2019-10-04, at 17:40. "
                + "and ends on saturday. 2019-10-05, at 19:10."
                      + "Would you like me to get the Shabbat times in another city?"),
          arguments(
            path,
            "friday",
            "Shabbat times in Jerusalem: starts today. 2019-10-04, at 17:40. "
                + "and ends tomorrow. 2019-10-05, at 19:10."
                + "Would you like me to get the Shabbat times in another city?"),
          arguments(
            path,
            "saturday",
            "Shabbat times in Jerusalem: started yesterday. 2019-10-04, at 17:40. "
                + "and ends today. 2019-10-05, at 19:10."
                + "Would you like me to get the Shabbat times in another city?"))
        );
  }

  @ParameterizedTest
  @MethodSource(value = "path_day_speech_matrix_provider")
  void requesting_info_about_a_known_city_in_il_should_play_the_info_and_deliver_a_card(
      final String path, final String day, final String speech) throws IOException {
    givenSkill(sut)
        .whenRequestIs(
          getResource("requests/get_city/" + path + "/il/" + day + "/get_city_intent.json"))
        .thenResponseShould()
            .waitForFollowup()
            .haveOutputSpeechOf(speech)
            .haveRepromptSpeechOf("If you're interested in another city, please tell me the city "
                + "name. For a list of all the possible city names, just ask me for help.")
            .haveCardTitleOf("Shabbat times: IL-Jerusalem")
            .haveCardTextOf("Starts on 2019-10-04, at 17:40. And ends on 2019-10-05, at 19:10.");
  }

  @ParameterizedTest
  @ValueSource(strings = {"with_country", "without_country"})
  void requesting_information_about_an_unknown_city_should_ask_for_another_city_and_wait(
      final String filepath) throws IOException {
    givenSkill(sut)
        .whenRequestIs(
          getResource("requests/get_city/" + filepath + "/unknown/get_city_intent.json"))
        .thenResponseShould()
            .waitForFollowup()
            .haveOutputSpeechOf("I'm sorry. I can't seem to find your requested city. "
                + "Please repeat the city name. "
                + "For a list of all the possible city names, just ask me for help.")
            .haveRepromptSpeechOf("Please tell me the requested city name. "
                + "For a list of all the possible city names, just ask me for help.");
  }
}
