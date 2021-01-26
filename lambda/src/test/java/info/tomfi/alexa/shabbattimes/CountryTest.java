package info.tomfi.alexa.shabbattimes;

import static info.tomfi.alexa.shabbattimes.assertions.BDDAssertions.then;
import static nl.jqno.equalsverifier.Warning.INHERITED_DIRECTLY_FROM_OBJECT;
import static nl.jqno.equalsverifier.Warning.NULL_FIELDS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.List;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
final class CountryTest {
  @Test
  void verify_country_object_equals_and_hashcode_implementations() {
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
  void build_a_country_object_using_the_builder_and_verify_the_value(
      @Mock final City mockCity1, @Mock final City mockCity2) {
    // stub the mocked citis with a fake name and alias
    given(mockCity1.cityName()).willReturn("city1");
    given(mockCity1.aliases()).willReturn(List.of("city1alias"));
    given(mockCity2.cityName()).willReturn("city2");
    // build the country using the builder
    var country =
        Country.builder()
            .abbreviation("AB")
            .cities(List.of(mockCity1, mockCity2))
            .name("name")
            .bundleKey(BundleKey.NOT_FOUND_IN_ISRAEL)
            .utterances(List.of("utter1", "utter2"))
            .build();
    // verify the pojo
    then(country)
        .abbreviationIs("AB")
        .nameIs("name")
        .stringCitiesIs("city1, city2")
        .bundleKeyIs(BundleKey.NOT_FOUND_IN_ISRAEL)
        .utterancesAre(List.of("utter1", "utter2"));
    // verify the getCity functionality
    assertThat(country.getCity("city1alias")).isNotEmpty().hasValue(mockCity1);
    assertThat(country.getCity("city2alias")).isEmpty();
    // verify the hasUtterance functionality
    assertThat(country.hasUtterance("utter1")).isTrue();
    assertThat(country.hasUtterance("utter3")).isFalse();
  }
}
