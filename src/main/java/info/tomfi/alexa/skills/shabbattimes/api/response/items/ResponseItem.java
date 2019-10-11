package info.tomfi.alexa.skills.shabbattimes.api.response.items;

import java.util.Optional;

import com.google.api.client.util.Key;

public final class ResponseItem
{
    @Key("hebrew") private String hebrew;
    @Key("date") private String date;
    @Key("title") private String title;
    @Key("category") private String category;

    @Key("link") private String link;
    @Key("memo") private String memo;
    @Key("subcat") private String subcat;

    @Key("yomtov") private boolean yomtov;

    public String getHebrew()
    {
        return hebrew;
    }

    public String getDate()
    {
        return date;
    }

    public String getTitle()
    {
        return title;
    }

    public String getCategory()
    {
        return category;
    }

    public Optional<String> getLink()
    {
        return Optional.ofNullable(link);
    }

    public Optional<String> getMemo()
    {
        return Optional.ofNullable(memo);
    }

    public Optional<String> getSubcat()
    {
        return Optional.ofNullable(subcat);
    }

    public boolean isYomtov()
    {
        return yomtov;
    }
}
