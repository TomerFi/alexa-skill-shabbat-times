package info.tomfi.alexa.skills.shabbattimes.api;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import info.tomfi.alexa.skills.shabbattimes.api.enums.FlagStates;
import info.tomfi.alexa.skills.shabbattimes.api.enums.GeoTypes;
import info.tomfi.alexa.skills.shabbattimes.api.enums.OutputTypes;
import info.tomfi.alexa.skills.shabbattimes.api.enums.ParamKeys;

public final class APIURLBuilder
{
    private static String BASE_URL = "https://www.hebcal.com/shabbat/";
    private static String DEFAULT_HAVDALAH = "50";
    private static String DEFAULT_CANDLE_LIGHTING = "18";

    private Map<ParamKeys, String> queryParams;

    public APIURLBuilder()
    {
        queryParams = new ConcurrentHashMap<>();
        queryParams.put(ParamKeys.OUTPUT_FORMAT, OutputTypes.JSON.type);
        queryParams.put(ParamKeys.INCLUDE_TURAH_HAFTARAH, FlagStates.OFF.state);
        queryParams.put(ParamKeys.ASHKENAZIS_TRANSLITERATIONS, FlagStates.OFF.state);
        queryParams.put(ParamKeys.GEO_TYPE, GeoTypes.GEO_NAME.type);
        queryParams.put(ParamKeys.HAVDALAH, DEFAULT_HAVDALAH);
        queryParams.put(ParamKeys.CANDLE_LIGHTING, DEFAULT_CANDLE_LIGHTING);
    }

    public APIURLBuilder setHavdalahMinutesAfterSundown(final int minutes) throws IllegalArgumentException
    {
        if (minutes <= 0)
        {
            throw new IllegalArgumentException("havdalah minutes should be bigger the 0, otherwise we can't calculate the shabbat end time.");
        }
        queryParams.put(ParamKeys.HAVDALAH, String.valueOf(minutes));
        return this;
    }

    public APIURLBuilder setCandleLightingMinutesBeforeSunset(final int minutes) throws IllegalArgumentException
    {
        if (minutes < 0)
        {
            throw new IllegalArgumentException("candle lighting time before sunset should a positive integer.");
        }
        queryParams.put(ParamKeys.CANDLE_LIGHTING, String.valueOf(minutes));
        return this;
    }

    public APIURLBuilder setGeoId(final int setGeoId) throws IllegalArgumentException
    {
        if (setGeoId <= 0)
        {
            throw new IllegalArgumentException("geo id should a positive integer.");
        }
        queryParams.put(ParamKeys.GEO_ID, String.valueOf(setGeoId));
        return this;
    }

    public APIURLBuilder setSpecificDate(final LocalDateTime dateTime)
    {
        queryParams.put(ParamKeys.GREGORIAN_YEAR, String.valueOf(dateTime.getYear()));
        queryParams.put(ParamKeys.GREGORIAN_MONTH, String.format("0%s", String.valueOf(dateTime.getMonthValue())).substring(0, 1));
        queryParams.put(ParamKeys.GREGORIAN_DAY, String.format("0%s", String.valueOf(dateTime.getDayOfMonth())).substring(0, 1));
        return this;
    }

    public URL build() throws IllegalStateException, MalformedURLException
    {
        if (!queryParams.containsKey(ParamKeys.GEO_ID))
        {
            throw new IllegalStateException("we need the requested city geo id for build the request.");
        }
        return new URL(BASE_URL);
    }

}
