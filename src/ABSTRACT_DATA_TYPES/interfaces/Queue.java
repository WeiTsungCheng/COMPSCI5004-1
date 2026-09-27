package ABSTRACT_DATA_TYPES.interfaces;

public interface Queue<E> {
    public boolean isEmpty();
    public int size();
    public E getFirst();
    public void clear();
    public void addLast(E it);
    public E removeFirst();
}