package info.tomfi.alexa.skills.shabbattimes.api.response;

import com.google.api.client.util.Key;

import info.tomfi.alexa.skills.shabbattimes.api.response.items.ResponseItem;
import info.tomfi.alexa.skills.shabbattimes.api.response.items.ResponseLocation;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Pojo for consuming json response from the api.
 *
 * @author Tomer Figenblat {@literal <tomer.figenblat@gmail.com>}
 */
@NoArgsConstructor
public final class ApiResponse
{
    @Key @Getter private String date;
    @Key @Getter private List<ResponseItem> items;
    @Key @Getter private String link;
    @Key @Getter private ResponseLocation location;
    @Key @Getter private String title;
}
