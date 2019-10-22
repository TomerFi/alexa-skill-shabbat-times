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

import info.tomfi.alexa.skills.shabbattimes.country.Country;

public final class CountryAssert extends AbstractAssert<CountryAssert, Country>
{
    protected CountryAssert(final Country actual)
    {
        super(actual, CountryAssert.class);
    }

    public CountryAssert abbreviationIs(final String testAbbreviation)
    {
        isNotNull();
        Assertions.assertThat(actual.getAbbreviation()).isEqualTo(testAbbreviation);
        return this;
    }

    public CountryAssert nameIs(final String testName)
    {
        isNotNull();
        Assertions.assertThat(actual.getName()).isEqualTo(testName);
        return this;
    }

    public CountryAssert prettyCityNamesIs(final String testPrettyCityNames)
    {
        isNotNull();
        Assertions.assertThat(actual.getPrettyCityNames()).isEqualTo(testPrettyCityNames);
        return this;
    }
}
