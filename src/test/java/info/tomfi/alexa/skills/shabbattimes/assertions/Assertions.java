/**
 * Copyright 2019 Tomer Figenblat
 *
 * <p>Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of the License at
 *
 * <p>http://www.apache.org/licenses/LICENSE-2.0
 *
 * <p>Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing permissions and
 * limitations under the License.
 */
package info.tomfi.alexa.skills.shabbattimes.assertions;

import com.amazon.ask.model.ResponseEnvelope;
import com.amazon.ask.response.SkillResponse;
import info.tomfi.alexa.skills.shabbattimes.api.response.ApiResponse;
import info.tomfi.alexa.skills.shabbattimes.api.response.items.ResponseItem;
import info.tomfi.alexa.skills.shabbattimes.api.response.items.ResponseLocation;
import info.tomfi.alexa.skills.shabbattimes.city.City;
import info.tomfi.alexa.skills.shabbattimes.country.Country;

public final class Assertions extends org.assertj.core.api.Assertions {
  public static ApiResponseAssert assertThat(final ApiResponse actual) {
    return new ApiResponseAssert(actual);
  }

  public static CityAssert assertThat(final City actual) {
    return new CityAssert(actual);
  }

  public static CountryAssert assertThat(final Country actual) {
    return new CountryAssert(actual);
  }

  public static ResponseItemAssert assertThat(final ResponseItem actual) {
    return new ResponseItemAssert(actual);
  }

  public static ResponseLocationAssert assertThat(final ResponseLocation actual) {
    return new ResponseLocationAssert(actual);
  }

  public static SkillResponseAssert assertThat(final SkillResponse<ResponseEnvelope> actual) {
    return new SkillResponseAssert(actual);
  }
}
