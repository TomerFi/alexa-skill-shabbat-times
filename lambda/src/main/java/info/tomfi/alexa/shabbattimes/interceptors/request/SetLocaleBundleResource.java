package info.tomfi.alexa.shabbattimes.interceptors.request;

import static info.tomfi.alexa.shabbattimes.AttributeKey.L10N_BUNDLE;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.interceptor.RequestInterceptor;
import info.tomfi.alexa.shabbattimes.TextService;
import java.util.Locale;
import org.springframework.stereotype.Component;

/** Request inteceptor loading the resource bundle for the request locale as a request attribute. */
@Component
public final class SetLocaleBundleResource implements RequestInterceptor {
  private final TextService textor;

  public SetLocaleBundleResource(final TextService setTextor) {
    textor = setTextor;
  }

  @Override
  public void process(final HandlerInput input) {
    var bundle = textor.getResource(new Locale(input.getRequest().getLocale()));
    var attribs = input.getAttributesManager().getRequestAttributes();
    attribs.put(L10N_BUNDLE.toString(), bundle);
    input.getAttributesManager().setRequestAttributes(attribs);
  }
}
