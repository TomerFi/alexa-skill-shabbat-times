package info.tomfi.alexa.skills.shabbattimes.api.response.items;

import java.util.Optional;

public final class ResponseItem
{
    private String hebrew;
    private String date;
    private String title;
    private String category;

    private String link;
    private String memo;
    private String subcat;

    private boolean yomtov;

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
