package info.tomfi.alexa.skills.shabbattimes.assertions;

import com.amazon.ask.model.ResponseEnvelope;
import com.amazon.ask.response.SkillResponse;

import info.tomfi.alexa.skills.shabbattimes.api.response.ApiResponse;
import info.tomfi.alexa.skills.shabbattimes.api.response.items.ResponseItem;
import info.tomfi.alexa.skills.shabbattimes.api.response.items.ResponseLocation;
import info.tomfi.alexa.skills.shabbattimes.city.City;
import info.tomfi.alexa.skills.shabbattimes.country.Country;

public final class Assertions extends org.assertj.core.api.Assertions
{
    public static ApiResponseAssert assertThat(final ApiResponse actual)
    {
        return new ApiResponseAssert(actual);
    }

    public static CityAssert assertThat(final City actual)
    {
        return new CityAssert(actual);
    }

    public static CountryAssert assertThat(final Country actual)
    {
        return new CountryAssert(actual);
    }

    public static ResponseItemAssert assertThat(final ResponseItem actual)
    {
        return new ResponseItemAssert(actual);
    }

    public static ResponseLocationAssert assertThat(final ResponseLocation actual)
    {
        return new ResponseLocationAssert(actual);
    }

    public static SkillResponseAssert assertThat(final SkillResponse<ResponseEnvelope> actual)
    {
        return new SkillResponseAssert(actual);
    }
}
