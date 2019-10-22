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
package info.tomfi.alexa.skills.shabbattimes.request.handlers;

import static info.tomfi.alexa.skills.shabbattimes.enums.Attributes.L10N_BUNDLE;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Locale;
import java.util.ResourceBundle;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.Intent;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.RequestEnvelope;
import com.amazon.ask.model.Session;
import com.amazon.ask.model.Slot;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import info.tomfi.alexa.skills.shabbattimes.enums.Intents;
import info.tomfi.alexa.skills.shabbattimes.enums.Slots;
import info.tomfi.alexa.skills.shabbattimes.di.DiMockAPIConfiguration;

import lombok.Cleanup;
import lombok.val;

public final class GetCityIntentHandlerTest
{
    private static IntentRequest fakeRequest;
    private static HandlerInput fakeInput;

    private static GetCityIntentHandler handlerInTest;

    @BeforeAll
    public static void initialize()
    {
        val fakeCountrySlot = Slot.builder().withValue("israel").build();
        val fakeCitySlot = Slot.builder().withValue("holon").build();
        val fakeIntent = Intent.builder()
            .withName(Intents.GET_CITY.getName())
            .putSlotsItem(Slots.COUNTRY.getName(), fakeCountrySlot)
            .putSlotsItem(Slots.CITY_IL.getName(), fakeCitySlot)
            .build();

        val fakeDateTime = LocalDate.parse("2019-10-01", DateTimeFormatter.ISO_LOCAL_DATE).atStartOfDay().atOffset(ZoneOffset.ofHours(3));
        fakeRequest = IntentRequest.builder().withIntent(fakeIntent).withTimestamp(fakeDateTime).build();
        val fakeSession = Session.builder().build();
        val fakeEnvelope = RequestEnvelope.builder().withRequest(fakeRequest).withSession(fakeSession).build();
        fakeInput = HandlerInput.builder().withRequestEnvelope(fakeEnvelope).build();

        val bundle = ResourceBundle.getBundle("locales/Responses", Locale.US);
        val attributes = new HashMap<String, Object>();
        attributes.put(L10N_BUNDLE.getName(), bundle);
        fakeInput.getAttributesManager().setRequestAttributes(attributes);

        @Cleanup val context = new AnnotationConfigApplicationContext(DiMockAPIConfiguration.class);
        handlerInTest = context.getBean(GetCityIntentHandler.class);
    }

    @Test
    @DisplayName("test canHandle method implmentation")
    public void canHandle_fakeArgs_returnTrue()
    {
        assertThat(handlerInTest.canHandle(fakeInput, fakeRequest)).isTrue();
    }

    @Test
    @DisplayName("test handle method implementation")
    public void handle_fakeArgs_validateResponse()
    {
        assertThat(handlerInTest.handle(fakeInput, fakeRequest).isPresent()).isTrue();
    }
}
