package info.tomfi.alexa.shabbattimes.handlers.session;

import static org.assertj.core.api.Assertions.assertThat;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.SessionEndedRequest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/** Test cases for the session end request handler. */
@ExtendWith(MockitoExtension.class)
@Tag("unit-tests")
final class SessionEndHandlerTest {
  @Mock private HandlerInput input;
  @Mock private SessionEndedRequest request;
  @InjectMocks private SessionEndHandler sut;

  @Test
  void can_handle_should_always_return_true_for_typed_request_handlers() {
    assertThat(sut.canHandle(input, request)).isTrue();
  }

  @Test
  void handle_should_always_return_empty_response() {
    assertThat(sut.handle(input, request)).isEmpty();
  }
}
