package info.tomfi.alexa.skills.shabbattimes.tools;

import static info.tomfi.alexa.skills.shabbattimes.tools.SkillTools.getCitySlotFromMap;
import static info.tomfi.alexa.skills.shabbattimes.assertions.Assertions.assertThat;
import static info.tomfi.alexa.skills.shabbattimes.assertions.Assertions.assertThatExceptionOfType;

import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import com.amazon.ask.model.Slot;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import info.tomfi.alexa.skills.shabbattimes.enums.Slots;
import info.tomfi.alexa.skills.shabbattimes.exception.NoCitySlotException;

@ExtendWith(MockitoExtension.class)
public final class SkillToolsTest
{
    @Mock private Slot slotIL;
    @Mock private Slot slotUS;
    @Mock private Slot slotGB;

    private Map<String, Slot> slots;

    @BeforeEach
    public void initialize()
    {
        slots = new HashMap<>();
        slots.put(Slots.CITY_IL.getName(), slotIL);
        slots.put(Slots.CITY_US.getName(), slotUS);
        slots.put(Slots.CITY_GB.getName(), slotGB);
    }

    @Test
    @DisplayName("test exception thrown when no slot is populated with a value")
    public void getCitySlotFromMap_noValues_throwsException()
    {
        assertThatExceptionOfType(NoCitySlotException.class).isThrownBy(() -> getCitySlotFromMap(slots));
    }

    @Test
    @DisplayName("test retrieval of the israel city slot value")
    public void getCitySlotFromMap_israelCity_validateReturn()
    {
        when(slotIL.getValue()).thenReturn("fake israel city");
        assertThat(getCitySlotFromMap(slots)).isEqualTo(slotIL);
    }

    @Test
    @DisplayName("test retrieval of the us city slot value")
    public void getCitySlotFromMap_usCity_validateReturn()
    {
        when(slotUS.getValue()).thenReturn("fake us city");
        assertThat(getCitySlotFromMap(slots)).isEqualTo(slotUS);
    }

    @Test
    @DisplayName("test retrieval of the gb city slot value")
    public void getCitySlotFromMap_gbCity_validateReturn()
    {
        when(slotGB.getValue()).thenReturn("fake gb city");
        assertThat(getCitySlotFromMap(slots)).isEqualTo(slotGB);
    }
}
