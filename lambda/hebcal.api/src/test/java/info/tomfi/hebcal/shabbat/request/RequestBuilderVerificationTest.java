package info.tomfi.hebcal.shabbat.request;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

import java.time.LocalDate;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@Tag("unit-tests")
final class RequestBuilderVerificationTest {
  @Test
  void building_a_request_without_setting_the_geoid_throws_an_IllegalStateException() {
    assertThatExceptionOfType(IllegalStateException.class)
        .isThrownBy(() -> Request.builder().build())
        .withMessage("geo id is mandatory for this request");
  }

  @ParameterizedTest
  @ValueSource(ints = {-1, 0})
  void building_a_request_with_a_non_positive_geoid_throws_an_IllegalArgumentException(final int geoId) {
    assertThatExceptionOfType(IllegalArgumentException.class)
        .isThrownBy(() -> Request.builder().forGeoId(geoId))
        .withMessage("geo id should be a positive integer");
  }

  @Test
  void building_a_request_with_a_negative_minutes_before_sunset_throws_an_IllegalArgumentException() {
    assertThatExceptionOfType(IllegalArgumentException.class)
        .isThrownBy(() -> Request.builder().withMinutesBeforeSunset(-1))
        .withMessage("minutes before sunset should be a non negative integer");
  }

  @ParameterizedTest
  @ValueSource(ints = {-1, 0})
  void building_a_request_with_a_non_positive_minutes_after_sundown_throws_an_IllegalArgumentException(final int minutes) {
    assertThatExceptionOfType(IllegalArgumentException.class)
        .isThrownBy(() -> Request.builder().withMinutesAfterSundown(minutes))
        .withMessage("minutes after sundown should be a positive integer");
  }

  @Test
  void building_a_request_with_a_null_date_throws_a_NullPointerException() {
    assertThatNullPointerException()
        .isThrownBy(() -> Request.builder().forDate(null).build())
        .withMessage("Null dateTime");
  }

  @Test
  void building_a_request_with_legal_arguments_does_not_throw_exceptions() {
    assertThatNoException().isThrownBy(() ->
        Request.builder()
            .forGeoId(12345)
            .forDate(LocalDate.now())
            .withMinutesBeforeSunset(0)
            .withMinutesAfterSundown(10)
            .build());
  }
}
