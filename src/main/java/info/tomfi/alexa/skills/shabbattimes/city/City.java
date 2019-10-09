package info.tomfi.alexa.skills.shabbattimes.city;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import info.tomfi.alexa.skills.shabbattimes.tools.DynTypeIterator;

public final class City implements Iterable<String>
{
    private String cityName;
    private String geoName;
    private int geoId;
    private String countryAbbreviation;

    private String[] aliases;

    public String getCityName()
    {
        return cityName;
    }

    public String getGeoName()
    {
        return geoName;
    }

    public int getGeoId()
    {
        return geoId;
    }

    public String getCountryAbbreviation()
    {
        return countryAbbreviation;
    }

    public Iterator<String> iterator()
    {
        final List<String> nameList = new ArrayList<>(Arrays.asList(aliases));
        nameList.add(cityName);
        return new DynTypeIterator<String>(nameList);
    }
}
