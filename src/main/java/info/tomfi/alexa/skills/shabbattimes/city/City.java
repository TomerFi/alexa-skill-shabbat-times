package info.tomfi.alexa.skills.shabbattimes.city;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import info.tomfi.alexa.skills.shabbattimes.tools.DynTypeIterator;

public final class City implements Iterable<String>
{
    private String cityName;
    private String geoName;
    private int geoId;

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

    public Iterator<String> iterator()
    {
        final List<String> nameList = Arrays.asList(aliases);
        nameList.add(cityName);
        return new DynTypeIterator<String>(nameList);
    }
}
