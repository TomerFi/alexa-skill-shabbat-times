package info.tomfi.alexa.skills.shabbattimes.tools;

import static info.tomfi.alexa.skills.shabbattimes.tools.CityLocator.getByCityAndCountry;

import static lombok.AccessLevel.PRIVATE;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.amazon.ask.attributes.AttributesManager;
import com.amazon.ask.model.Slot;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import info.tomfi.alexa.skills.shabbattimes.city.City;
import info.tomfi.alexa.skills.shabbattimes.country.Country;
import info.tomfi.alexa.skills.shabbattimes.enums.Attributes;
import info.tomfi.alexa.skills.shabbattimes.enums.Slots;
import info.tomfi.alexa.skills.shabbattimes.exception.NoCitySlotException;
import info.tomfi.alexa.skills.shabbattimes.exception.NoJsonFileException;
import lombok.Cleanup;
import lombok.NoArgsConstructor;
import lombok.val;

/**
 * Utility class for working with the skill City objects.
 *
 * @author Tomer Figenblat {@literal <tomer.figenblat@gmail.com>}
 */
@NoArgsConstructor(access = PRIVATE)
public final class SkillTools
{
    private static final Gson gsonParser = new GsonBuilder().create();

    /**
     * A static tool for retrieving the populated city slot from the request slot map.
     * @param slots the map of slots retrieved from the request.
     * @return the populated slot holding the selected city.
     * @throws NoCitySlotException when no populated slot was found.
     */
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

    /**
     * A static tool for creating a List of {@link info.tomfi.alexa.skills.shabbattimes.city.City} objects based on the backend json files.
     * @param countryAbbreviation country abbreviation for constructing the json files name %abbreviation%_Cities.json.
     * @return a List of {@link info.tomfi.alexa.skills.shabbattimes.city.City} objects.
     * @throws NoJsonFileException when the backend json file was not found.
     */
    public static List<City> getCityListFromJsonFile(final String countryAbbreviation) throws NoJsonFileException
    {
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

    /**
     * A static tool for retrieving the {@link info.tomfi.alexa.skills.shabbattimes.city.City} object from the slots map
     * and saving the appropriate information as session attributes.
     * @param slots the map of slots retrieved from the request.
     * @param attribManager the AttributesManager object for retrieving and saving the session attributes.
     * @return
    */
    public static City getCityFromSlots(final Map<String, Slot> slots, final AttributesManager attribManager)
    {
        val selectedCity = getByCityAndCountry(slots.get(Slots.COUNTRY.getName()), getCitySlotFromMap(slots));
        setCitySessionAttributes(attribManager, selectedCity);
        return selectedCity;
    }

    private static void setCitySessionAttributes(final AttributesManager attribManager, final City selectedCity)
    {
        val sessionAttributes = attribManager.getSessionAttributes();
        sessionAttributes.put(Attributes.COUNTRY.getName(), selectedCity.getCountryAbbreviation());
        sessionAttributes.put(Attributes.CITY.getName(), selectedCity.getCityName());
        attribManager.setSessionAttributes(sessionAttributes);
    }
}
