package info.tomfi.alexa.skills.shabbattimes.request.handlers;

import static info.tomfi.alexa.skills.shabbattimes.enums.Attributes.L10N_BUNDLE;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Locale;
import java.util.ResourceBundle;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.Intent;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.RequestEnvelope;
import com.amazon.ask.model.Session;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import info.tomfi.alexa.skills.shabbattimes.enums.Attributes;
import info.tomfi.alexa.skills.shabbattimes.enums.Intents;

import lombok.val;

public final class NoIntentHandlerTest
{
    private static IntentRequest fakeRequest;
    private static HandlerInput fakeInput;

    private static NoIntentHandler handlerInTest;

    @BeforeAll
    public static void initialize()
    {
        val fakeIntent = Intent.builder().withName(Intents.NO.getName()).build();
        fakeRequest = IntentRequest.builder().withIntent(fakeIntent).build();
        val fakeSession = Session.builder()
            .putAttributesItem(Attributes.LAST_INTENT.getName(), Intents.COUNTRY_SELECTED.getName())
            .putAttributesItem(Attributes.COUNTRY.getName(), "IL")
            .build();
        val fakeEnvelope = RequestEnvelope.builder().withRequest(fakeRequest).withSession(fakeSession).build();
        fakeInput = HandlerInput.builder().withRequestEnvelope(fakeEnvelope).build();

        val bundle = ResourceBundle.getBundle("locales/Responses", Locale.US);
        val attributes = new HashMap<String, Object>();
        attributes.put(L10N_BUNDLE.getName(), bundle);
        fakeInput.getAttributesManager().setRequestAttributes(attributes);

        handlerInTest = new NoIntentHandler();
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
