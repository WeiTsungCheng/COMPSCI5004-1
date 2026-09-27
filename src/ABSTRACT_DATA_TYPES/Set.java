package ABSTRACT_DATA_TYPES;
import java.util.Iterator;

public interface Set<E> {

    public boolean isEmpty();
    public int size();
    public boolean contains(E it);
    public boolean equals(Set<E> that);
    public boolean containsAll(Set<E> that);
    public void clear();
    public void add(E it);
    public void remove(E it);
    public void addAll(Set<E> that);
    public void removeAll(Set<E> that);
    public void retainAll(Set<E> that);

    public Iterator<E> iterator();

}
