package info.tomfi.alexa.skills.shabbattimes.tools;

import static lombok.AccessLevel.PRIVATE;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.amazon.ask.model.Slot;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import info.tomfi.alexa.skills.shabbattimes.city.City;
import info.tomfi.alexa.skills.shabbattimes.country.Country;
import info.tomfi.alexa.skills.shabbattimes.enums.Slots;
import info.tomfi.alexa.skills.shabbattimes.exception.NoCitySlotException;
import info.tomfi.alexa.skills.shabbattimes.exception.NoJsonFileException;
import lombok.Cleanup;
import lombok.NoArgsConstructor;
import lombok.val;

@NoArgsConstructor(access = PRIVATE)
public final class SkillTools
{
    private static final Gson gsonParser = new GsonBuilder().create();

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

    public static List<City> getCityListFromJsonFile(final String countryAbbreviation) throws NoJsonFileException {
        val jsonFileName = String.format("cities/%s_Cities.json", countryAbbreviation);
        try
        {
            @Cleanup val breader =
                Files.newBufferedReader(Paths.get(Country.class.getClassLoader().getResource(jsonFileName).toURI())
            );
            val cityArray = gsonParser.fromJson(breader, City[].class);
            return Arrays.asList(cityArray);
        } catch (IOException | NullPointerException | URISyntaxException exc)
        {
            throw new NoJsonFileException("No json file found", exc);
        }
    }
}
