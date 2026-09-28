package ABSTRACT_DATA_TYPES;

import ABSTRACT_DATA_TYPES.ADTs.set.BSTSet;
import ABSTRACT_DATA_TYPES.interfaces.Set;
import java.util.Iterator;

public class Main3 {

    public static void main(String[] args) {

        // 4. Set
        // BSTSet

        Set<Integer> numbers = new BSTSet<>();

        // 建立樹
        for (int value : new int[]{12, 7, 15, 9, 2, 6}) {
            numbers.add(value);
        }

        printSet(numbers); // 2 6 7 9 12 15
        assert numbers.size() == 6;
        assert numbers.contains(9);
        assert !numbers.contains(8);

        // 重複元素不新增
        numbers.add(7);
        assert numbers.size() == 6;

        // 刪除葉節點
        numbers.remove(9);
        assert !numbers.contains(9);
        assert numbers.size() == 5;

        // 刪除只有一個子節點的節點
        numbers.remove(2);
        assert !numbers.contains(2);
        assert numbers.contains(6);
        assert numbers.size() == 4;

        // 刪除有兩個子節點的根節點
        numbers.remove(12);
        assert !numbers.contains(12);
        assert numbers.size() == 3;
        printSet(numbers); // 6 7 15

        // 刪除不存在的元素
        numbers.remove(100);
        assert numbers.size() == 3;

        Set<Integer> other = new BSTSet<>();
        other.add(7);
        other.add(20);

        // 聯集
        numbers.addAll(other);
        printSet(numbers); // 6 7 15 20
        assert numbers.size() == 4;
        assert numbers.containsAll(other);

        // 交集
        numbers.retainAll(other);
        printSet(numbers); // 7 20
        assert numbers.equals(other);

        // 差集
        Set<Integer> toRemove = new BSTSet<>();
        toRemove.add(7);

        numbers.removeAll(toRemove);
        assert numbers.size() == 1;
        assert numbers.contains(20);

        // 自己減去自己
        numbers.removeAll(numbers);
        assert numbers.isEmpty();
        assert numbers.size() == 0;

        // 清空後重用
        numbers.add(30);
        assert numbers.contains(30);

        numbers.clear();
        assert numbers.isEmpty();
    }

    private static void printSet(Set<Integer> set) {
        Iterator<Integer> iter = set.iterator();

        while (iter.hasNext()) {
            System.out.print(iter.next() + " ");
        }
        System.out.println();
    }
}