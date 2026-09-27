package ABSTRACT_DATA_TYPES;

public class Main1 {
    public static void main(String[] args) {

//        1 Stack
        // ArrayStack, LinkedStack
        Stack<Integer> arrayStack = new ArrayStack<>(10);

        arrayStack.push(10);
        arrayStack.push(20);
        arrayStack.push(30);

        System.out.println(arrayStack.pop());  // 30
        System.out.println(arrayStack.peek()); // 20
        System.out.println(arrayStack.pop());  // 20

        Stack<Integer> linkedStack = new LinkedStack<>();

        linkedStack.push(10);
        linkedStack.push(20);
        linkedStack.push(30);

        System.out.println(linkedStack.pop());  // 30
        System.out.println(linkedStack.peek()); // 20
        System.out.println(linkedStack.pop());  // 20

//        2 Queue
        //  ArrayQueue, LinkedQueue
        Queue<String> arrayQueue = new ArrayQueue<>(10);

        arrayQueue.addLast("A");
        arrayQueue.addLast("B");
        arrayQueue.addLast("C");

        System.out.println(arrayQueue.removeFirst()); // A
        System.out.println(arrayQueue.getFirst()); // B
        System.out.println(arrayQueue.removeFirst()); // B

        Queue<String> linkedQueue = new LinkedQueue<>();

        linkedQueue.addLast("A");
        linkedQueue.addLast("B");
        linkedQueue.addLast("C");

        System.out.println(linkedQueue.removeFirst()); // A
        System.out.println(linkedQueue.getFirst()); // B
        System.out.println(linkedQueue.removeFirst()); // B

    }
}
