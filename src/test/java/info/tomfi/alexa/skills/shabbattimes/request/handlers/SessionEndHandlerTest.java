package info.tomfi.alexa.skills.shabbattimes.request.handlers;

import static org.assertj.core.api.Assertions.assertThat;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.RequestEnvelope;
import com.amazon.ask.model.SessionEndedRequest;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import lombok.val;

public final class SessionEndHandlerTest
{
    private static SessionEndedRequest fakeRequest;
    private static HandlerInput fakeInput;

    private static SessionEndHandler handlerInTest;

    @BeforeAll
    public static void initialize()
    {
        fakeRequest = SessionEndedRequest.builder().build();
        val fakeEnvelope = RequestEnvelope.builder().withRequest(fakeRequest).build();
        fakeInput = HandlerInput.builder().withRequestEnvelope(fakeEnvelope).build();

        handlerInTest = new SessionEndHandler();
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
        assertThat(handlerInTest.handle(fakeInput, fakeRequest).isPresent()).isFalse();
    }
}
