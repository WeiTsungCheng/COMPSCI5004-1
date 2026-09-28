
package ABSTRACT_DATA_TYPES;

import ABSTRACT_DATA_TYPES.ADTs.map.HashMap;
import ABSTRACT_DATA_TYPES.interfaces.Map;
import ABSTRACT_DATA_TYPES.interfaces.Set;

public class Main8 {
    
    public static void main(String[] args) {
        Map<Integer, String> names = new HashMap<>(3);

        assert names.isEmpty();
        assert names.size() == 0;

        // 碰撞：1、4、7 都放進索引 1 的桶子
        // 插入後：7 → 4 → 1
        String oldValue = names.put(1, "One");
        assert oldValue == null;

        names.put(4, "Four");
        names.put(7, "Seven");

        assert names.size() == 3;
        assert names.get(1).equals("One");
        assert names.get(4).equals("Four");
        assert names.get(7).equals("Seven");
        assert names.get(10) == null;

        // 更新既有 key，大小不變
        oldValue = names.put(4, "FOUR");
        assert oldValue.equals("Four");
        assert names.get(4).equals("FOUR");
        assert names.size() == 3;

        // 刪除中間節點
        String removed = names.remove(4);
        assert removed.equals("FOUR");
        assert names.get(4) == null;
        assert names.get(7).equals("Seven");
        assert names.get(1).equals("One");

        // 刪除第一個節點，確認後面仍保留
        removed = names.remove(7);
        assert removed.equals("Seven");
        assert names.get(1).equals("One");
        assert names.size() == 1;

        // 刪除最後剩下的節點
        removed = names.remove(1);
        assert removed.equals("One");
        assert names.isEmpty();

        // 刪除不存在的 key
        removed = names.remove(100);
        assert removed == null;

        // 負數 hashCode
        names.put(-1, "Negative");
        names.put(Integer.MIN_VALUE, "Minimum");

        assert names.get(-1).equals("Negative");
        assert names.get(Integer.MIN_VALUE).equals("Minimum");

        names.clear();
        assert names.isEmpty();

        names.put(1, "One");
        names.put(4, "Four");

        Map<Integer, String> other = new HashMap<>(5);
        other.put(4, "Updated");
        other.put(8, "Eight");

        // 覆蓋：更新 4，新增 8
        names.putAll(other);

        assert names.size() == 3;
        assert names.get(1).equals("One");
        assert names.get(4).equals("Updated");
        assert names.get(8).equals("Eight");

        // 來源 Map 不变
        assert other.size() == 2;
        assert other.get(1) == null;

        // 桶子數量和插入順序不同，內容相同仍相等
        Map<Integer, String> expected = new HashMap<>(7);
        expected.put(8, "Eight");
        expected.put(1, "One");
        expected.put(4, "Updated");

        assert names.equals(expected);
        assert expected.equals(names);
        assert !names.equals(null);

        // 相同 key、不同 value，應不相等
        expected.put(4, "Different");
        assert !names.equals(expected);

        // keySet 是獨立集合
        Set<Integer> copiedKeys = names.keySet();

        assert copiedKeys.size() == 3;
        assert copiedKeys.contains(1);
        assert copiedKeys.contains(4);
        assert copiedKeys.contains(8);

        copiedKeys.clear();
        assert names.size() == 3;

        // 自己覆蓋自己
        names.putAll(names);
        assert names.size() == 3;

        // null value 不等於 key 不存在
        Map<Integer, String> left = new HashMap<>(3);
        Map<Integer, String> right = new HashMap<>(3);

        left.put(1, null);
        right.put(2, null);

        assert !left.equals(right);

        right.clear();
        right.put(1, null);

        assert left.equals(right);

        removed = left.remove(1);
        assert removed == null;
        assert left.isEmpty();

        // 清空後重用
        names.clear();
        assert names.isEmpty();

        names.put(20, "Twenty");
        assert names.get(20).equals("Twenty");
    }
}
