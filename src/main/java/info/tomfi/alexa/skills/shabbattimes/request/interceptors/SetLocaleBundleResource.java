package info.tomfi.alexa.skills.shabbattimes.request.interceptors;

import static info.tomfi.alexa.skills.shabbattimes.tools.GlobalEnums.Attributes.L10N_BUNDLE;

import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.request.interceptor.GenericRequestInterceptor;

public final class SetLocaleBundleResource implements GenericRequestInterceptor<HandlerInput>
{
    private static final String L10N_BASE_NAME = "Responses";

    @Override
    public void process(final HandlerInput input)
    {
        ResourceBundle bundle;
        try
        {
            bundle = ResourceBundle.getBundle(L10N_BASE_NAME, new Locale(input.getRequest().getLocale()));
        } catch(MissingResourceException exc)
        {
            bundle = ResourceBundle.getBundle(L10N_BASE_NAME, Locale.US);
        }

        final Map<String, Object> attribs = input.getAttributesManager().getRequestAttributes();
        attribs.put(L10N_BUNDLE.name, bundle);
        input.getAttributesManager().setRequestAttributes(attribs);
    }
}
