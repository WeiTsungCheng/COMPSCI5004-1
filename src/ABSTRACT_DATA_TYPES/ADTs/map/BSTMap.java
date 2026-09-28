package ABSTRACT_DATA_TYPES.ADTs.map;

import ABSTRACT_DATA_TYPES.interfaces.Map;
import ABSTRACT_DATA_TYPES.interfaces.Set;
import ABSTRACT_DATA_TYPES.ADTs.set.BSTSet;

import java.util.Iterator;
import java.util.Objects;

public class BSTMap<K extends Comparable<K>, V>
        implements Map<K, V> {

    private Node<K, V> root;
    private int size;

    private static class Node<K, V> {
        K key;
        V value;
        Node<K, V> left;
        Node<K, V> right;

        Node(K key, V value) {
            this.key = key;
            this.value = value;
            left = null;
            right = null;
        }
    }

    public BSTMap() {
        root = null;
        size = 0;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public int size() {
        return size;
    }

    public void clear() {
        root = null;
        size = 0;
    }

    // 找到指定 key 的節點，找不到回傳 null
    private Node<K, V> findNode(K key) {
        Objects.requireNonNull(key, "Null keys are not supported");

        Node<K, V> current = root;

        while (current != null) {
            int comparison = key.compareTo(current.key);

            if (comparison == 0) {
                return current;
            }

            if (comparison < 0) {
                current = current.left;
            } else {
                current = current.right;
            }
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

    // 新增或更新，回傳舊 value
    public V put(K key, V val) {
        Objects.requireNonNull(key, "Null keys are not supported");

        if (root == null) {
            root = new Node<>(key, val);
            size++;
            return null;
        }

        Node<K, V> current = root;

        while (true) {
            int comparison = key.compareTo(current.key);

            // key 已存在：只更新 value
            if (comparison == 0) {
                V oldValue = current.value;
                current.value = val;
                return oldValue;
            }

            if (comparison < 0) {
                if (current.left == null) {
                    current.left = new Node<>(key, val);
                    size++;
                    return null;
                }

                current = current.left;
            } else {
                if (current.right == null) {
                    current.right = new Node<>(key, val);
                    size++;
                    return null;
                }

                current = current.right;
            }
        }
    }

    // 移除指定 key 的配對，回傳舊 value
    public V remove(K key) {
        Node<K, V> target = findNode(key);

        if (target == null) {
            return null;
        }

        // 先保存舊值，避免刪除過程中被接替者覆蓋
        V oldValue = target.value;

        root = remove(root, key);
        size--;

        return oldValue;
    }

    // 回傳刪除後的子樹根節點
    private Node<K, V> remove(Node<K, V> node, K key) {
        if (node == null) {
            return null;
        }

        int comparison = key.compareTo(node.key);

        if (comparison < 0) {
            node.left = remove(node.left, key);
        } else if (comparison > 0) {
            node.right = remove(node.right, key);
        } else {
            // 沒有左子樹：由右子樹接替
            // 若右子樹也不存在，就回傳 null
            if (node.left == null) {
                return node.right;
            }

            // 只有左子樹
            if (node.right == null) {
                return node.left;
            }

            // 有兩個子樹：找右子樹中最小的 key
            Node<K, V> successor = node.right;

            while (successor.left != null) {
                successor = successor.left;
            }

            // key 和 value 必須一起複製
            node.key = successor.key;
            node.value = successor.value;

            // 刪除原本的接替者節點
            node.right = remove(node.right, successor.key);
        }

        return node;
    }

    // 建立獨立的 key 集合
    public Set<K> keySet() {
        Set<K> keys = new BSTSet<>();
        collectKeys(root, keys);
        return keys;
    }

    // 先加入目前節點，再處理左右子樹
    private void collectKeys(Node<K, V> node, Set<K> keys) {
        if (node == null) {
            return;
        }

        keys.add(node.key);
        collectKeys(node.left, keys);
        collectKeys(node.right, keys);
    }

    // 目前用於兩個 BSTMap 之間的比較
    // BSTSet 的 iterator 會依 key 由小到大走訪
    public boolean equals(Map<K, V> that) {
        if (that == null || size != that.size()) {
            return false;
        }

        if (this == that) {
            return true;
        }

        Iterator<K> left = keySet().iterator();
        Iterator<K> right = that.keySet().iterator();

        while (left.hasNext()) {
            K leftKey = left.next();
            K rightKey = right.next();

            if (leftKey.compareTo(rightKey) != 0) {
                return false;
            }

            if (!Objects.equals(get(leftKey), that.get(rightKey))) {
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