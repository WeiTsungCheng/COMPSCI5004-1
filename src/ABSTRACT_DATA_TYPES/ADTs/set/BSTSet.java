package ABSTRACT_DATA_TYPES.ADTs.set;

import ABSTRACT_DATA_TYPES.interfaces.Set;
import java.util.ArrayList;
import java.util.Iterator;

public class BSTSet<E extends Comparable<E>> implements Set<E> {

    private Node<E> root;
    private int size;

    private static class Node<E> {
        E element;
        Node<E> left;
        Node<E> right;

        Node(E element) {
            this.element = element;
            left = null;
            right = null;
        }
    }

    public BSTSet() {
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

    // 搜尋：小的往左，大的往右
    public boolean contains(E it) {
        Node<E> current = root;

        while (current != null) {
            int comparison = it.compareTo(current.element);

            if (comparison == 0) {
                return true;
            }

            if (comparison < 0) {
                current = current.left;
            } else {
                current = current.right;
            }
        }

        return false;
    }

    // 新增：找到空位置後建立節點，重複元素不新增
    public void add(E it) {
        if (root == null) {
            root = new Node<>(it);
            size++;
            return;
        }

        Node<E> current = root;

        while (true) {
            int comparison = it.compareTo(current.element);

            if (comparison == 0) {
                return;
            }

            if (comparison < 0) {
                if (current.left == null) {
                    current.left = new Node<>(it);
                    size++;
                    return;
                }

                current = current.left;
            } else {
                if (current.right == null) {
                    current.right = new Node<>(it);
                    size++;
                    return;
                }

                current = current.right;
            }
        }
    }

    // 刪除不存在的元素時，不做任何事
    public void remove(E it) {
        if (!contains(it)) {
            return;
        }

        root = remove(root, it);
        size--;
    }

    // 回傳刪除後的子樹根節點
    private Node<E> remove(Node<E> node, E it) {
        if (node == null) {
            return null;
        }

        int comparison = it.compareTo(node.element);

        if (comparison < 0) {
            node.left = remove(node.left, it);
        } else if (comparison > 0) {
            node.right = remove(node.right, it);
        } else {
            // 情況 1、2：沒有左子樹
            // 若也沒有右子樹，回傳 null；否則讓右子樹接替
            if (node.left == null) {
                return node.right;
            }

            // 情況 2：只有左子樹，讓左子樹接替
            if (node.right == null) {
                return node.left;
            }

            // 情況 3：有兩個子樹
            // 找右子樹中最小的元素，作為接替者
            Node<E> successor = node.right;

            while (successor.left != null) {
                successor = successor.left;
            }

            // 將接替者的值放到目前節點
            node.element = successor.element;

            // 刪除右子樹中原本的接替者節點
            node.right = remove(node.right, successor.element);
        }

        return node;
    }

    // 比較集合內容：兩邊都依由小到大的順序走訪
    public boolean equals(Set<E> that) {
        if (that == null || size != that.size()) {
            return false;
        }

        Iterator<E> left = iterator();
        Iterator<E> right = that.iterator();

        while (left.hasNext()) {
            if (left.next().compareTo(right.next()) != 0) {
                return false;
            }
        }

        return true;
    }

    // 是否包含另一個集合的所有元素
    public boolean containsAll(Set<E> that) {
        Iterator<E> iter = that.iterator();

        while (iter.hasNext()) {
            if (!contains(iter.next())) {
                return false;
            }
        }

        return true;
    }

    // 聯集：逐一新增，add() 會忽略重複元素
    public void addAll(Set<E> that) {
        Iterator<E> iter = that.iterator();

        while (iter.hasNext()) {
            add(iter.next());
        }
    }

    // 差集：移除同時存在於另一個集合的元素
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

    // 交集：只保留另一個集合也有的元素
    public void retainAll(Set<E> that) {
        Iterator<E> iter = iterator();

        while (iter.hasNext()) {
            E element = iter.next();

            if (!that.contains(element)) {
                remove(element);
            }
        }
    }

    // 先建立排序清單，再回傳清單的 iterator
    public Iterator<E> iterator() {
        ArrayList<E> elements = new ArrayList<>();
        collectInOrder(root, elements);
        return elements.iterator();
    }

    // 中序遍歷：左子樹 → 自己 → 右子樹
    private void collectInOrder(Node<E> node, ArrayList<E> elements) {
        if (node == null) {
            return;
        }

        collectInOrder(node.left, elements);
        elements.add(node.element);
        collectInOrder(node.right, elements);
    }
}