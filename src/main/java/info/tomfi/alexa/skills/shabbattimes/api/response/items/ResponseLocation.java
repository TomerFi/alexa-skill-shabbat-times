package info.tomfi.alexa.skills.shabbattimes.api.response.items;

import com.google.api.client.util.Key;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Pojo for consuming a json location from the api response location object.
 *
 * @author Tomer Figenblat {@literal <tomer.figenblat@gmail.com>}
 */
@NoArgsConstructor
public final class ResponseLocation
{
    @Key @Getter private String admin1;
    @Key @Getter private String asciiname;
    @Key @Getter private String city;
    @Key @Getter private String country;
    @Key @Getter private String geo;
    @Key @Getter private int geonameid;
    @Key @Getter private Double latitude;
    @Key @Getter private Double longitude;
    @Key @Getter private String title;
    @Key @Getter private String tzid;
}
