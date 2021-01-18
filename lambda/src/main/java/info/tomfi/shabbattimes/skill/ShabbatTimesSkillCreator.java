/**
 * Copyright Tomer Figenblat
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
package info.tomfi.shabbattimes.skill;

import com.amazon.ask.Skill;
import com.amazon.ask.Skills;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.Response;
import com.amazon.ask.request.exception.handler.GenericExceptionHandler;
import com.amazon.ask.request.handler.GenericRequestHandler;
import com.amazon.ask.request.interceptor.GenericRequestInterceptor;
import com.amazon.ask.request.interceptor.GenericResponseInterceptor;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Class for constructing a Skill object with the injected handlers and interceptors.
 *
 * @author Tomer Figenblat {@literal <tomer.figenblat@gmail.com>}
 */
public final class ShabbatTimesSkillCreator {
  public ShabbatTimesSkillCreator() {
    //
  }

  @Autowired private List<GenericRequestHandler<HandlerInput, Optional<Response>>> requestHandlers;

  @Autowired
  private List<GenericExceptionHandler<HandlerInput, Optional<Response>>> exceptionHandlers;

  @Autowired private List<GenericRequestInterceptor<HandlerInput>> requestInterceptors;

  @Autowired
  private List<GenericResponseInterceptor<HandlerInput, Optional<Response>>> responseInterceptors;

  /**
   * Build the Shabbat Times Skill object.
   *
   * @return the Shabbat Time Skill object.
   */
  public Skill getSkill() {
    return Skills.standard()
        .addRequestInterceptors(requestInterceptors)
        .addResponseInterceptors(responseInterceptors)
        .addRequestHandlers(requestHandlers)
        .addExceptionHandlers(exceptionHandlers)
        .build();
  }
}
