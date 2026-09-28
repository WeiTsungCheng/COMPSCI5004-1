package ABSTRACT_DATA_TYPES.ADTs.set;

import ABSTRACT_DATA_TYPES.interfaces.Set;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

public class HashSet<E> implements Set<E> {

    private Node<E>[] buckets;
    private int size;

    private static class Node<E> {
        E element;
        Node<E> next;

        Node(E element, Node<E> next) {
            this.element = element;
            this.next = next;
        }
    }

    @SuppressWarnings("unchecked")
    public HashSet(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException(
                    "Bucket count must be greater than zero"
            );
        }

        buckets = (Node<E>[]) new Node<?>[capacity];
        size = 0;
    }

    // 計算桶子的索引，也能處理負數 hashCode
    private int bucketIndex(E it) {
        Objects.requireNonNull(it, "Null elements are not supported");
        return Math.floorMod(it.hashCode(), buckets.length);
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void clear() {
        for (int i = 0; i < buckets.length; i++) {
            buckets[i] = null;
        }

        size = 0;
    }

    // 搜尋：找到桶子，再走訪鏈結串列
    public boolean contains(E it) {
        int index = bucketIndex(it);
        Node<E> current = buckets[index];

        while (current != null) {
            if (current.element.equals(it)) {
                return true;
            }

            current = current.next;
        }

        return false;
    }

    // 新增：不接受重複元素，插入桶子的最前面
    public void add(E it) {
        int index = bucketIndex(it);
        Node<E> current = buckets[index];

        while (current != null) {
            if (current.element.equals(it)) {
                return;
            }

            current = current.next;
        }

        buckets[index] = new Node<>(it, buckets[index]);
        size++;
    }

    // 刪除：找到後，讓前一個節點跳過目前節點
    public void remove(E it) {
        int index = bucketIndex(it);
        Node<E> current = buckets[index];
        Node<E> previous = null;

        while (current != null) {
            if (current.element.equals(it)) {
                if (previous == null) {
                    // 刪除第一個節點
                    buckets[index] = current.next;
                } else {
                    // 刪除中間或最後一個節點
                    previous.next = current.next;
                }

                size--;
                return;
            }

            previous = current;
            current = current.next;
        }
    }

    // HashSet 不保證遍歷順序，因此比較成員，不逐項配對
    public boolean equals(Set<E> that) {
        if (that == null || size != that.size()) {
            return false;
        }

        return containsAll(that);
    }

    // 是否包含另一個集合的所有成員
    public boolean containsAll(Set<E> that) {
        Iterator<E> iter = that.iterator();

        while (iter.hasNext()) {
            if (!contains(iter.next())) {
                return false;
            }
        }

        return true;
    }

    // 聯集：逐項新增
    public void addAll(Set<E> that) {
        Iterator<E> iter = that.iterator();

        while (iter.hasNext()) {
            add(iter.next());
        }
    }

    // 差集：逐項移除
    public void removeAll(Set<E> that) {
        if (this == that) {
            clear();
            return;
        }

        Iterator<E> iter = that.iterator();

        while (iter.hasNext()) {
            remove(iter.next());
        }
    }

    // 交集：保留對方也有的成員
    public void retainAll(Set<E> that) {
        Iterator<E> iter = iterator();

        while (iter.hasNext()) {
            E element = iter.next();

            if (!that.contains(element)) {
                remove(element);
            }
        }
    }

    // 建立快照，順序依桶子及鏈結串列而定
    public Iterator<E> iterator() {
        ArrayList<E> elements = new ArrayList<>();

        for (int i = 0; i < buckets.length; i++) {
            Node<E> current = buckets[i];

            while (current != null) {
                elements.add(current.element);
                current = current.next;
            }
        }

        return elements.iterator();
    }
}