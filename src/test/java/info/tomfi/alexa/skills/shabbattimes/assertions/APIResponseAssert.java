package info.tomfi.alexa.skills.shabbattimes.assertions;

import java.util.List;

import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;

import info.tomfi.alexa.skills.shabbattimes.api.response.ApiResponse;
import info.tomfi.alexa.skills.shabbattimes.api.response.items.ResponseItem;
import info.tomfi.alexa.skills.shabbattimes.api.response.items.ResponseLocation;

public final class ApiResponseAssert extends AbstractAssert<ApiResponseAssert, ApiResponse>
{
    protected ApiResponseAssert(final ApiResponse actual)
    {
        super(actual, ApiResponseAssert.class);
    }

    public ApiResponseAssert dateIs(final String testDate)
    {
        isNotNull();
        Assertions.assertThat(actual.getDate()).isEqualTo(testDate);
        return this;
    }

    public ApiResponseAssert linkIs(final String testLink)
    {
        isNotNull();
        Assertions.assertThat(actual.getLink()).isEqualTo(testLink);
        return this;
    }

    public ApiResponseAssert titleIs(final String testTitle)
    {
        isNotNull();
        Assertions.assertThat(actual.getTitle()).isEqualTo(testTitle);
        return this;
    }

    public ApiResponseAssert itemsIs(final List<ResponseItem> testItems)
    {
        isNotNull();
        Assertions.assertThat(actual.getItems()).isEqualTo(testItems);
        return this;
    }

    public ApiResponseAssert locationIs(final ResponseLocation testLocation)
    {
        isNotNull();
        Assertions.assertThat(actual.getLocation()).isEqualTo(testLocation);
        return this;
    }
}
