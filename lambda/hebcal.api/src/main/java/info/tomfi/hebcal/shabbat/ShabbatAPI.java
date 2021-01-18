package info.tomfi.hebcal.shabbat;

import info.tomfi.hebcal.shabbat.request.Request;
import info.tomfi.hebcal.shabbat.response.Response;
import java.util.concurrent.CompletableFuture;

public interface ShabbatAPI {
  default String getEndpoint() {
    return "https://www.hebcal.com/shabbat/";
  }

  CompletableFuture<Response> sendAsync(Request request);
}
