package ABSTRACT_DATA_TYPES.ADTs.stack;

import ABSTRACT_DATA_TYPES.interfaces.Stack;

import java.util.EmptyStackException;

public class ArrayStack <E> implements Stack<E>{

    private E[] elems;
    private int size;

    public ArrayStack(int cap) {
        elems = (E[]) new Object[cap];
        size = 0;
    };

    public boolean isEmpty() {
        return size == 0;
    };

    public E peek() {
        if (size == 0) {
            throw new EmptyStackException();
        }
        return (E) elems[size - 1];
    };

    public void push(E it) {
       if (size == elems.length) {
           throw new IllegalStateException("Stack is full");
       }
       elems[size++] = it;
    };

    public E pop() {
        if (size == 0) {
            throw new EmptyStackException();
        }
        E topElem = elems[--size];
        elems[size] = null;
        return topElem;
    };

}
