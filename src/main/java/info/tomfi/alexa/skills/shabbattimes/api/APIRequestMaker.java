package info.tomfi.alexa.skills.shabbattimes.api;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;

import info.tomfi.alexa.skills.shabbattimes.api.enums.*;
import info.tomfi.alexa.skills.shabbattimes.api.response.APIResponse;

public final class APIRequestMaker
{
    private static String DEFAULT_HAVDALAH = "50";
    private static String DEFAULT_CANDLE_LIGHTING = "18";

    private final HttpTransport transport;
    private final String baseUrl;
    private final Map<String, String> queryParams;

    public APIRequestMaker(final String setBaseUrl)
    {
        baseUrl = setBaseUrl;
        transport = new NetHttpTransport();

        queryParams = new ConcurrentHashMap<>();
        queryParams.put(ParamKeys.OUTPUT_FORMAT.getKey(), OutputTypes.JSON.getType());
        queryParams.put(ParamKeys.INCLUDE_TURAH_HAFTARAH.getKey(), FlagStates.OFF.getState());
        queryParams.put(ParamKeys.ASHKENAZIS_TRANSLITERATIONS.getKey(), FlagStates.OFF.getState());
        queryParams.put(ParamKeys.GEO_TYPE.getKey(), GeoTypes.GEO_NAME.getType());
        queryParams.put(ParamKeys.HAVDALAH.getKey(), DEFAULT_HAVDALAH);
        queryParams.put(ParamKeys.CANDLE_LIGHTING.getKey(), DEFAULT_CANDLE_LIGHTING);
    }

    public APIRequestMaker setHavdalahMinutesAfterSundown(final int minutes) throws IllegalArgumentException
    {
        if (minutes <= 0)
        {
            throw new IllegalArgumentException("havdalah minutes should be bigger the 0, otherwise we can't calculate the shabbat end time.");
        }
        queryParams.put(ParamKeys.HAVDALAH.getKey(), String.valueOf(minutes));
        return this;
    }

    public APIRequestMaker setCandleLightingMinutesBeforeSunset(final int minutes) throws IllegalArgumentException
    {
        if (minutes < 0)
        {
            throw new IllegalArgumentException("candle lighting time before sunset should a positive integer.");
        }
        queryParams.put(ParamKeys.CANDLE_LIGHTING.getKey(), String.valueOf(minutes));
        return this;
    }

    public APIRequestMaker setGeoId(final int setGeoId) throws IllegalArgumentException
    {
        if (setGeoId <= 0)
        {
            throw new IllegalArgumentException("geo id should a positive integer.");
        }
        queryParams.put(ParamKeys.GEO_ID.getKey(), String.valueOf(setGeoId));
        return this;
    }

    public APIRequestMaker setSpecificDate(final LocalDate dateTime)
    {
        final String year = String.valueOf(dateTime.getYear());
        final String month = String.format("0%s", String.valueOf(dateTime.getMonthValue()));
        final String day = String.format("0%s", String.valueOf(dateTime.getDayOfMonth()));

        queryParams.put(ParamKeys.GREGORIAN_YEAR.getKey(), year);
        queryParams.put(ParamKeys.GREGORIAN_MONTH.getKey(), month.substring(month.length() - 2));
        queryParams.put(ParamKeys.GREGORIAN_DAY.getKey(), day.substring(month.length() - 2));
        return this;
    }

    public APIResponse send() throws IllegalStateException, IOException
    {
        if (!queryParams.containsKey(ParamKeys.GEO_ID.getKey()))
        {
            throw new IllegalStateException("we need the requested city geo id for build the request.");
        }
        final HttpRequestFactory requestFactory = transport.createRequestFactory(new APIRequestInitializer());
        final GenericUrl url = new GenericUrl(baseUrl);
        url.putAll(queryParams);
        final HttpRequest request = requestFactory.buildGetRequest(url);
        return request.execute().parseAs(APIResponse.class);
    }
}
