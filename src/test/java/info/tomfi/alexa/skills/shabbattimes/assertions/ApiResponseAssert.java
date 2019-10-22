/**
 * Copyright 2019 Tomer Figenblat
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
