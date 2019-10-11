package info.tomfi.alexa.skills.shabbattimes.api.response.items;

import com.google.api.client.util.Key;

public final class ResponseLocation
{
    @Key("admin1") private String admin1;
    @Key("asciiname") private String asciiname;
    @Key("city") private String city;
    @Key("country") private String country;
    @Key("geo") private String geo;
    @Key("geonameid") private int geonameid;
    @Key("latitude") private Double latitude;
    @Key("longitude") private Double longitude;
    @Key("title") private String title;
    @Key("tzid") private String tzid;

    public String getAdmin1()
    {
        return admin1;
    }

    public String getAsciiname()
    {
        return asciiname;
    }

    public String getCity()
    {
        return city;
    }

    public String getCountry()
    {
        return country;
    }

    public String getGeo()
    {
        return geo;
    }

    public int getGeonameid()
    {
        return geonameid;
    }

    public Double getLatitude()
    {
        return latitude;
    }

    public Double getLongitude()
    {
        return longitude;
    }

    public String getTitle()
    {
        return title;
    }

    public String getTzid()
    {
        return tzid;
    }
}
