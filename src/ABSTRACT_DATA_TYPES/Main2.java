package ABSTRACT_DATA_TYPES;

import java.util.Iterator;

public class Main2 {

    public static void main(String[] args) {

        // List
        // ArrayList, LinkedList
        List<String> tour = new ArrayList<>(10);
        System.out.println(tour.isEmpty());
        tour.addLast("GLA");
        System.out.println(tour.isEmpty());
        tour.addLast("BEL");
        tour.addLast("PAR");
        tour.add(1, "TPE");
        tour.remove(2);
        Iterator<String> it = tour.iterator();
        while (it.hasNext()) {
            System.out.println(it.next());
        }
        tour.clear();
        System.out.println(tour.isEmpty());

        List<String> tour2 = new LinkedList<>();
        tour2.addLast("GLA");
        System.out.println(tour2.isEmpty());
        tour2.addLast("BEL");
        tour2.addLast("PAR");
        tour2.add(1, "TPE");
        tour2.remove(2);
        Iterator<String> it2 = tour2.iterator();
        while (it2.hasNext()) {
            System.out.println(it2.next());
        }
        tour2.clear();
        System.out.println(tour2.isEmpty());

        // Set
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
