module info.tomfi.shabbat.api {
  exports info.tomfi.hebcal.shabbat.request;
  exports info.tomfi.hebcal.shabbat.response;

  provides info.tomfi.hebcal.shabbat.ShabbatAPI with info.tomfi.hebcal.shabbat.impl.ShabbatAPIProvider;

  requires java.net.http;
  requires com.fasterxml.jackson.annotation;
  requires com.fasterxml.jackson.core;
  requires com.fasterxml.jackson.databind;
  requires auto.value.annotations;
  requires com.google.auto.service;
  requires jsr305;
}
