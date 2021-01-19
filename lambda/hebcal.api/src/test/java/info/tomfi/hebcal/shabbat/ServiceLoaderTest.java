package info.tomfi.hebcal.shabbat;

import static org.assertj.core.api.BDDAssertions.then;

import java.lang.reflect.InvocationTargetException;
import java.util.ServiceLoader;
import org.junit.jupiter.api.Test;

import info.tomfi.hebcal.shabbat.impl.ShabbatAPIProvider;

final class ServiceLoaderTest {
  @Test
  void load_service_with_default_empty_ctor_return_instantiated_provider()
      throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
    var impl = ServiceLoader.load(ShabbatAPI.class).findFirst().get();
    then(impl).isInstanceOf(ShabbatAPIProvider.class);

    var endpointField = impl.getClass().getDeclaredField("endpoint");
    endpointField.setAccessible(true);

    then((String) endpointField.get(impl)).isEqualTo("https://www.hebcal.com/shabbat/");
  }

  @Test
  void load_service_via_reflection_with_custom_ctor_validate_instantiated_provider()
      throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
      NoSuchMethodException, SecurityException, NoSuchFieldException {
    var type = ServiceLoader.load(ShabbatAPI.class).stream().findFirst().get().type();
    var impl = type.getDeclaredConstructor(String.class).newInstance("fake.url.com:1234/blabla");
    then(impl).isInstanceOf(ShabbatAPIProvider.class);

    var endpointField = type.getDeclaredField("endpoint");
    endpointField.setAccessible(true);

    then((String) endpointField.get(impl)).isEqualTo("fake.url.com:1234/blabla");
  }
}
