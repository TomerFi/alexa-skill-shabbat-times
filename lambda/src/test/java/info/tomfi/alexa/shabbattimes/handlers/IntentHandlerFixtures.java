/**
 * Copyright Tomer Figenblat.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package info.tomfi.alexa.shabbattimes.handlers;

import com.amazon.ask.model.IntentRequest;
import org.mockito.Mock;

// TODO: get rid of this
/** Various testing fixtures for testing the intent handler classes. */
public class IntentHandlerFixtures extends HandlerFixtures {
  @Mock protected IntentRequest request;
}
