package info.tomfi.alexa.skills.shabbattimes.city;

import static lombok.AccessLevel.PROTECTED;

import info.tomfi.alexa.skills.shabbattimes.tools.DynTypeIterator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.val;

/**
 * Pojo for creatign City objects from the backend json files.
 *
 * @author Tomer Figenblat {@literal <tomer.figenblat@gmail.com>}
 */
@NoArgsConstructor(access = PROTECTED)
@SuppressWarnings("PMD.ShortClassName")
public final class City implements Iterable<String>
{
    @Getter private String cityName;
    @Getter private String geoName;
    @Getter private int geoId;
    @Getter private String countryAbbreviation;

    private String[] aliases;

    @Override
    public Iterator<String> iterator()
    {
        val nameList = new ArrayList<>(Arrays.asList(aliases));
        nameList.add(cityName);
        return new DynTypeIterator<String>(nameList);
    }
}
