/**
 * Copyright Tomer Figenblat
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
module info.tomfi.hebcal.shabbat {
  exports info.tomfi.hebcal.shabbat.request;
  exports info.tomfi.hebcal.shabbat.response;

  uses info.tomfi.hebcal.shabbat.ShabbatAPI;
  provides info.tomfi.hebcal.shabbat.ShabbatAPI
    with info.tomfi.hebcal.shabbat.impl.ShabbatAPIProvider;

  requires java.net.http;
  requires com.google.auto.service;
  requires com.fasterxml.jackson.annotation;
  requires com.fasterxml.jackson.core;
  requires com.fasterxml.jackson.databind;

  requires auto.value.annotations;
  requires jsr305;
}
