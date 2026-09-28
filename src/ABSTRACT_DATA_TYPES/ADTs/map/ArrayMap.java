package ABSTRACT_DATA_TYPES.ADTs.map;

import ABSTRACT_DATA_TYPES.interfaces.Map;
import ABSTRACT_DATA_TYPES.interfaces.Set;
import ABSTRACT_DATA_TYPES.ADTs.set.ArraySet;

import java.util.Iterator;
import java.util.Objects;

public class ArrayMap<K extends Comparable<K>, V>
        implements Map<K, V> {

    private Entry<K, V>[] entries;
    private int size;

    private static class Entry<K, V> {
        K key;
        V value;

        Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    @SuppressWarnings("unchecked")
    public ArrayMap(int capacity) {
        if (capacity < 0) {
            throw new IllegalArgumentException(
                    "Capacity must not be negative"
            );
        }

        entries = (Entry<K, V>[]) new Entry<?, ?>[capacity];
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    // 根據 key 查詢 value
    public V get(K key) {
        int index = binarySearch(key);

        if (index < 0) {
            return null;
        }

        return entries[index].value;
    }

    // 新增或更新配對，回傳舊 value
    public V put(K key, V val) {
        int index = binarySearch(key);

        // key 已存在：只更新 value
        if (index >= 0) {
            V oldValue = entries[index].value;
            entries[index].value = val;
            return oldValue;
        }

        // key 不存在：新增前先檢查容量
        if (size == entries.length) {
            throw new IllegalStateException("Map is full");
        }

        int insertPoint = -(index + 1);

        // 將插入位置之後的配對往右移
        for (int i = size; i > insertPoint; i--) {
            entries[i] = entries[i - 1];
        }

        entries[insertPoint] = new Entry<>(key, val);
        size++;

        return null;
    }

    // 移除指定 key 的配對，回傳原本的 value
    public V remove(K key) {
        int index = binarySearch(key);

        if (index < 0) {
            return null;
        }

        V oldValue = entries[index].value;

        // 將後面的配對往左移
        for (int i = index; i < size - 1; i++) {
            entries[i] = entries[i + 1];
        }

        size--;
        entries[size] = null;

        return oldValue;
    }

    public void clear() {
        for (int i = 0; i < size; i++) {
            entries[i] = null;
        }

        size = 0;
    }

    // 回傳獨立的 key 集合
    public Set<K> keySet() {
        Set<K> keys = new ArraySet<>(size);

        for (int i = 0; i < size; i++) {
            keys.add(entries[i].key);
        }

        return keys;
    }

    // 比較 key 與對應的 value
    // 依目前 ArrayMap 的 keySet() 排序順序逐項比較
    public boolean equals(Map<K, V> that) {
        if (that == null || size != that.size()) {
            return false;
        }

        if (this == that) {
            return true;
        }

        Iterator<K> keys = that.keySet().iterator();

        for (int i = 0; i < size; i++) {
            K otherKey = keys.next();

            if (entries[i].key.compareTo(otherKey) != 0) {
                return false;
            }

            if (!Objects.equals(
                    entries[i].value,
                    that.get(otherKey))) {
                return false;
            }
        }

        return true;
    }

    // 覆蓋：逐一加入另一個 Map 的配對
    public void putAll(Map<K, V> that) {
        if (this == that) {
            return;
        }

        Set<K> keys = that.keySet();

        // 先確認新增的 key 是否放得下
        int newKeys = 0;
        Iterator<K> check = keys.iterator();

        while (check.hasNext()) {
            if (binarySearch(check.next()) < 0) {
                newKeys++;
            }
        }

        if (newKeys > entries.length - size) {
            throw new IllegalStateException("Map is full");
        }

        // put() 會處理新增與更新
        Iterator<K> iter = keys.iterator();

        while (iter.hasNext()) {
            K key = iter.next();
            put(key, that.get(key));
        }
    }

    // 找到時回傳索引
    // 找不到時回傳 -(應插入的位置 + 1)
    private int binarySearch(K key) {
        Objects.requireNonNull(key, "Null keys are not supported");

        int low = 0;
        int high = size - 1;

        while (low <= high) {
            int middle = low + (high - low) / 2;
            int comparison = entries[middle].key.compareTo(key);

            if (comparison < 0) {
                low = middle + 1;
            } else if (comparison > 0) {
                high = middle - 1;
            } else {
                return middle;
            }
        }

        return -(low + 1);
    }
}