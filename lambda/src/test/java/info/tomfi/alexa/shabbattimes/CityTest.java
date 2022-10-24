package info.tomfi.alexa.shabbattimes;

import static info.tomfi.alexa.shabbattimes.assertions.BDDAssertions.then;
import static nl.jqno.equalsverifier.Warning.INHERITED_DIRECTLY_FROM_OBJECT;
import static nl.jqno.equalsverifier.Warning.NULL_FIELDS;
import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.List;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/** Test cases for the City implementation. */
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

  @Test
  void value_city_to_string_implementation_should_yield_non_empty_string()
      throws JsonParseException, JsonMappingException, IOException {
    try (var json = getClass().getClassLoader().getResourceAsStream("cities/TST_City1.json")) {
      // create a country pojo
      var city = new ObjectMapper().readValue(json, City.class);
      // verify toString implementation
      assertThat(city.toString()).isNotBlank();
    }
  }
}
