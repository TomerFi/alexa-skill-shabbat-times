package info.tomfi.alexa.skills.shabbattimes.exception.handlers;

import java.util.Optional;

import com.amazon.ask.dispatcher.exception.ExceptionHandler;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.Response;

import org.springframework.stereotype.Component;

import info.tomfi.alexa.skills.shabbattimes.exception.NoJsonFileException;

@Component
public final class NoJsonFileHandler implements ExceptionHandler
{
    @Override
    public boolean canHandle(final HandlerInput input, final Throwable throwable) {
        return throwable instanceof NoJsonFileException;
    }

    @Override
    public Optional<Response> handle(final HandlerInput input, final Throwable throwable) {
        return input.getResponseBuilder()
            .withSpeech("I'm sorry. Something went wrong. I'm doing my best to resolve this issue. Please try again later. goodbye.")
            .withShouldEndSession(true)
            .build();
    }
}
