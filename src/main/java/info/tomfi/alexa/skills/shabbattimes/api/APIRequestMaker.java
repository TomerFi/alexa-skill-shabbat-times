package info.tomfi.alexa.skills.shabbattimes.api;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;

import info.tomfi.alexa.skills.shabbattimes.api.enums.FlagStates;
import info.tomfi.alexa.skills.shabbattimes.api.enums.GeoTypes;
import info.tomfi.alexa.skills.shabbattimes.api.enums.OutputTypes;
import info.tomfi.alexa.skills.shabbattimes.api.enums.ParamKeys;
import info.tomfi.alexa.skills.shabbattimes.api.response.ApiResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import lombok.val;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * Builder class for constructing and sending API requests.
 *
 * @author Tomer Figenblat {@literal <tomer.figenblat@gmail.com>}
 */
public final class ApiRequestMaker
{
    private static String DEFAULT_HAVDALAH = "50";
    private static String DEFAULT_CANDLE_LIGHTING = "18";

    private final Map<String, String> queryParams;

    @Autowired private GenericUrl apiUrl;
    @Autowired private HttpTransport transport;
    @Autowired private HttpRequestInitializer initializer;

    /**
     * Main and only constructor, instantiates the query param map with the param default values.
     */
    public ApiRequestMaker()
    {
        queryParams = new ConcurrentHashMap<>();
        queryParams.put(ParamKeys.OUTPUT_FORMAT.getKey(), OutputTypes.JSON.getType());
        queryParams.put(ParamKeys.INCLUDE_TURAH_HAFTARAH.getKey(), FlagStates.OFF.getState());
        queryParams.put(ParamKeys.ASHKENAZIS_TRANSLITERATIONS.getKey(), FlagStates.OFF.getState());
        queryParams.put(ParamKeys.GEO_TYPE.getKey(), GeoTypes.GEO_NAME.getType());
        queryParams.put(ParamKeys.HAVDALAH.getKey(), DEFAULT_HAVDALAH);
        queryParams.put(ParamKeys.CANDLE_LIGHTING.getKey(), DEFAULT_CANDLE_LIGHTING);
    }

    /**
     * Set the the minutes after sundown of the shabbat end for calculation of the shabbat end time.
     *
     * Default mintues if not set is {@value #DEFAULT_HAVDALAH}.
     *
     * @param minutes integer representing the minutes to set.
     * @return this builder object.
     * @throws IllegalArgumentException when the minutes value is smaller then 1.
     */
    public ApiRequestMaker setHavdalahMinutesAfterSundown(final int minutes)
        throws IllegalArgumentException
    {
        if (minutes <= 0)
        {
            throw new IllegalArgumentException(String.join(", ",
                "havdalah minutes should be bigger the 0",
                "otherwise we can't calculate the shabbat end time."
                )
            );
        }
        queryParams.put(ParamKeys.HAVDALAH.getKey(), String.valueOf(minutes));
        return this;
    }

    /**
     * Set the the minutes before sundown of the shabbat start for calculation
     * of the shabbat start time.
     *
     * Default mintues if not set is {@value #DEFAULT_CANDLE_LIGHTING}.
     *
     * @param minutes integer representing the minutes to set.
     * @return this builder object.
     * @throws IllegalArgumentException when the minutes value is smaller then 0.
     */
    public ApiRequestMaker setCandleLightingMinutesBeforeSunset(final int minutes)
        throws IllegalArgumentException
    {
        if (minutes < 0)
        {
            throw new IllegalArgumentException(
                "candle lighting time before sunset should a positive integer."
            );
        }
        queryParams.put(ParamKeys.CANDLE_LIGHTING.getKey(), String.valueOf(minutes));
        return this;
    }

    /**
     * Set the the geoid of the requested city.
     *
     * @param setGeoId integer representing the geoid to set.
     * @return this builder object.
     * @throws IllegalArgumentException when the geoid value is smaller then 1.
     */
    public ApiRequestMaker setGeoId(final int setGeoId) throws IllegalArgumentException
    {
        if (setGeoId <= 0)
        {
            throw new IllegalArgumentException("geo id should a positive integer.");
        }
        queryParams.put(ParamKeys.GEO_ID.getKey(), String.valueOf(setGeoId));
        return this;
    }

    /**
     * Set the LocalDate object for the request.
     *
     * @param dateTime the date object to set.
     * @return this builder object.
     */
    public ApiRequestMaker setSpecificDate(final LocalDate dateTime)
    {
        val year = String.valueOf(dateTime.getYear());
        val month = String.format("0%s", String.valueOf(dateTime.getMonthValue()));
        val day = String.format("0%s", String.valueOf(dateTime.getDayOfMonth()));

        queryParams.put(ParamKeys.GREGORIAN_YEAR.getKey(), year);
        queryParams.put(ParamKeys.GREGORIAN_MONTH.getKey(), month.substring(month.length() - 2));
        queryParams.put(ParamKeys.GREGORIAN_DAY.getKey(), day.substring(month.length() - 2));
        return this;
    }

    /**
     * Send the api http request.
     *
     * @return the {@link info.tomfi.alexa.skills.shabbattimes.api.response.ApiResponse}
     *     represnting the response consumed from the api.
     * @throws IllegalStateException if the geod id is no set prior to this method invoking.
     * @throws IOException when failed to execute the http request.
     */
    public ApiResponse send() throws IllegalStateException, IOException
    {
        if (!queryParams.containsKey(ParamKeys.GEO_ID.getKey()))
        {
            throw new IllegalStateException(
                "we need the requested city geo id for build the request."
            );
        }
        val requestFactory = transport.createRequestFactory(initializer);
        apiUrl.putAll(queryParams);
        val request = requestFactory.buildGetRequest(apiUrl);
        return request.execute().parseAs(ApiResponse.class);
    }
}
