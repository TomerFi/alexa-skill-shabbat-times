package info.tomfi.alexa.skills.shabbattimes.api.response.items;

import com.google.api.client.util.Key;

import java.util.Optional;

import lombok.Getter;

/**
 * Pojo for consuming a json item from the api response items list.
 *
 * @author Tomer Figenblat {@literal <tomer.figenblat@gmail.com>}
 */
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
