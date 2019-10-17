package info.tomfi.alexa.skills.shabbattimes.request.handlers;

import static info.tomfi.alexa.skills.shabbattimes.enums.Attributes.L10N_BUNDLE;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.Intent;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.RequestEnvelope;
import com.amazon.ask.model.Session;
import com.amazon.ask.model.Slot;
import com.google.gson.GsonBuilder;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import info.tomfi.alexa.skills.shabbattimes.api.APIRequestMaker;
import info.tomfi.alexa.skills.shabbattimes.api.response.APIResponse;
import info.tomfi.alexa.skills.shabbattimes.enums.Intents;
import info.tomfi.alexa.skills.shabbattimes.enums.Slots;

@Configuration
@ComponentScan(value = "info.tomfi.alexa.skills.shabbattimes.request.handlers")
public class GetCityIntentHandlerTest {
    private static Slot fakeCountrySlot;
    private static Slot fakeCitySlot;
    private static Intent fakeIntent;
    private static IntentRequest fakeRequest;
    private static Session fakeSession;
    private static RequestEnvelope fakeEnvelope;
    private static HandlerInput fakeInput;

    private static GetCityIntentHandler handlerInTest;

    @Bean
    public APIRequestMaker getAPIRequestMaker() throws IllegalStateException, IOException, URISyntaxException
    {
        APIResponse fakeResponse;
        try (
            BufferedReader breader = Files.newBufferedReader(
                Paths.get(GetCityIntentHandler.class.getClassLoader().getResource("api-responses/response_real.json").toURI())
            )
        )
        {
            fakeResponse = new GsonBuilder().create().fromJson(breader, APIResponse.class);
        }

        final APIRequestMaker mockedMaker = mock(APIRequestMaker.class);
        when(mockedMaker.setGeoId(anyInt())).thenReturn(mockedMaker);
        when(mockedMaker.setSpecificDate(any(LocalDate.class))).thenReturn(mockedMaker);
        when(mockedMaker.send()).thenReturn(fakeResponse);
        return mockedMaker;
    }

    @BeforeAll
    public static void initialize()
    {
        fakeCountrySlot = Slot.builder().withValue("israel").build();
        fakeCitySlot = Slot.builder().withValue("holon").build();
        fakeIntent = Intent.builder()
            .withName(Intents.GET_CITY.getName())
            .putSlotsItem(Slots.COUNTRY.getName(), fakeCountrySlot)
            .putSlotsItem(Slots.CITY_IL.getName(), fakeCitySlot)
            .build();

        final OffsetDateTime fakeDateTime = LocalDate.parse("2019-10-01", DateTimeFormatter.ISO_LOCAL_DATE).atStartOfDay().atOffset(ZoneOffset.ofHours(3));
        fakeRequest = IntentRequest.builder().withIntent(fakeIntent).withTimestamp(fakeDateTime).build();
        fakeSession = Session.builder().build();
        fakeEnvelope = RequestEnvelope.builder().withRequest(fakeRequest).withSession(fakeSession).build();
        fakeInput = HandlerInput.builder().withRequestEnvelope(fakeEnvelope).build();

        final ResourceBundle bundle = ResourceBundle.getBundle("locales/Responses", Locale.US);
        final Map<String, Object> attributes = new HashMap<>();
        attributes.put(L10N_BUNDLE.getName(), bundle);

        fakeInput.getAttributesManager().setRequestAttributes(attributes);

        try
        (
            AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(GetCityIntentHandlerTest.class)
        )
        {
            handlerInTest = context.getBean(GetCityIntentHandler.class);
        }
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
