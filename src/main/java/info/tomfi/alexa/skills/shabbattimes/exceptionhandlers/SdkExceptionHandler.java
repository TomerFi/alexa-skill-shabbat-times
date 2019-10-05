package info.tomfi.alexa.skills.shabbattimes.exceptionhandlers;

import java.util.Optional;

import com.amazon.ask.dispatcher.exception.ExceptionHandler;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.exception.AskSdkException;
import com.amazon.ask.model.Response;

public final class SdkExceptionHandler implements ExceptionHandler
{
    @Override
    public boolean canHandle(final HandlerInput input, Throwable throwable) {
        return throwable instanceof AskSdkException;
    }

    @Override
    public Optional<Response> handle(HandlerInput input, Throwable throwable) {
        return input.getResponseBuilder()
            .withSpeech("I'm sorry. I am unable to help you at the moment. Please try again later!")
            .withShouldEndSession(true)
            .build();
    }
}