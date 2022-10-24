package info.tomfi.alexa.shabbattimes;

import static info.tomfi.alexa.shabbattimes.BundleKey.SHABBAT_END_SATURDAY;
import static info.tomfi.alexa.shabbattimes.BundleKey.SHABBAT_END_TODAY;
import static info.tomfi.alexa.shabbattimes.BundleKey.SHABBAT_END_TOMORROW;
import static info.tomfi.alexa.shabbattimes.BundleKey.SHABBAT_START_FRIDAY;
import static info.tomfi.alexa.shabbattimes.BundleKey.SHABBAT_START_TODAY;
import static info.tomfi.alexa.shabbattimes.BundleKey.SHABBAT_START_TOMORROW;
import static info.tomfi.alexa.shabbattimes.BundleKey.SHABBAT_START_YESTERDAY;
import static java.time.DayOfWeek.FRIDAY;
import static java.time.DayOfWeek.SATURDAY;
import static java.time.DayOfWeek.THURSDAY;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.BDDAssertions.assertThat;
import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenExceptionOfType;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.mock;

import com.amazon.ask.model.Slot;
import info.tomfi.alexa.shabbattimes.SlotName.CitySlot;
import info.tomfi.alexa.shabbattimes.exceptions.NoCitySlotException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/** Test cases for the static tool in Tools class. */
@Tag("unit-tests")
final class ToolsTest {
  @Test
  void instantiation_of_the_utility_class_using_the_default_ctor_throws_illegal_access_exception() {
    thenExceptionOfType(IllegalAccessException.class)
        .isThrownBy(() -> Tools.class.getDeclaredConstructor().newInstance());
  }

  @ParameterizedTest
  @MethodSource
  void test_function_for_bumping_dates_to_the_current_or_next_friday_date(
      final String originalDate, final String expectedBumpDate) {
    var requestedDate = LocalDate.parse(originalDate);
    var expectedDate = LocalDate.parse(expectedBumpDate);
    assertThat(Tools.bumpToFriday().apply(requestedDate)).isEqualTo(expectedDate);
  }

  static Stream<Arguments> test_function_for_bumping_dates_to_the_current_or_next_friday_date() {
    return Stream.of(
        arguments("2019-09-28", "2019-09-27"), // saturday returns previous friday (yesterday)
        arguments("2019-09-29", "2019-10-04"), // sunday return next friday
        arguments("2019-09-30", "2019-10-04"), // monday return next friday
        arguments("2019-10-01", "2019-10-04"), // tuesday return next friday
        arguments("2019-10-02", "2019-10-04"), // wednesday return next friday
        arguments("2019-10-03", "2019-10-04"), // thursday return next friday (tomorrow)
        arguments("2019-10-04", "2019-10-04"), // friday return today
        arguments("2019-10-05", "2019-10-04"), // saturday returns previous friday (yesterday)
        arguments("2019-10-06", "2019-10-11")); // sunday return next friday
  }

  @ParameterizedTest
  @MethodSource
  void test_function_for_preparing_end_of_prompt_statement(
      final DayOfWeek dow, final BundleKey key) {
    assertThat(Tools.endAtStmt().apply(dow)).isEqualTo(key);
  }

  static Stream<Arguments> test_function_for_preparing_end_of_prompt_statement() {
    return Stream.concat(
        Stream.of(arguments(FRIDAY, SHABBAT_END_TOMORROW), arguments(SATURDAY, SHABBAT_END_TODAY)),
        Stream.of(DayOfWeek.values())
            .filter(d -> !List.of(FRIDAY, SATURDAY).contains(d))
            .map(d -> arguments(d, SHABBAT_END_SATURDAY)));
  }

  @ParameterizedTest
  @MethodSource
  void test_function_for_preparing_start_of_prompt_statement(
      final DayOfWeek dow, final BundleKey key) {
    assertThat(Tools.strtAtStmt().apply(dow)).isEqualTo(key);
  }

  static Stream<Arguments> test_function_for_preparing_start_of_prompt_statement() {
    return Stream.concat(
        Stream.of(
            arguments(THURSDAY, SHABBAT_START_TOMORROW),
            arguments(FRIDAY, SHABBAT_START_TODAY),
            arguments(SATURDAY, SHABBAT_START_YESTERDAY)),
        Stream.of(DayOfWeek.values())
            .filter(d -> !List.of(THURSDAY, FRIDAY, SATURDAY).contains(d))
            .map(d -> arguments(d, SHABBAT_START_FRIDAY)));
  }

  @Test
  void test_the_find_slot_tool_with_active_city_slot_should_retun_the_active_slot() {
    // select a random city slot name
    var slotInUse = CitySlot.US;
    // mock active slot and stub with value
    var activeSlot = mock(Slot.class);
    given(activeSlot.getValue()).willReturn("word");
    // create slot map
    var slots = new HashMap<String, Slot>();
    // populate map with mock active slot and non-active slots returning null by default
    slots.put(slotInUse.toString(), activeSlot);
    Stream.of(CitySlot.values())
        .filter(v -> !slotInUse.equals(v))
        .forEach(v -> slots.put(v.toString(), mock(Slot.class)));
    // create keys to compare slot keys to
    var keys = Stream.of(CitySlot.values()).map(Object::toString).collect(toList());
    // verify function return the active slot
    then(Tools.findCitySlot(keys).apply(slots)).isEqualTo(activeSlot);
  }

  @Test
  void test_the_find_slot_tool_with_wrong_keys_list_throws_no_city_slot_exception() {
    // select a random city slot name
    var slotInUse = CitySlot.IL;
    // mock active slot and stub with value
    var activeSlot = mock(Slot.class);
    given(activeSlot.getValue()).willReturn("notme");
    // create slot map
    var slots = new HashMap<String, Slot>();
    // populate map with mock active slot and non-active slots returning null by default
    slots.put(slotInUse.toString(), activeSlot);
    Stream.of(CitySlot.values())
        .filter(v -> !slotInUse.equals(v))
        .forEach(v -> slots.put(v.toString(), mock(Slot.class)));
    // create keys to compare slot keys to
    var keys = List.of("meniether", "nitherdidi");
    // verify function throws excption
    thenExceptionOfType(NoCitySlotException.class)
        .isThrownBy(() -> Tools.findCitySlot(keys).apply(slots));
  }

  @Test
  void test_the_find_slot_tool_with_no_active_city_slots_throws_no_city_slot_exception() {
    // create slot map
    var slots = new HashMap<String, Slot>();
    // populate map with mock all non-active slots returning null by default
    Stream.of(CitySlot.values()).forEach(v -> slots.put(v.toString(), mock(Slot.class)));
    // create keys to compare slot keys to
    var keys = Stream.of(CitySlot.values()).map(Object::toString).collect(toList());
    // verify function throws excption
    thenExceptionOfType(NoCitySlotException.class)
        .isThrownBy(() -> Tools.findCitySlot(keys).apply(slots));
  }
}
