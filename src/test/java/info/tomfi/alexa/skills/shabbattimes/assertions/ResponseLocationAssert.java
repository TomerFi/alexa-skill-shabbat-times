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

import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;

import info.tomfi.alexa.skills.shabbattimes.api.response.items.ResponseLocation;

public final class ResponseLocationAssert extends AbstractAssert<ResponseLocationAssert, ResponseLocation>
{
    protected ResponseLocationAssert(final ResponseLocation actual)
    {
        super(actual, ResponseLocationAssert.class);
    }

    public ResponseLocationAssert admin1Is(final String testAdmin1)
    {
        isNotNull();
        Assertions.assertThat(actual.getAdmin1()).isEqualTo(testAdmin1);
        return this;
    }

    public ResponseLocationAssert asciinameIs(final String testAsciiname)
    {
        isNotNull();
        Assertions.assertThat(actual.getAsciiname()).isEqualTo(testAsciiname);
        return this;
    }

    public ResponseLocationAssert cityIs(final String testCity)
    {
        isNotNull();
        Assertions.assertThat(actual.getCity()).isEqualTo(testCity);
        return this;
    }

    public ResponseLocationAssert countryIs(final String testCountry)
    {
        isNotNull();
        Assertions.assertThat(actual.getCountry()).isEqualTo(testCountry);
        return this;
    }

    public ResponseLocationAssert geoIs(final String testGeo)
    {
        isNotNull();
        Assertions.assertThat(actual.getGeo()).isEqualTo(testGeo);
        return this;
    }

    public ResponseLocationAssert geonameidIs(final int testGeonameid)
    {
        isNotNull();
        Assertions.assertThat(actual.getGeonameid()).isEqualTo(testGeonameid);
        return this;
    }

    public ResponseLocationAssert titleIs(final String testTitle)
    {
        isNotNull();
        Assertions.assertThat(actual.getTitle()).isEqualTo(testTitle);
        return this;
    }

    public ResponseLocationAssert tzidIs(final String testTzid)
    {
        isNotNull();
        Assertions.assertThat(actual.getTzid()).isEqualTo(testTzid);
        return this;
    }

    public ResponseLocationAssert latitudeIs(final Double testLatitude)
    {
        isNotNull();
        Assertions.assertThat(actual.getLatitude()).isEqualTo(testLatitude);
        return this;
    }

    public ResponseLocationAssert longitudeIs(final Double testLongitude)
    {
        isNotNull();
        Assertions.assertThat(actual.getLongitude()).isEqualTo(testLongitude);
        return this;
    }
}
