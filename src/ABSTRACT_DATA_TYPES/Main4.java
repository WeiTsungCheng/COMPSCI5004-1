package ABSTRACT_DATA_TYPES;

import ABSTRACT_DATA_TYPES.ADTs.set.HashSet;

public class Main4 {

    public static void main(String[] args) {

        // 4. Set
        // HashSet

        HashSet<Integer> numbers = new HashSet<>(3);

        assert numbers.isEmpty();
        assert numbers.size() == 0;

        numbers.add(1);
        numbers.add(4);
        numbers.add(7);

        assert numbers.size() == 3;
        assert numbers.contains(1);
        assert numbers.contains(4);
        assert numbers.contains(7);
        assert !numbers.contains(10);

        // 重複新增
        numbers.add(4);
        assert numbers.size() == 3;

        numbers.remove(4);
        assert !numbers.contains(4);
        assert numbers.contains(7);
        assert numbers.contains(1);
        assert numbers.size() == 2;

        numbers.remove(1);
        assert !numbers.contains(1);
        assert numbers.contains(7);
        assert numbers.size() == 1;

        numbers.remove(7);
        assert numbers.isEmpty();

        numbers.remove(100);
        assert numbers.size() == 0;

        // 負數 hashCode
        numbers.add(-1);
        numbers.add(Integer.MIN_VALUE);
        assert numbers.contains(-1);
        assert numbers.contains(Integer.MIN_VALUE);

        numbers.clear();
        assert numbers.isEmpty();

        // 建立兩個 HashSet
        numbers.add(1);
        numbers.add(4);

        HashSet<Integer> other = new HashSet<>(5);
        other.add(4);
        other.add(8);

        // 聯集：{1, 4} ∪ {4, 8} = {1, 4, 8}
        numbers.addAll(other);
        assert numbers.size() == 3;
        assert numbers.contains(1);
        assert numbers.contains(4);
        assert numbers.contains(8);
        assert numbers.containsAll(other);

        // 來源集合不應改變
        assert other.size() == 2;
        assert !other.contains(1);

        // 交集：{1, 4, 8} ∩ {4, 8} = {4, 8}
        numbers.retainAll(other);
        assert numbers.size() == 2;
        assert numbers.equals(other);
        assert other.equals(numbers);
        assert !numbers.equals(null);

        // 差集：{4, 8} - {4} = {8}
        HashSet<Integer> toRemove = new HashSet<>(3);
        toRemove.add(4);

        numbers.removeAll(toRemove);
        assert numbers.size() == 1;
        assert numbers.contains(8);
        assert !numbers.contains(4);

        // 自己與自己運算
        numbers.addAll(numbers);
        assert numbers.size() == 1;

        numbers.retainAll(numbers);
        assert numbers.size() == 1;

        numbers.removeAll(numbers);
        assert numbers.isEmpty();

        // 清空後重用
        numbers.add(20);
        assert numbers.contains(20);
        assert numbers.size() == 1;

    }
}