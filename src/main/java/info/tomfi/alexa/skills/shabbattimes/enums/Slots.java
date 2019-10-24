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

import static lombok.AccessLevel.PRIVATE;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

/**
 * Helper class for identifying the request slots.
 *
 * @author Tomer Figenblat {@literal <tomer.figenblat@gmail.com>}
 */
@NoArgsConstructor(access = PRIVATE)
@SuppressWarnings("PMD.ClassNamingConventions")
public final class Slots
{
    /**
     * Constant String for identifying the country slot.
     */
    public static final String COUNTRY = "Country";

    /**
     * Enum helper for identifying the city slots.
     *
     * @author Tomer Figenblat {@literal <tomer.figenblat@gmail.com>}
     */
    @RequiredArgsConstructor
    public enum City
    {
        GB("City_GB"),
        IL("City_IL"),
        US("City_US");

        @Getter private final String name;
    }
}
