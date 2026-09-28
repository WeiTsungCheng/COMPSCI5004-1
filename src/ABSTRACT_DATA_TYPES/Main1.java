package ABSTRACT_DATA_TYPES;

import ABSTRACT_DATA_TYPES.interfaces.Stack;
import ABSTRACT_DATA_TYPES.ADTs.stack.ArrayStack;
import ABSTRACT_DATA_TYPES.ADTs.stack.LinkedStack;
import ABSTRACT_DATA_TYPES.interfaces.Queue;

import ABSTRACT_DATA_TYPES.ADTs.queue.ArrayQueue;
import ABSTRACT_DATA_TYPES.ADTs.queue.LinkedQueue;

public class Main1 {
    public static void main(String[] args) {

        // 1. Stack
        // ArrayStack, LinkedStack
        Stack<Integer> arrayStack = new ArrayStack<>(10);

        arrayStack.push(10);
        arrayStack.push(20);

        arrayStack.clear();
        System.out.println(arrayStack.isEmpty());

        arrayStack.push(30);
        System.out.println(arrayStack.pop());  // 30
        System.out.println(arrayStack.isEmpty());

        arrayStack.clear();
        arrayStack.clear();
        System.out.println(arrayStack.isEmpty());

        Stack<Integer> linkedStack = new LinkedStack<>();

        linkedStack.push(10);
        linkedStack.push(20);

        linkedStack.clear();
        System.out.println(linkedStack.isEmpty());

        linkedStack.push(30);
        System.out.println(linkedStack.pop());  // 30
        System.out.println(linkedStack.isEmpty());

        linkedStack.clear();
        linkedStack.clear();
        System.out.println(linkedStack.isEmpty());

        // 2. Queue
        // ArrayQueue, LinkedQueue
        testQueue(new ArrayQueue<>(3));
        testQueue(new LinkedQueue<>());

    }

    private static void testQueue(Queue<String> queue) {
        // 新建時為空
        assert queue.isEmpty();
        assert queue.size() == 0;

        queue.addLast("A");
        queue.addLast("B");
        queue.addLast("C");

        assert queue.size() == 3;

        // 查看第一個元素，不應移除它
        assert queue.getFirst().equals("A");
        assert queue.size() == 3;

        // FIFO：先加入的先取出
        String first = queue.removeFirst();
        String second = queue.removeFirst();
        String third = queue.removeFirst();

        assert first.equals("A");
        assert second.equals("B");
        assert third.equals("C");
        assert queue.isEmpty();
        assert queue.size() == 0;

        // 移除到空之後，能重新加入
        queue.addLast("D");
        assert queue.getFirst().equals("D");
        assert queue.size() == 1;

        // 清空非空 Queue
        queue.clear();
        assert queue.isEmpty();
        assert queue.size() == 0;

        // 空 Queue 可以再次清空
        queue.clear();

        // 清空後能重新使用
        queue.addLast("E");
        String value = queue.removeFirst();
        assert value.equals("E");
        assert queue.isEmpty();
    }
    
}
