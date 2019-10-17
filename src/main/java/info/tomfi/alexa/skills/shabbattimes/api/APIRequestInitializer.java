package info.tomfi.alexa.skills.shabbattimes.api;

import java.io.IOException;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.gson.GsonFactory;

public final class APIRequestInitializer implements HttpRequestInitializer
{
    @Override
    public void initialize(final HttpRequest request) throws IOException {
        request.setParser(new JsonObjectParser(new GsonFactory()));
    }
}
