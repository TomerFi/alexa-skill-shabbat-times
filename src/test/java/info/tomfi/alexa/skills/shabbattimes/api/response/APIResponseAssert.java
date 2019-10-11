package info.tomfi.alexa.skills.shabbattimes.api.response;

import java.util.List;

import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;

import info.tomfi.alexa.skills.shabbattimes.api.response.items.ResponseItem;
import info.tomfi.alexa.skills.shabbattimes.api.response.items.ResponseLocation;

public final class APIResponseAssert extends AbstractAssert<APIResponseAssert, APIResponse>
{
    public APIResponseAssert(final APIResponse actual)
    {
        super(actual, APIResponseAssert.class);
    }

    public static APIResponseAssert assertThat(final APIResponse actual)
    {
        return new APIResponseAssert(actual);
    }

    public APIResponseAssert dateIs(final String testDate)
    {
        isNotNull();
        Assertions.assertThat(actual.getDate()).isEqualTo(testDate);
        return this;
    }

    public APIResponseAssert linkIs(final String testLink)
    {
        isNotNull();
        Assertions.assertThat(actual.getLink()).isEqualTo(testLink);
        return this;
    }

    public APIResponseAssert titleIs(final String testTitle)
    {
        isNotNull();
        Assertions.assertThat(actual.getTitle()).isEqualTo(testTitle);
        return this;
    }

    public APIResponseAssert itemsIs(final List<ResponseItem> testItems)
    {
        isNotNull();
        Assertions.assertThat(actual.getItems()).isEqualTo(testItems);
        return this;
    }

    public APIResponseAssert locationIs(final ResponseLocation testLocation)
    {
        isNotNull();
        Assertions.assertThat(actual.getLocation()).isEqualTo(testLocation);
        return this;
    }
}
