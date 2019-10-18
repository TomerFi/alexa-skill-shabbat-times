package info.tomfi.alexa.skills.shabbattimes.api.response.items;

import java.util.Optional;

import com.google.api.client.util.Key;

import lombok.Getter;

public final class ResponseItem
{
    @Key @Getter private String hebrew;
    @Key @Getter private String date;
    @Key @Getter private String title;
    @Key @Getter private String category;

    @Key private String link;
    @Key private String memo;
    @Key private String subcat;

    @Key @Getter private boolean yomtov;

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
}
