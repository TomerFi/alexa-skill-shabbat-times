package info.tomfi.alexa.skills.shabbattimes.request.handlers;

import static info.tomfi.alexa.skills.shabbattimes.enums.Attributes.L10N_BUNDLE;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

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

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import info.tomfi.alexa.skills.shabbattimes.enums.Intents;
import info.tomfi.alexa.skills.shabbattimes.enums.Slots;
import info.tomfi.alexa.skills.shabbattimes.exception.NoCountrySlotException;

public final class CountrySelectedIntentHandlerTest
{
    private static Slot fakeCountrySlot;
    private static Intent fakeIntent;
    private static IntentRequest fakeRequest;
    private static Session fakeSession;
    private static RequestEnvelope fakeEnvelope;
    private static HandlerInput fakeInput;

    private static CountrySelectedIntentHandler handlerInTest;

    @BeforeAll
    public static void initialize()
    {
        fakeCountrySlot = Slot.builder().withValue("great britain").build();
        fakeIntent = Intent.builder()
            .withName(Intents.COUNTRY_SELECTED.getName())
            .putSlotsItem(Slots.COUNTRY.getName(), fakeCountrySlot)
            .build();
        fakeRequest = IntentRequest.builder().withIntent(fakeIntent).build();
        fakeSession = Session.builder().build();
        fakeEnvelope = RequestEnvelope.builder().withRequest(fakeRequest).withSession(fakeSession).build();
        fakeInput = HandlerInput.builder().withRequestEnvelope(fakeEnvelope).build();

        final ResourceBundle bundle = ResourceBundle.getBundle("locales/Responses", Locale.US);
        final Map<String, Object> attributes = new HashMap<>();
        attributes.put(L10N_BUNDLE.getName(), bundle);

        fakeInput.getAttributesManager().setRequestAttributes(attributes);

        handlerInTest = new CountrySelectedIntentHandler();
    }

    @Test
    @DisplayName("test canHandle method implmentation")
    public void canHandle_fakeArgs_returnTrue()
    {
        assertThat(handlerInTest.canHandle(fakeInput, fakeRequest)).isTrue();
    }

    @Test
    @DisplayName("test thrown exception when no country slot value exist")
    public void handle_noCountrySlot_throwsException()
    {
        final Slot emptyCountrySlot = Slot.builder().build();
        final Intent noCountrySlotIntent = Intent.builder()
            .withName(Intents.COUNTRY_SELECTED.getName())
            .putSlotsItem(Slots.COUNTRY.getName(), emptyCountrySlot)
            .build();
        final IntentRequest noCountrySlotIntentRequest = IntentRequest.builder().withIntent(noCountrySlotIntent).build();
        final RequestEnvelope noCountrySlotEnvelope = RequestEnvelope.builder().withRequest(noCountrySlotIntentRequest).build();
        final HandlerInput noCountrySlotInput = HandlerInput.builder().withRequestEnvelope(noCountrySlotEnvelope).build();

        assertThatExceptionOfType(NoCountrySlotException.class).isThrownBy(() -> handlerInTest.handle(noCountrySlotInput, noCountrySlotIntentRequest));
    }

    @Test
    @DisplayName("test handle method implementation")
    public void handle_fakeArgs_validateResponse()
    {
        assertThat(handlerInTest.handle(fakeInput, fakeRequest).isPresent()).isTrue();
    }
}
