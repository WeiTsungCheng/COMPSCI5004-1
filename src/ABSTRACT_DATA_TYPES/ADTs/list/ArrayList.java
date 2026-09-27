package ABSTRACT_DATA_TYPES.ADTs.list;

import ABSTRACT_DATA_TYPES.interfaces.List;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayList<E> implements List<E> {
    private E[] elems;
    private int size;

    public ArrayList(int cap) {
        elems = (E[]) new Object[cap];
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    };

    public int size() {
        return size;
    };

    public E get(int p) {
        if (p < 0 || p >= size) throw new IndexOutOfBoundsException();
        return elems[p];
    };

    public void clear(){
        size = 0;
    };

    public void set(int p, E it) {
        if (p < 0 || p >= size) throw new IndexOutOfBoundsException();
        elems[p] = it;
    };

    public void add(int p, E it) {
        if (p < 0 || p > size) throw new IndexOutOfBoundsException();
        if (size == elems.length) throw new IllegalStateException("List is full");

        for (int i = size; i > p; i--) {
            elems[i] = elems[i - 1];
        }
        elems[p] = it;
        size++;
    };

    public void addLast(E it) {
        add(size, it);
    };

    public E remove(int p) {
        if (p < 0 || p >= size) throw new IndexOutOfBoundsException();

        E toReturn = elems[p];
        for (int i = p; i < size - 1; i++) {
            elems[i] = elems[i + 1];
        }
        size--;
        elems[size] = null;

        return toReturn;
    };

    public Iterator<E> iterator() {
        return new ArrayList.ArrayIterator();
    };

    private class ArrayIterator implements Iterator<E> {

        private int current = 0;

        public boolean hasNext() {
            return current < size;
        }

        public E next() {
            if (!hasNext()) throw new NoSuchElementException();
            return elems[current++];
        }
    }
}