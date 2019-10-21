package info.tomfi.alexa.skills.shabbattimes.tools;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import lombok.RequiredArgsConstructor;

/**
 * Iterator taking a dynamic type.
 *
 * @author Tomer Figenblat {@literal <tomer.figenblat@gmail.com>}
 *
 * @param <E> type of iterator.
 */
@RequiredArgsConstructor
public final class DynTypeIterator<E> implements Iterator<E>
{
    private final List<E> list;
    private int idx;

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasNext()
    {
        return idx < list.size();
    }

    /**
     * {@inheritDoc}
     */
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
