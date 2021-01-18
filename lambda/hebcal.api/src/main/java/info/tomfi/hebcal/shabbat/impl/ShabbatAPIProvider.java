package info.tomfi.hebcal.shabbat.impl;

import static java.net.http.HttpResponse.BodyHandlers.ofString;
import static java.util.stream.Collectors.joining;

import info.tomfi.hebcal.shabbat.ShabbatAPI;
import info.tomfi.hebcal.shabbat.request.Request;
import info.tomfi.hebcal.shabbat.response.Response;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auto.service.AutoService;

@AutoService(ShabbatAPI.class)
public final class ShabbatAPIProvider implements ShabbatAPI {
  private final HttpClient client;
  private final ObjectMapper mapper;

  public ShabbatAPIProvider() {
    client = HttpClient.newHttpClient();
    mapper = new ObjectMapper();
  }

  @Override
  public CompletableFuture<Response> sendAsync(final Request request) {
    var query = request.queryParams().entrySet().stream().map(e -> String.join("=", e.getKey(), e.getValue()))
        .collect(joining("&"));

    var uri = URI.create(String.join("?", getEndpoint(), query));

    var httpRequest = HttpRequest.newBuilder(uri).header("Accept", "application/json").build();

    return client.sendAsync(httpRequest, ofString()).thenApply(HttpResponse::body).thenApply(s -> {
      try {
        return mapper.readValue(s, Response.class);
      } catch (JsonProcessingException exc) {
        throw new CompletionException(exc);
      }
    });
  }
}
