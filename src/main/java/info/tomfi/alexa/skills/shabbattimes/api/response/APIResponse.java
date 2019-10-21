package info.tomfi.alexa.skills.shabbattimes.api.response;

import java.util.List;

import com.google.api.client.util.Key;

import info.tomfi.alexa.skills.shabbattimes.api.response.items.ResponseItem;
import info.tomfi.alexa.skills.shabbattimes.api.response.items.ResponseLocation;
import lombok.Getter;

/**
 * Pojo for consuming json response from the api.
 *
 * @author Tomer Figenblat {@literal <tomer.figenblat@gmail.com>}
 */
public final class APIResponse
{
    @Key @Getter private String date;
    @Key @Getter private List<ResponseItem> items;
    @Key @Getter private String link;
    @Key @Getter private ResponseLocation location;
    @Key @Getter private String title;
}
