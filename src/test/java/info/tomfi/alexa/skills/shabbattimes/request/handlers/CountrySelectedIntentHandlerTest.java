package info.tomfi.alexa.skills.shabbattimes.request.handlers;

import static info.tomfi.alexa.skills.shabbattimes.enums.Attributes.L10N_BUNDLE;
import static info.tomfi.alexa.skills.shabbattimes.assertions.Assertions.assertThat;
import static info.tomfi.alexa.skills.shabbattimes.assertions.Assertions.assertThatExceptionOfType;

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

import info.tomfi.alexa.skills.shabbattimes.enums.Intents;
import info.tomfi.alexa.skills.shabbattimes.enums.Slots;
import info.tomfi.alexa.skills.shabbattimes.exception.NoCountrySlotException;
import lombok.val;

public final class CountrySelectedIntentHandlerTest
{
    private static IntentRequest fakeRequest;
    private static HandlerInput fakeInput;

    private static CountrySelectedIntentHandler handlerInTest;

    @BeforeAll
    public static void initialize()
    {
        val fakeCountrySlot = Slot.builder().withValue("great britain").build();
        val fakeIntent = Intent.builder()
            .withName(Intents.COUNTRY_SELECTED.getName())
            .putSlotsItem(Slots.COUNTRY.getName(), fakeCountrySlot)
            .build();
        fakeRequest = IntentRequest.builder().withIntent(fakeIntent).build();

        val fakeSession = Session.builder().build();
        val fakeEnvelope = RequestEnvelope.builder().withRequest(fakeRequest).withSession(fakeSession).build();
        fakeInput = HandlerInput.builder().withRequestEnvelope(fakeEnvelope).build();

        val bundle = ResourceBundle.getBundle("locales/Responses", Locale.US);
        val attributes = new HashMap<String, Object>();
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
        val emptyCountrySlot = Slot.builder().build();
        val noCountrySlotIntent = Intent.builder()
            .withName(Intents.COUNTRY_SELECTED.getName())
            .putSlotsItem(Slots.COUNTRY.getName(), emptyCountrySlot)
            .build();
        val noCountrySlotIntentRequest = IntentRequest.builder().withIntent(noCountrySlotIntent).build();
        val noCountrySlotEnvelope = RequestEnvelope.builder().withRequest(noCountrySlotIntentRequest).build();
        val noCountrySlotInput = HandlerInput.builder().withRequestEnvelope(noCountrySlotEnvelope).build();

        assertThatExceptionOfType(NoCountrySlotException.class).isThrownBy(
            () -> handlerInTest.handle(noCountrySlotInput, noCountrySlotIntentRequest)
        );
    }

    @Test
    @DisplayName("test handle method implementation")
    public void handle_fakeArgs_validateResponse()
    {
        assertThat(handlerInTest.handle(fakeInput, fakeRequest).isPresent()).isTrue();
    }
}
