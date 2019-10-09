package info.tomfi.alexa.skills.shabbattimes.request.handlers;

import static info.tomfi.alexa.skills.shabbattimes.tools.GlobalEnums.Attributes;
import static info.tomfi.alexa.skills.shabbattimes.tools.GlobalEnums.CountryInfo;
import static info.tomfi.alexa.skills.shabbattimes.tools.GlobalEnums.Intents;
import static info.tomfi.alexa.skills.shabbattimes.tools.GlobalEnums.Slots;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.impl.IntentRequestHandler;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.Slot;

import info.tomfi.alexa.skills.shabbattimes.annotation.IncludeRequestHandler;
import info.tomfi.alexa.skills.shabbattimes.city.City;
import info.tomfi.alexa.skills.shabbattimes.country.Country;
import info.tomfi.alexa.skills.shabbattimes.country.CountryFactory;
import info.tomfi.alexa.skills.shabbattimes.exception.NoCityFoundException;
import info.tomfi.alexa.skills.shabbattimes.exception.NoCityInCountryException;
import info.tomfi.alexa.skills.shabbattimes.exception.NoCitySlotException;

@IncludeRequestHandler
public final class GetCityIntentHandler implements IntentRequestHandler
{
    @Override
    public boolean canHandle(final HandlerInput input, final IntentRequest intent)
    {
        return intent.getIntent().getName().equals(Intents.GET_CITY.name);
    }

    @Override
    public Optional<Response> handle(final HandlerInput input, final IntentRequest intent) throws NoCitySlotException, NoCityInCountryException
    {
        final List<String> cityKeys = Arrays.asList(Slots.CITY_IL.name, Slots.CITY_GB.name, Slots.CITY_US.name);
        final Map<String, Slot> slots = intent.getIntent().getSlots();
        final Optional<String> slotKey = slots.keySet()
            .stream()
            .filter(key -> cityKeys.contains(key))
            .filter(key -> slots.get(key).getValue() != null)
            .findFirst();

            if (!slotKey.isPresent())
        {
            throw new NoCitySlotException("No city slot found.");
        }
        final City selectedCity = getByCityAndCountry(slots.get(Slots.COUNTRY.name), slots.get(slotKey.get()));

        final Map<String, Object> attribs = input.getAttributesManager().getSessionAttributes();
        attribs.put(Attributes.COUNTRY.name, selectedCity.getCountryAbbreviation());
        attribs.put(Attributes.CITY.name, selectedCity.getCityName());
        attribs.put(Attributes.GEOID.name, selectedCity.getGeoId());
        attribs.put(Attributes.GEONAME.name, selectedCity.getGeoName());
        input.getAttributesManager().setSessionAttributes(attribs);

        // TODO
        return input.getResponseBuilder()
            .withSpeech("TODO found city")
            .withShouldEndSession(true)
            .build();
    }

    private City getByCityAndCountry(final Slot countrySlot, final Slot citySlot) throws NoCityFoundException, NoCityInCountryException
    {
        if (countrySlot.getValue() == null)
        {
            return getByCity(citySlot);
        }
        final Country country = CountryFactory.getCountry(countrySlot.getValue());
        final Optional<City> cityOpt = findCityInCountry(country, citySlot.getValue());
        if (cityOpt.isPresent())
        {
            return cityOpt.get();
        }
        throw new NoCityInCountryException(String.format("no city %s in %s.", citySlot.getValue(), countrySlot.getValue()));
    }

    private City getByCity(final Slot citySlot)
    {
        for (CountryInfo member : CountryInfo.values())
        {
            final Country country = CountryFactory.getCountryByMember(member);
            final Optional<City> cityOpt = findCityInCountry(country, citySlot.getValue());
            if (cityOpt.isPresent())
            {
                return cityOpt.get();
            }
        }
        throw new NoCityFoundException(String.format("city %s not found", citySlot.getValue()));
    }

    private Optional<City> findCityInCountry(final Country country, final String cityName)
    {
        final Iterator<City> cities = country.iterator();
        while (cities.hasNext())
        {
            final City currentCity = cities.next();
            final Iterator<String> aliases = currentCity.iterator();
            while (aliases.hasNext())
            {
                final String currentAlias = aliases.next();
                if (currentAlias.equalsIgnoreCase(cityName))
                {
                    return Optional.of(currentCity);
                }
            }
        }
        return Optional.empty();
    }
}
