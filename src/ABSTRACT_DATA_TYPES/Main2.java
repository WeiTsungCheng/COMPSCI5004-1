package ABSTRACT_DATA_TYPES;

import ABSTRACT_DATA_TYPES.interfaces.List;
import ABSTRACT_DATA_TYPES.ADTs.list.ArrayList;
import ABSTRACT_DATA_TYPES.ADTs.list.LinkedList;
import ABSTRACT_DATA_TYPES.interfaces.Set;
import ABSTRACT_DATA_TYPES.ADTs.set.ArraySet;
import ABSTRACT_DATA_TYPES.ADTs.set.LinkedSet;

public class Main2 {

    public static void main(String[] args) {

        // 3. List
        // ArrayList, LinkedList

        List<String> first = new ArrayList<>(10);
        List<String> second = new ArrayList<>(10);

        first.addLast("A");
        first.addLast("B");
        second.addLast("A");
        second.addLast("B");

        // 內容及順序相同
        assert first.equals(second);

        // 改成不同順序
        second.set(0, "B");
        second.set(1, "A");
        assert !first.equals(second);

        // [A, B] + [B, A] → [A, B, B, A]
        first.addAll(second);
        assert first.size() == 4;
        assert first.get(0).equals("A");
        assert first.get(1).equals("B");
        assert first.get(2).equals("B");
        assert first.get(3).equals("A");

        // 來源 List 保持原樣
        assert second.size() == 2;
        assert second.get(0).equals("B");
        assert second.get(1).equals("A");

        // 自己串接自己：[B, A] → [B, A, B, A]
        second.addAll(second);
        assert second.size() == 4;
        assert second.get(0).equals("B");
        assert second.get(1).equals("A");
        assert second.get(2).equals("B");
        assert second.get(3).equals("A");

        List<String> first2 = new LinkedList<>();
        List<String> second2 = new LinkedList<>();

        first2.addLast("A");
        first2.addLast("B");
        second2.addLast("A");
        second2.addLast("B");

        // 內容及順序相同
        assert first2.equals(second2);

        // 改成不同順序
        second2.set(0, "B");
        second2.set(1, "A");
        assert !first2.equals(second2);

        // [A, B] + [B, A] → [A, B, B, A]
        first2.addAll(second2);
        assert first2.size() == 4;
        assert first2.get(0).equals("A");
        assert first2.get(1).equals("B");
        assert first2.get(2).equals("B");
        assert first2.get(3).equals("A");

        // 修改來源 List，確認串接後沒有共用節點
        second2.set(0, "X");
        assert first2.get(2).equals("B");
        assert second2.size() == 2;

        // 自己串接自己：[X, A] → [X, A, X, A]
        second2.addAll(second2);
        assert second2.size() == 4;
        assert second2.get(0).equals("X");
        assert second2.get(1).equals("A");
        assert second2.get(2).equals("X");
        assert second2.get(3).equals("A");

        // 4. Set
        // ArraySet, LinkedSet

        // 初始化 ArraySet，必須給定容量上限 (Bounded)
        // 這裡設定容量為 5
        Set<String> nafta = new ArraySet<>(5);

        // 新增元素 (使用二元搜尋找到位置後插入，耗時 O(n))
        nafta.add("US");
        nafta.add("CA");
        nafta.add("MX");

        // 嘗試加入重複的元素，根據 Set 的定義，這將被忽略
        nafta.add("US");

        System.out.println("--- ArraySet (NAFTA) 測試 ---");
        System.out.println("集合大小: " + nafta.size()); // 輸出: 3

        // 測試成員是否存在 (使用二元搜尋法，極快，耗時 O(log n))
        System.out.println("包含 'CA' (加拿大) 嗎？ " + nafta.contains("CA")); // 輸出: true
        System.out.println("包含 'UK' (英國) 嗎？ " + nafta.contains("UK"));   // 輸出: false

        // 刪除元素 (尋找後將後續元素往前移，耗時 O(n))
        nafta.remove("MX");
        System.out.println("移除 'MX' 後的集合大小: " + nafta.size()); // 輸出: 2


        // 1. 初始化 LinkedSet，不需要給定容量 (Unbounded)
        Set<Integer> primes = new LinkedSet<>();

        // 2. 新增元素 (使用線性搜尋找到適當位置後插入節點，耗時 O(n))
        primes.add(2);
        primes.add(3);
        primes.add(5);
        primes.add(7);
        primes.add(11);

        // 嘗試加入重複的元素，同樣會被忽略
        primes.add(5);
        System.out.println("集合大小: " + primes.size()); // 輸出: 5

        // 測試成員是否存在 (必須從頭節點開始線性搜尋，耗時 O(n))
        System.out.println("包含 7 嗎？ " + primes.contains(7));  // 輸出: true
        System.out.println("包含 10 嗎？ " + primes.contains(10)); // 輸出: false

        // 測試包含子集 (containsAll)
        Set<Integer> smallPrimes = new LinkedSet<>();
        smallPrimes.add(2);
        smallPrimes.add(3);
        System.out.println("包含子集 {2, 3} 嗎？ " + primes.containsAll(smallPrimes)); // 輸出: true
    }
}
