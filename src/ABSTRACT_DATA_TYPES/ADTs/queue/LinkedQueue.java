package ABSTRACT_DATA_TYPES.ADTs.queue;

import ABSTRACT_DATA_TYPES.interfaces.Queue;

import java.util.EmptyStackException;
import java.util.NoSuchElementException;


public class LinkedQueue<E> implements Queue<E> {
    private Node<E> front, rear;
    private int size;

    private static class Node<E> {
        public E element;
        public Node<E> next;

        public Node(E x, Node<E> s) {
            element = x;
            next = s;
        }
    }

    public LinkedQueue() {
        front = rear = null;
        size = 0;
    };

    public boolean isEmpty() {
        return front == null;
    };

    public int size() {
        return size;
    };

    public E getFirst() {
        if (front == null) throw new NoSuchElementException();
        return front.element;
    };

    public void clear() {
        front = rear = null;
        size = 0;
    };

    public void addLast(E it) {
        Node<E> newest = new Node<>(it, null);
        if (rear != null) {
            rear.next = newest;
        } else {
            front = newest;
        }
        rear = newest;
        size++;
    };

    public E removeFirst() {
        if (front == null) throw new NoSuchElementException();
        E frontElem = front.element;
        front = front.next;
        if (front == null) rear = null;
        size--;
        return frontElem;
    };
}
