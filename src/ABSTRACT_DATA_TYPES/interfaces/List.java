
package ABSTRACT_DATA_TYPES.interfaces;
import java.util.Iterator;

public interface List<E> {
    public boolean isEmpty();
    public int size();
    public E get(int p);

    public void clear();
    public void set(int p, E it);
    public void add(int p, E it);
    public void addLast(E it);
    public E remove(int p);

    public boolean equals(List<E> that);
    public void addAll(List<E> that);

    public Iterator<E> iterator();
}
