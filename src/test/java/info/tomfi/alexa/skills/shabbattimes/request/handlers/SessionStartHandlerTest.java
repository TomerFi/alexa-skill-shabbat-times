package info.tomfi.alexa.skills.shabbattimes.request.handlers;

import static info.tomfi.alexa.skills.shabbattimes.enums.Attributes.L10N_BUNDLE;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Locale;
import java.util.ResourceBundle;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.LaunchRequest;
import com.amazon.ask.model.RequestEnvelope;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import lombok.val;

public final class SessionStartHandlerTest
{
    private static LaunchRequest fakeRequest;
    private static HandlerInput fakeInput;

    private static SessionStartHandler handlerInTest;

    @BeforeAll
    public static void initialize()
    {
        fakeRequest = LaunchRequest.builder().build();
        val fakeEnvelope = RequestEnvelope.builder().withRequest(fakeRequest).build();
        fakeInput = HandlerInput.builder().withRequestEnvelope(fakeEnvelope).build();

        val bundle = ResourceBundle.getBundle("locales/Responses", Locale.US);
        val attributes = new HashMap<String, Object>();
        attributes.put(L10N_BUNDLE.getName(), bundle);
        fakeInput.getAttributesManager().setRequestAttributes(attributes);

        handlerInTest = new SessionStartHandler();
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
