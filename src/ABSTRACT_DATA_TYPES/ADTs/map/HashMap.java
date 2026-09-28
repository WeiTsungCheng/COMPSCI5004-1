package ABSTRACT_DATA_TYPES.ADTs.map;

import ABSTRACT_DATA_TYPES.interfaces.Map;
import ABSTRACT_DATA_TYPES.interfaces.Set;
import ABSTRACT_DATA_TYPES.ADTs.set.HashSet;

import java.util.Iterator;
import java.util.Objects;

public class HashMap<K, V> implements Map<K, V> {

    private Node<K, V>[] buckets;
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

    public HashMap(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException(
                    "Bucket count must be greater than zero"
            );
        }

        buckets = (Node<K, V>[]) new Node<?, ?>[capacity];
        size = 0;
    }

    // 使用 key 計算桶子的索引
    private int bucketIndex(K key) {
        Objects.requireNonNull(key, "Null keys are not supported");
        return Math.floorMod(key.hashCode(), buckets.length);
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

    // 找到指定 key 的節點，找不到回傳 null
    private Node<K, V> findNode(K key) {
        int index = bucketIndex(key);
        Node<K, V> current = buckets[index];

        while (current != null) {
            if (current.key.equals(key)) {
                return current;
            }

            current = current.next;
        }

        return null;
    }

    // 根據 key 查詢 value
    public V get(K key) {
        Node<K, V> node = findNode(key);

        if (node == null) {
            return null;
        }

        return node.value;
    }

    // 新增或更新配對，回傳舊 value
    public V put(K key, V val) {
        int index = bucketIndex(key);
        Node<K, V> current = buckets[index];

        while (current != null) {
            if (current.key.equals(key)) {
                V oldValue = current.value;
                current.value = val;
                return oldValue;
            }

            current = current.next;
        }

        // key 不存在：插入桶子的最前面
        buckets[index] = new Node<>(key, val, buckets[index]);
        size++;

        return null;
    }

    // 移除指定 key 的配對，回傳舊 value
    public V remove(K key) {
        int index = bucketIndex(key);
        Node<K, V> current = buckets[index];
        Node<K, V> previous = null;

        while (current != null) {
            if (current.key.equals(key)) {
                if (previous == null) {
                    buckets[index] = current.next;
                } else {
                    previous.next = current.next;
                }

                size--;
                return current.value;
            }

            previous = current;
            current = current.next;
        }

        return null;
    }

    // 回傳獨立的 key 集合，不保證排序
    public Set<K> keySet() {
        Set<K> keys = new HashSet<>(buckets.length);

        for (int i = 0; i < buckets.length; i++) {
            Node<K, V> current = buckets[i];

            while (current != null) {
                keys.add(current.key);
                current = current.next;
            }
        }

        return keys;
    }

    // 目前用於兩個 HashMap 之間的比較
    // 不依遍歷順序，而是檢查相同 key 的 value
    public boolean equals(Map<K, V> that) {
        if (that == null || size != that.size()) {
            return false;
        }

        if (this == that) {
            return true;
        }

        Iterator<K> keys = that.keySet().iterator();

        while (keys.hasNext()) {
            K key = keys.next();
            Node<K, V> node = findNode(key);

            // 必須先確認 key 存在，因為 value 可能是 null
            if (node == null) {
                return false;
            }

            if (!Objects.equals(node.value, that.get(key))) {
                return false;
            }
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