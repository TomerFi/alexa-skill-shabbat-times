/**
 * Copyright 2019 Tomer Figenblat
 *
 * <p>Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of the License at
 *
 * <p>http://www.apache.org/licenses/LICENSE-2.0
 *
 * <p>Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing permissions and
 * limitations under the License.
 */
package info.tomfi.alexa.skills.shabbattimes.tools;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;

/**
 * Iterator taking a dynamic type.
 *
 * @author Tomer Figenblat {@literal <tomer.figenblat@gmail.com>}
 * @param <E> type of iterator.
 */
@RequiredArgsConstructor
public final class DynTypeIterator<E> implements Iterator<E> {
  private final List<E> list;
  private int idx;

  @Override
  public boolean hasNext() {
    return idx < list.size();
  }

  @Override
  public E next() throws NoSuchElementException {
    if (hasNext()) {
      return list.get(idx++);
    }
    throw new NoSuchElementException();
  }
}
