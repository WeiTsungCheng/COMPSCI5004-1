package ABSTRACT_DATA_TYPES;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedList<E> implements List<E> {

    private Node<E> first;
    private Node<E> last;
    private int size;

    public LinkedList() {
        first = last = null;
        size = 0;
    }

    private static class Node<E> {
        public E element;
        public Node<E> next;

        public Node(E x, Node<E> n) {
            element = x;
            next = n;
        }
    }

    public boolean isEmpty() {
        return size == 0;
    };

    public int size() {
        return size;
    };

    public E get(int p) {
        return locate(p).element;
    };

    public void clear() {
        first = last = null;
        size = 0;
    };

    public void set(int p, E it) {
        Node<E> node = locate(p);
        node.element = it;
    };

    public void add(int p, E it) {
        if (p < 0 || p > size) throw new IndexOutOfBoundsException();

        if (p == 0) {
            first = new Node<E>(it, first);
            if (size == 0) {
                last = first;
            }
        } else {
            Node<E> prev = locate(p - 1);
            Node<E> newNode = new Node<E>(it, prev.next);
            prev.next = newNode;

            if (newNode.next == null) {
                last = newNode;
            }
        }
        size++;
    };

    public void addLast(E it) {
        Node<E> newNode = new Node<E>(it, null);
        if (size == 0) {
            first = newNode;
        } else {
            last.next = newNode;
        }
        last = newNode;
        size++;
    };

    public E remove(int p) {
        if (p < 0 || p >= size) throw new IndexOutOfBoundsException();

        E removedElement;
        if (p == 0) {
            removedElement = first.element;
            first = first.next;
            if (size == 1) {
                last = null;
            }
        } else {
            Node<E> prev = locate(p - 1);
            Node<E> current = prev.next;
            removedElement = current.element;

            prev.next = current.next;
            if (current.next == null) {
                last = prev;
            }
        }
        size--;
        return removedElement;
    };

    public Iterator<E> iterator() {
        return new LinkedListIterator();
    };

    private class LinkedListIterator implements Iterator<E> {
        private Node<E> current = first;

        public boolean hasNext() {
            return current != null;
        }

        public E next() {
            if (!hasNext()) throw new NoSuchElementException();
            E item = current.element;
            current = current.next;
            return item;
        }
    }

    private Node<E> locate(int p) {
        if (p < 0 || p >= size) throw new IndexOutOfBoundsException();
        Node<E> curr = first;
        for (int i = 0; i < p; i++) {
            curr = curr.next;
        }
        return curr;
    }


}
