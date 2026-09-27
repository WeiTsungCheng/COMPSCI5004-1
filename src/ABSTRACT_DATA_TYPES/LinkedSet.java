package ABSTRACT_DATA_TYPES;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedSet<E extends Comparable<E>> implements Set<E> {
    private Node<E> first;
    private int size;

    private static class Node<E> {
        public E element;
        public Node<E> next;
        public Node(E e, Node<E> n) {
            element = e;
            next = n;
        }
    }

    public LinkedSet() {
        first = null;
        size = 0;
    }

    @Override
    public boolean isEmpty() { return size == 0; }

    @Override
    public int size() { return size; }

    @Override
    public void clear() {
        first = null;
        size = 0;
    }

    @Override
    public boolean contains(E it) {
        Node<E> curr = first;
        while (curr != null) {
            int cmp = curr.element.compareTo(it);
            if (cmp == 0) return true;
            if (cmp > 0) return false; // 因為已排序，超過就代表不存在
            curr = curr.next;
        }
        return false;
    }

    @Override
    public void add(E it) {
        if (first == null || first.element.compareTo(it) > 0) {
            first = new Node<>(it, first);
            size++;
            return;
        }

        Node<E> curr = first;
        while (curr.next != null) {
            int cmp = curr.next.element.compareTo(it);
            if (cmp == 0) return;
            if (cmp > 0) break;
            curr = curr.next;
        }

        if (curr.element.compareTo(it) == 0) return;

        curr.next = new Node<>(it, curr.next);
        size++;
    }

    @Override
    public void remove(E it) {

        if (first == null) return;

        if (first.element.compareTo(it) == 0) {
            first = first.next;
            size--;
            return;
        }

        Node<E> curr = first;
        while (curr.next != null) {
            int cmp = curr.next.element.compareTo(it);
            if (cmp == 0) {
                curr.next = curr.next.next;
                size--;
                return;
            }
            if (cmp > 0) return;
            curr = curr.next;
        }
    }

    @Override
    public boolean equals(Set<E> that) {
        if (this.size != that.size()) return false;

        Iterator<E> iter1 = this.iterator();
        Iterator<E> iter2 = that.iterator();
        while (iter1.hasNext()) {
            if (iter1.next().compareTo(iter2.next()) != 0) return false;
        }
        return true;
    }

    @Override
    public boolean containsAll(Set<E> that) {
        Iterator<E> iter = that.iterator();
        while (iter.hasNext()) {
            if (!this.contains(iter.next())) return false;
        }
        return true;
    }

    @Override
    public void addAll(Set<E> that) {
        Iterator<E> iter = that.iterator();
        while (iter.hasNext()) {
            this.add(iter.next());
        }
    }

    @Override
    public void removeAll(Set<E> that) {
        Iterator<E> iter = that.iterator();
        while (iter.hasNext()) {
            this.remove(iter.next());
        }
    }

    @Override
    public void retainAll(Set<E> that) {
        Node<E> curr = first;
        while (curr != null) {
            E element = curr.element;
            curr = curr.next;
            if (!that.contains(element)) {
                this.remove(element);
            }
        }
    }

    @Override
    public Iterator<E> iterator() {
        return new LinkedSetIterator();
    }

    private class LinkedSetIterator implements Iterator<E> {
        private LinkedSet.Node<E> current = first;
        public boolean hasNext() { return current != null; }
        public E next() {
            if (!hasNext()) throw new NoSuchElementException();
            E item = current.element;
            current = current.next;
            return item;
        }
    }
}
