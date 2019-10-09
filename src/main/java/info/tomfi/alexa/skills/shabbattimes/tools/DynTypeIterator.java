package info.tomfi.alexa.skills.shabbattimes.tools;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public final class DynTypeIterator<E> implements Iterator<E>
{
    private final List<E> list;
    private int idx;

    public DynTypeIterator(final List<E> setList)
    {
        list = setList;
    }

    @Override
    public boolean hasNext()
    {
        return idx < list.size();
    }

    @Override
    public E next() throws NoSuchElementException
    {
        if (hasNext())
        {
            return list.get(idx++);
        }
        throw new NoSuchElementException();
    }
}
