package ABSTRACT_DATA_TYPES;

import java.util.EmptyStackException;
import java.util.NoSuchElementException;

public class ArrayQueue<E> implements Queue<E>{
    private E[] elems;
    private int size, front, rear;

    public ArrayQueue(int cap) {
        // E[] = 資料結構（data structure / implementation）
        elems = (E[]) new Object[cap];
        size = 0;
        front = rear = 0;
    };

    public boolean isEmpty() {
        return (size == 0);
    };

    public int size() {
        return size;
    };

    public E getFirst() {
        if (size == 0) throw new EmptyStackException();
        return elems[front];
    };

    public void clear() {
        size = front = rear = 0;
    };

    public void addLast(E it) {
        if (size == elems.length) throw new IllegalStateException("Queue is full");
        elems[rear++] = it;
        if (rear == elems.length) rear = 0;
        size++;
    };

    public E removeFirst() {
        if (size == 0) throw new NoSuchElementException();
        E frontElem = elems[front];
        elems[front++] = null;
        if (front == elems.length) front = 0;
        size--;
        return frontElem;
    };
}
