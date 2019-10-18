package info.tomfi.alexa.skills.shabbattimes.city;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import info.tomfi.alexa.skills.shabbattimes.tools.DynTypeIterator;

import lombok.Getter;

public final class City implements Iterable<String>
{
    @Getter private String cityName;
    @Getter private String geoName;
    @Getter private int geoId;
    @Getter private String countryAbbreviation;

    private String[] aliases;

    public Iterator<String> iterator()
    {
        final List<String> nameList = new ArrayList<>(Arrays.asList(aliases));
        nameList.add(cityName);
        return new DynTypeIterator<String>(nameList);
    }
}
