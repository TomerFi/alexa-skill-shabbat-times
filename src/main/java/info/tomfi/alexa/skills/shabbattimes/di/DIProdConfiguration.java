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
package info.tomfi.alexa.skills.shabbattimes.di;

import static org.springframework.core.Ordered.LOWEST_PRECEDENCE;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.gson.GsonFactory;

import info.tomfi.alexa.skills.shabbattimes.ShabbatTimesSkillCreator;
import info.tomfi.alexa.skills.shabbattimes.api.ApiRequestMaker;

import java.io.IOException;

import lombok.NoArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.annotation.Order;

/**
 * Main Spring-Context dependency injection annotated configuration class.
 *
 * This configuration class takes the lowest precedence for multiple configuration classes.
 * Component scaning is based on:
 * <ul>
 *     <li>info.tomfi.alexa.skills.shabbattimes.exception.handlers</li>
 *     <li>info.tomfi.alexa.skills.shabbattimes.request.handlers</li>
 *     <li>info.tomfi.alexa.skills.shabbattimes.request.interceptors</li>
 *     <li>info.tomfi.alexa.skills.shabbattimes.response.interceptors</li>
 * </ul>
 *
 * @author Tomer Figenblat {@literal <tomer.figenblat@gmail.com>}
 */
@Lazy
@Configuration
@ComponentScan(basePackages = {
    "info.tomfi.alexa.skills.shabbattimes.exception.handlers",
    "info.tomfi.alexa.skills.shabbattimes.request.handlers",
    "info.tomfi.alexa.skills.shabbattimes.request.interceptors",
    "info.tomfi.alexa.skills.shabbattimes.response.interceptors"
})
@Order(LOWEST_PRECEDENCE)
@NoArgsConstructor
public class DiProdConfiguration
{
    @Bean
    public ShabbatTimesSkillCreator getShabbatTimesSkillCreator()
    {
        return new ShabbatTimesSkillCreator();
    }

    @Bean
    public ApiRequestMaker getRequestMaker()
    {
        return new ApiRequestMaker();
    }

    @Bean
    public GenericUrl getApiUrl()
    {
        return new GenericUrl("https://www.hebcal.com/shabbat/");
    }

    @Bean
    public HttpTransport getTransport()
    {
        return new NetHttpTransport();
    }

    @Bean
    public HttpRequestInitializer getInitializer()
    {
        return new HttpRequestInitializer()
        {
            @Override
            public void initialize(final HttpRequest request) throws IOException
            {
                request.setParser(new JsonObjectParser(new GsonFactory()));
            }
        };
    }
}
