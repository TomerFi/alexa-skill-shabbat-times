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

import static info.tomfi.alexa.shabbattimes.assertions.BDDAssertions.then;
import static nl.jqno.equalsverifier.Warning.INHERITED_DIRECTLY_FROM_OBJECT;
import static nl.jqno.equalsverifier.Warning.NULL_FIELDS;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.List;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("unit-tests")
final class CityTest {
  @Test
  void verify_city_object_equals_and_hashcode_implementations() {
    // verify abstraction, not implementing equals and hashcode
    EqualsVerifier.forClass(City.class)
        .withRedefinedSubclass(AutoValue_City.class)
        .usingGetClass()
        .suppress(INHERITED_DIRECTLY_FROM_OBJECT)
        .verify();
    // verify implementation, not verifying nulls
    EqualsVerifier.forClass(AutoValue_City.class)
        .withRedefinedSuperclass()
        .suppress(NULL_FIELDS)
        .verify();
  }

  @Test
  void test_deserialization_of_json_to_city_pojo()
      throws JsonParseException, JsonMappingException, IOException {
    // load test city json file
    try (var json = getClass().getClassLoader().getResourceAsStream("cities/TST_City1.json")) {
      // map city json file to city pojo
      var city = new ObjectMapper().readValue(json, City.class);
      // verify pojo fields
      then(city)
          .cityNameIs("testCity1")
          .geoNameIs("TST-testCity1")
          .geoIdIs(1234567)
          .countryAbbreviationIs("TST")
          .aliasesAre(List.of("city1", "firstcity"));
    }
  }
}
