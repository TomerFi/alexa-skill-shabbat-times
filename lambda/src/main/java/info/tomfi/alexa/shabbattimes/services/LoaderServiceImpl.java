package info.tomfi.alexa.shabbattimes.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import info.tomfi.alexa.shabbattimes.City;
import info.tomfi.alexa.shabbattimes.LoaderService;
import info.tomfi.alexa.shabbattimes.exceptions.NoJsonFileException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Component;

/** Service provider for loading cities for countries. */
@Component
public final class LoaderServiceImpl implements LoaderService {
  private final ObjectMapper mapper;

  public LoaderServiceImpl() {
    mapper = new ObjectMapper();
  }

  @Override
  public List<City> loadCities(final String abbreviation) {
    var jsonFileName = String.format("cities/%s_Cities.json", abbreviation);
    try (var json = getClass().getClassLoader().getResourceAsStream(jsonFileName)) {
      return Arrays.asList(mapper.readValue(json, City[].class));
    } catch (IOException | IllegalArgumentException | NullPointerException exc) {
      throw new NoJsonFileException(exc);
    }
  }
}
