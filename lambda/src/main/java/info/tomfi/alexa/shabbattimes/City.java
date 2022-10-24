package info.tomfi.alexa.shabbattimes;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.auto.value.AutoValue;
import java.util.List;

/** City value abstraction. */
@AutoValue
@JsonDeserialize(builder = AutoValue_City.Builder.class)
public abstract class City {
  public abstract String cityName();

  public abstract String geoName();

  public abstract int geoId();

  public abstract String countryAbbreviation();

  public abstract List<String> aliases();

  /** City value builder abstraction. */
  @AutoValue.Builder
  public abstract static class Builder {
    @JsonProperty("cityName")
    public abstract Builder cityName(String cityName);

    @JsonProperty("geoName")
    public abstract Builder geoName(String geoName);

    @JsonProperty("geoId")
    public abstract Builder geoId(int geoId);

    @JsonProperty("countryAbbreviation")
    public abstract Builder countryAbbreviation(String countryAbbreviation);

    @JsonProperty("aliases")
    public abstract Builder aliases(List<String> aliases);

    public abstract City build();
  }
}
