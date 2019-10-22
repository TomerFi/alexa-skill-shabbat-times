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
package info.tomfi.alexa.skills.shabbattimes.enums;

import static info.tomfi.alexa.skills.shabbattimes.enums.BundleKeys.NOT_FOUND_IN_ISRAEL;
import static info.tomfi.alexa.skills.shabbattimes.enums.BundleKeys.NOT_FOUND_IN_UK;
import static info.tomfi.alexa.skills.shabbattimes.enums.BundleKeys.NOT_FOUND_IN_US;

import java.util.Arrays;
import java.util.List;

import lombok.Getter;

/**
 * Enum helper for creating {@link info.tomfi.alexa.skills.shabbattimes.country.Country} objects.
 *
 * @author Tomer Figenblat {@literal <tomer.figenblat@gmail.com>}
 */
public enum CountryInfo
{
    ISRAEL("IL", "Israel", NOT_FOUND_IN_ISRAEL, "israel"),
    UNITED_STATES("US", "the United States", NOT_FOUND_IN_US, "united states"),
    UNITED_KINGDOM(
        "GB", "the United Kingdom",
        NOT_FOUND_IN_UK,
        "united kingdom", "great britain", "britain", "england"
    );

    @Getter private final String abbreviation;
    @Getter private final String name;
    @Getter private final BundleKeys bundleKey;
    @Getter private final List<String> utterances;

    // CHECKSTYLE.OFF: MissingJavadocMethod
    CountryInfo(
        final String setAbbreviation,
        final String setName,
        final BundleKeys setBundleKey,
        final String... setUtterances
    )
    {
        abbreviation = setAbbreviation;
        name = setName;
        bundleKey = setBundleKey;
        utterances = Arrays.asList(setUtterances);
    }
    // CHECKSTYLE.ON: MissingJavadocMethod
}
