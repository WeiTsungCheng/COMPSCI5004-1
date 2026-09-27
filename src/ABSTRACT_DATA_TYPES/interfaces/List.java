package ABSTRACT_DATA_TYPES.interfaces;
import java.util.Iterator;
import java.util.NoSuchElementException;

public interface List<E> {
    public boolean isEmpty();
    public int size();
    public E get(int p);

    public void clear();
    public void set(int p, E it);
    public void add(int p, E it);
    public void addLast(E it);
    public E remove(int p);

    public Iterator<E> iterator();
}
