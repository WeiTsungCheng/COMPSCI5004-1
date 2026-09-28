package ABSTRACT_DATA_TYPES.ADTs.set;

import ABSTRACT_DATA_TYPES.interfaces.Set;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArraySet<E extends Comparable> implements Set<E> {

    private E[] members;
    private int size;

    public ArraySet(int cap) {
        members = (E[]) new Comparable[cap];
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    };

    public int size() {
        return size;
    };

    public boolean contains(E it) {
        return binarySearch(it) >= 0;
    };

    public boolean equals(Set<E> that) {
        if (that == null || this.size != that.size()) return false;
        // 兩者皆為排序狀態，進行成對比較 (Pairwise comparison) O(n) [3]
        Iterator<E> iter1 = this.iterator();
        Iterator<E> iter2 = that.iterator();
        while (iter1.hasNext()) {
            if (iter1.next().compareTo(iter2.next()) != 0) return false;
        }
        return true;
    };

    public boolean containsAll(Set<E> that) {
        Iterator<E> iter = that.iterator();
        while (iter.hasNext()) {
            if (!this.contains(iter.next())) return false;
        }
        return true;
    };

    public void clear() {
        for (int i = 0; i < size; i++) {
            members[i] = null;
        }
        size = 0;
    };

    public void add(E it) {
        // 二元搜尋 + 插入 O(n)
        int index = binarySearch(it);
        if (index >= 0) return; // 已存在 (無重複元素)，不做事

        int insertPoint = -(index + 1);
        if (size == members.length) throw new IllegalStateException("Set is full");

        // 將插入點之後的元素往後移
        for (int i = size; i > insertPoint; i--) {
            members[i] = members[i - 1];
        }
        members[insertPoint] = it;
        size++;

    };

    public void remove(E it) {
        int index = binarySearch(it);
        if (index < 0) return; // 不存在，不做事

        // 將刪除點之後的元素往前移
        for (int i = index; i < size - 1; i++) {
            members[i] = members[i + 1];
        }
        size--;
        members[size] = null;
    };

    public void addAll(Set<E> that) {
        // 先確認新增的不重複元素是否放得下，避免加到一半才失敗
        int newMembers = 0;
        Iterator<E> check = that.iterator();
        while (check.hasNext()) {
            if (!this.contains(check.next())) newMembers++;
        }
        if (newMembers > members.length - size) {
            throw new IllegalStateException("Set is full");
        }

        // 陣列合併 (Array merge) 概念實作 [3]
        Iterator<E> iter = that.iterator();
        while (iter.hasNext()) {
            this.add(iter.next()); // 若要嚴格達到 O(n+n') 需配置新陣列雙指標合併，此處為簡化版邏輯保證介面完成
        }
    };

    public void removeAll(Set<E> that) {
        // 自己減去自己應為空集合，避免一邊走訪一邊刪除而漏掉元素
        if (this == that) {
            clear();
            return;
        }
        Iterator<E> iter = that.iterator();
        while (iter.hasNext()) {
            this.remove(iter.next());
        }
    };

    public void retainAll(Set<E> that) {
        // 交集：保留同時存在於此集合與 that 集合的元素 [7]
        for (int i = size - 1; i >= 0; i--) {
            if (!that.contains(members[i])) {
                this.remove(members[i]);
            }
        }
    };

    public Iterator<E> iterator() {
        return new ArraySetIterator();
    };

    // 輔助方法：二元搜尋 (Binary Search)，根據講義 [3]
    // 回傳索引值，若找不到則回傳應該插入的位置的負值 (類似 Arrays.binarySearch)
    private int binarySearch(E it) {
        int low = 0, high = size - 1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            int cmp = members[mid].compareTo(it);
            if (cmp < 0) low = mid + 1;
            else if (cmp > 0) high = mid - 1;
            else return mid; // 找到了
        }
        return -(low + 1); // 沒找到，回傳 負的 (插入點 + 1)
    }

    private class ArraySetIterator implements Iterator<E> {
        private int current = 0;
        public boolean hasNext() { return current < size; }
        public E next() {
            if (!hasNext()) throw new NoSuchElementException();
            return members[current++];
        }
    }



}
