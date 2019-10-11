package info.tomfi.alexa.skills.shabbattimes.api.response;

import java.util.List;

import com.google.api.client.util.Key;

import info.tomfi.alexa.skills.shabbattimes.api.response.items.ResponseItem;
import info.tomfi.alexa.skills.shabbattimes.api.response.items.ResponseLocation;

public final class APIResponse
{
    @Key("date") private String date;
    @Key("items") private List<ResponseItem> items;
    @Key("link") private String link;
    @Key("location") private ResponseLocation location;
    @Key("title") private String title;

    public String getDate()
    {
        return date;
    }

    public List<ResponseItem> getItems()
    {
        return items;
    }

    public String getLink()
    {
        return link;
    }

    public ResponseLocation getLocation()
    {
        return location;
    }

    public String getTitle()
    {
        return title;
    }
}
