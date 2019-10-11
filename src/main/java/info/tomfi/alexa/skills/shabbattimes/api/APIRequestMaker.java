package info.tomfi.alexa.skills.shabbattimes.api;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;

import info.tomfi.alexa.skills.shabbattimes.api.enums.FlagStates;
import info.tomfi.alexa.skills.shabbattimes.api.enums.GeoTypes;
import info.tomfi.alexa.skills.shabbattimes.api.enums.OutputTypes;
import info.tomfi.alexa.skills.shabbattimes.api.enums.ParamKeys;
import info.tomfi.alexa.skills.shabbattimes.api.response.APIResponse;

public final class APIRequestMaker
{
    private static String BASE_URL = "https://www.hebcal.com/shabbat/";
    private static String DEFAULT_HAVDALAH = "50";
    private static String DEFAULT_CANDLE_LIGHTING = "18";

    private final HttpTransport transport;

    private Map<String, String> queryParams;

    public APIRequestMaker()
    {
        transport = new NetHttpTransport();

        queryParams = new ConcurrentHashMap<>();
        queryParams.put(ParamKeys.OUTPUT_FORMAT.key, OutputTypes.JSON.type);
        queryParams.put(ParamKeys.INCLUDE_TURAH_HAFTARAH.key, FlagStates.OFF.state);
        queryParams.put(ParamKeys.ASHKENAZIS_TRANSLITERATIONS.key, FlagStates.OFF.state);
        queryParams.put(ParamKeys.GEO_TYPE.key, GeoTypes.GEO_NAME.type);
        queryParams.put(ParamKeys.HAVDALAH.key, DEFAULT_HAVDALAH);
        queryParams.put(ParamKeys.CANDLE_LIGHTING.key, DEFAULT_CANDLE_LIGHTING);
    }

    public APIRequestMaker setHavdalahMinutesAfterSundown(final int minutes) throws IllegalArgumentException
    {
        if (minutes <= 0)
        {
            throw new IllegalArgumentException("havdalah minutes should be bigger the 0, otherwise we can't calculate the shabbat end time.");
        }
        queryParams.put(ParamKeys.HAVDALAH.key, String.valueOf(minutes));
        return this;
    }

    public APIRequestMaker setCandleLightingMinutesBeforeSunset(final int minutes) throws IllegalArgumentException
    {
        if (minutes < 0)
        {
            throw new IllegalArgumentException("candle lighting time before sunset should a positive integer.");
        }
        queryParams.put(ParamKeys.CANDLE_LIGHTING.key, String.valueOf(minutes));
        return this;
    }

    public APIRequestMaker setGeoId(final int setGeoId) throws IllegalArgumentException
    {
        if (setGeoId <= 0)
        {
            throw new IllegalArgumentException("geo id should a positive integer.");
        }
        queryParams.put(ParamKeys.GEO_ID.key, String.valueOf(setGeoId));
        return this;
    }

    public APIRequestMaker setSpecificDate(final LocalDateTime dateTime)
    {
        final String year = String.valueOf(dateTime.getYear());
        final String month = String.format("0%s", String.valueOf(dateTime.getMonthValue()));
        final String day = String.format("0%s", String.valueOf(dateTime.getDayOfMonth()));

        queryParams.put(ParamKeys.GREGORIAN_YEAR.key, year);
        queryParams.put(ParamKeys.GREGORIAN_MONTH.key, month.substring(month.length() - 2));
        queryParams.put(ParamKeys.GREGORIAN_DAY.key, day.substring(month.length() - 2));
        return this;
    }

    public APIResponse send() throws IllegalStateException, IOException
    {
        if (!queryParams.containsKey(ParamKeys.GEO_ID.key))
        {
            throw new IllegalStateException("we need the requested city geo id for build the request.");
        }
        final HttpRequestFactory requestFactory = transport.createRequestFactory(new APIRequestInitializer());
        final GenericUrl url = new GenericUrl(BASE_URL);
        url.putAll(queryParams);
        final HttpRequest request = requestFactory.buildGetRequest(url);
        return request.execute().parseAs(APIResponse.class);
    }
}
