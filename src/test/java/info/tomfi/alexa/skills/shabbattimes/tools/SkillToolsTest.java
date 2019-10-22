/**
 * Copyright 2019 Tomer Figenblat
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package info.tomfi.alexa.skills.shabbattimes.tools;

import static info.tomfi.alexa.skills.shabbattimes.tools.SkillTools.getCitySlotFromMap;
import static info.tomfi.alexa.skills.shabbattimes.assertions.Assertions.assertThat;
import static info.tomfi.alexa.skills.shabbattimes.assertions.Assertions.assertThatExceptionOfType;

import static org.mockito.Mockito.when;

import com.amazon.ask.model.Slot;

import info.tomfi.alexa.skills.shabbattimes.enums.Slots;
import info.tomfi.alexa.skills.shabbattimes.exception.NoCitySlotException;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
