package ABSTRACT_DATA_TYPES;

import java.util.EmptyStackException;

public class LinkedStack<E> implements Stack<E> {

    private Node<E> top;

    private static class Node<E> {
        public E element;
        public Node<E> next;

        public Node(E x, Node<E> n) {
            element = x;
            next = n;
        }
    }

    public void push(E it) {
        top = new Node<E>(it, top); // 新節點成為新的節點 top
    };

    public E pop() {
        if (top == null) throw new EmptyStackException();
        E topElem = top.element;
        top = top.next;
        return topElem;
    };

    public E peek() {
        if (top == null) throw new EmptyStackException();
        return top.element;
    };

    public boolean isEmpty() {
        return  (top == null);
    };
}
