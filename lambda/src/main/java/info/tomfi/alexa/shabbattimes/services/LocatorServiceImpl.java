package info.tomfi.alexa.shabbattimes.services;

import static java.util.Objects.isNull;

import com.amazon.ask.model.Slot;
import info.tomfi.alexa.shabbattimes.City;
import info.tomfi.alexa.shabbattimes.Country;
import info.tomfi.alexa.shabbattimes.LocatorService;
import info.tomfi.alexa.shabbattimes.exceptions.NoCityFoundException;
import info.tomfi.alexa.shabbattimes.exceptions.NoCityInCountryException;
import info.tomfi.alexa.shabbattimes.exceptions.NoCountryFoundException;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;

/** Service provider for locating cities within a predefined list of countries. */
@Component
public final class LocatorServiceImpl implements LocatorService {
  private final List<Country> countries;

  public LocatorServiceImpl(final List<Country> setCountries) {
    countries = setCountries;
  }

  @Override
  public City locate(final Slot citySlot) {
    return locateCity(citySlot.getValue());
  }

  @Override
  public City locate(final Slot countrySlot, final Slot citySlot) {
    return isNull(countrySlot.getValue())
        ? locateCity(citySlot.getValue())
        : locateCityInCountry(countrySlot.getValue(), citySlot.getValue());
  }

  private City locateCity(final String cityAlias) {
    return countries.stream()
        .map(c -> c.getCity(cityAlias))
        .flatMap(Optional<City>::stream)
        .findFirst()
        .orElseThrow(NoCityFoundException::new);
  }

  private City locateCityInCountry(final String countryUtterance, final String cityAlias) {
    var country =
        countries.stream()
            .filter(c -> c.hasUtterance(countryUtterance))
            .findFirst()
            .orElseThrow(NoCountryFoundException::new);

    return country.getCity(cityAlias).orElseThrow(NoCityInCountryException::new);
  }
}
