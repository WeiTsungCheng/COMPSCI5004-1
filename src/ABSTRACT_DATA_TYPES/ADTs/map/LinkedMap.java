package ABSTRACT_DATA_TYPES.ADTs.map;

import ABSTRACT_DATA_TYPES.interfaces.Map;
import ABSTRACT_DATA_TYPES.interfaces.Set;
import ABSTRACT_DATA_TYPES.ADTs.set.LinkedSet;

import java.util.Iterator;
import java.util.Objects;

public class LinkedMap<K extends Comparable<K>, V>
        implements Map<K, V> {

    private Node<K, V> first;
    private int size;

    private static class Node<K, V> {
        K key;
        V value;
        Node<K, V> next;

        Node(K key, V value, Node<K, V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }

    public LinkedMap() {
        first = null;
        size = 0;
    }

    public boolean isEmpty() {
        return first == null;
    }

    public int size() {
        return size;
    }

    public void clear() {
        first = null;
        size = 0;
    }

    // 搜尋：依 key 由小到大走訪
    public V get(K key) {
        Objects.requireNonNull(key, "Null keys are not supported");

        Node<K, V> current = first;

        while (current != null) {
            int comparison = key.compareTo(current.key);

            if (comparison == 0) {
                return current.value;
            }

            // 已超過目標位置，後面不可能找到
            if (comparison < 0) {
                return null;
            }

            current = current.next;
        }

        return null;
    }

    // 新增或更新配對，回傳舊 value
    public V put(K key, V val) {
        Objects.requireNonNull(key, "Null keys are not supported");

        Node<K, V> current = first;
        Node<K, V> previous = null;

        // 找到第一個 key 大於或等於目標的節點
        while (current != null
                && current.key.compareTo(key) < 0) {
            previous = current;
            current = current.next;
        }

        // key 已存在：更新 value
        if (current != null && current.key.compareTo(key) == 0) {
            V oldValue = current.value;
            current.value = val;
            return oldValue;
        }

        // key 不存在：在 previous 與 current 之間插入
        Node<K, V> newest = new Node<>(key, val, current);

        if (previous == null) {
            first = newest;
        } else {
            previous.next = newest;
        }

        size++;
        return null;
    }

    // 移除指定 key 的配對，回傳原本的 value
    public V remove(K key) {
        Objects.requireNonNull(key, "Null keys are not supported");

        Node<K, V> current = first;
        Node<K, V> previous = null;

        while (current != null) {
            int comparison = key.compareTo(current.key);

            if (comparison == 0) {
                if (previous == null) {
                    first = current.next;
                } else {
                    previous.next = current.next;
                }

                size--;
                return current.value;
            }

            // 已超過目標位置，表示不存在
            if (comparison < 0) {
                return null;
            }

            previous = current;
            current = current.next;
        }

        return null;
    }

    // 回傳獨立的 key 集合
    public Set<K> keySet() {
        Set<K> keys = new LinkedSet<>();
        Node<K, V> current = first;

        while (current != null) {
            keys.add(current.key);
            current = current.next;
        }

        return keys;
    }

    // 按排序後的 key 及對應 value 比較
    // 目前用於兩個 LinkedMap 之間的比較
    public boolean equals(Map<K, V> that) {
        if (that == null || size != that.size()) {
            return false;
        }

        if (this == that) {
            return true;
        }

        Node<K, V> current = first;
        Iterator<K> keys = that.keySet().iterator();

        while (current != null) {
            K otherKey = keys.next();

            if (current.key.compareTo(otherKey) != 0) {
                return false;
            }

            if (!Objects.equals(current.value, that.get(otherKey))) {
                return false;
            }

            current = current.next;
        }

        return true;
    }

    // 覆蓋：逐項新增或更新
    public void putAll(Map<K, V> that) {
        if (this == that) {
            return;
        }

        Iterator<K> keys = that.keySet().iterator();

        while (keys.hasNext()) {
            K key = keys.next();
            put(key, that.get(key));
        }
    }
}
