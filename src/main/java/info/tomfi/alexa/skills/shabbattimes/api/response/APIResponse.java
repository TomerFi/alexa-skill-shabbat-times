package info.tomfi.alexa.skills.shabbattimes.api.response;

import java.util.List;

import info.tomfi.alexa.skills.shabbattimes.api.response.items.ResponseItem;
import info.tomfi.alexa.skills.shabbattimes.api.response.items.ResponseLocation;

public final class APIResponse
{
    private String date;
    private List<ResponseItem> items;
    private String link;
    private ResponseLocation location;
    private String title;

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
