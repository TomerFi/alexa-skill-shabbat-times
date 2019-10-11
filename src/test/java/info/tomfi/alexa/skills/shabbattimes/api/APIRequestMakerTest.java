package info.tomfi.alexa.skills.shabbattimes.api;

import static java.util.stream.Collectors.joining;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;

import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockserver.client.MockServerClient;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;
import org.mockserver.model.JsonBody;

import info.tomfi.alexa.skills.shabbattimes.api.response.APIResponse;
import info.tomfi.alexa.skills.shabbattimes.api.response.APIResponseAssert;
import info.tomfi.alexa.skills.shabbattimes.api.response.items.ResponseItem;
import info.tomfi.alexa.skills.shabbattimes.api.response.items.ResponseItemAssert;
import info.tomfi.alexa.skills.shabbattimes.api.response.items.ResponseLocationAssert;

public final class APIRequestMakerTest {
    private APIRequestMaker requestMaker;

    @BeforeEach
    public void initialize() {
        requestMaker = new APIRequestMaker("http://localhost:1234/shabbat");
    }

    @Test
    @DisplayName("test exception thrown by setting illegal havdalah minutes")
    public void setHavdalahMinutes_illegalArgument_throwsExcpetion() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> requestMaker.setHavdalahMinutesAfterSundown(0));
    }

    @Test
    @DisplayName("test exception thrown by setting illegal candle lighting minutes")
    public void setCandleLightingMinutes_illegalArgument_throwsExcpetion() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> requestMaker.setCandleLightingMinutesBeforeSunset(-1));
    }

    @Test
    @DisplayName("test exception thrown by setting illegal geoid")
    public void setGeoId_illegalArgument_throwsExcpetion() {
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> requestMaker.setGeoId(0));
    }

    @Test
    @DisplayName("test exception thrown when trying to send request without setting the geo id")
    public void sendRequest_withoutGeoid_throwsExcpetion() {
        assertThatExceptionOfType(IllegalStateException.class).isThrownBy(() -> requestMaker.send());
    }

    @Test
    @DisplayName("test an api response with a correctly build request using a mock server and client")
    public void sendRequest_buildCorrectly_success() throws IllegalStateException, IOException, URISyntaxException
    {
        requestMaker.setHavdalahMinutesAfterSundown(20);
        requestMaker.setCandleLightingMinutesBeforeSunset(10);
        requestMaker.setGeoId(1);
        requestMaker.setSpecificDate(LocalDateTime.now());

        String responesText;
        try
        (
            Stream<String> lines = Files.lines(Paths.get(APIRequestMakerTest.class.getClassLoader().getResource("response_full.json").toURI()))
        )
        {
            responesText = lines.collect(joining(System.lineSeparator()));
        }

        final ClientAndServer mockServer = ClientAndServer.startClientAndServer(1234);
        final MockServerClient mockClient = new MockServerClient("localhost", mockServer.getLocalPort());
        mockClient
        .when(
            HttpRequest.request()
                .withMethod("GET")
                .withPath("/shabbat")
        )
        .respond(
            HttpResponse.response()
                .withStatusCode(200)
                .withBody(JsonBody.json(responesText))
        );

        final APIResponse response = requestMaker.send();

        APIResponseAssert.assertThat(response)
            .titleIs("testTitle")
            .dateIs("testDate")
            .linkIs("testLink");

        ResponseLocationAssert.assertThat(response.getLocation())
            .latitudeIs(25.25)
            .longitudeIs(26.26)
            .geonameidIs(2526)
            .cityIs("testCity")
            .asciinameIs("testAsciiname")
            .titleIs("testTitle")
            .tzidIs("testTzid")
            .admin1Is("testAdmin1")
            .countryIs("testCountry")
            .geoIs("testGeo");

        for (ResponseItem item : response.getItems())
        {
            ResponseItemAssert.assertThat(item)
                .titleIs("testTitle")
                .categoryIs("testCategory")
                .dateIs("testDate")
                .hebrewIs("testHebrew");
        }

        mockClient.close();
        mockServer.stop();
    }
}
