package info.tomfi.alexa.skills.shabbattimes.tools;

import static lombok.AccessLevel.PRIVATE;

import java.util.Arrays;
import java.util.Map;

import com.amazon.ask.model.Slot;

import info.tomfi.alexa.skills.shabbattimes.enums.Slots;
import info.tomfi.alexa.skills.shabbattimes.exception.NoCitySlotException;
import lombok.NoArgsConstructor;
import lombok.val;

@NoArgsConstructor(access = PRIVATE)
public final class SkillTools
{
    public static Slot getCitySlotFromMap(final Map<String, Slot> slots) throws NoCitySlotException
    {
        val cityKeys = Arrays.asList(Slots.CITY_IL.getName(), Slots.CITY_GB.getName(), Slots.CITY_US.getName());
        val slotKey = slots.keySet()
            .stream()
            .filter(key -> cityKeys.contains(key))
            .filter(key -> slots.get(key).getValue() != null)
            .findFirst();

        if (!slotKey.isPresent())
        {
            throw new NoCitySlotException("No city slot found.");
        }
        return slots.get(slotKey.get());
    }
}
