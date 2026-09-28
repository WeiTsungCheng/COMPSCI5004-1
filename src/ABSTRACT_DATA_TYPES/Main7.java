package ABSTRACT_DATA_TYPES;

import ABSTRACT_DATA_TYPES.ADTs.map.BSTMap;
import ABSTRACT_DATA_TYPES.interfaces.Map;
import ABSTRACT_DATA_TYPES.interfaces.Set;

import java.util.Iterator;

public class Main7 {

    public static void main(String[] args) {
        Map<Integer, String> names = new BSTMap<>();

        assert names.isEmpty();
        assert names.size() == 0;

        // 建立樹
        String oldValue = names.put(12, "Twelve");
        assert oldValue == null;

        names.put(7, "Seven");
        names.put(15, "Fifteen");
        names.put(9, "Nine");
        names.put(2, "Two");
        names.put(6, "Six");

        assert names.size() == 6;
        assert names.get(9).equals("Nine");
        assert names.get(100) == null;

        // keySet 的 iterator 應依 key 排序
        Iterator<Integer> keys = names.keySet().iterator();

        for (int expected : new int[]{2, 6, 7, 9, 12, 15}) {
            assert keys.hasNext();
            assert keys.next().equals(expected);
        }

        assert !keys.hasNext();

        // 更新既有 key
        oldValue = names.put(7, "SEVEN");
        assert oldValue.equals("Seven");
        assert names.get(7).equals("SEVEN");
        assert names.size() == 6;

        // 刪除葉節點
        String removed = names.remove(9);
        assert removed.equals("Nine");
        assert names.get(9) == null;
        assert names.size() == 5;

        // 刪除只有一個子節點的節點
        removed = names.remove(2);
        assert removed.equals("Two");
        assert names.get(2) == null;
        assert names.get(6).equals("Six");
        assert names.size() == 4;

        // 刪除有兩個子節點的根
        removed = names.remove(12);
        assert removed.equals("Twelve");
        assert names.get(12) == null;
        assert names.get(15).equals("Fifteen");
        assert names.size() == 3;

        // 刪除不存在的 key
        removed = names.remove(100);
        assert removed == null;
        assert names.size() == 3;

        // 覆蓋另一個 BSTMap
        Map<Integer, String> other = new BSTMap<>();
        other.put(7, "Updated");
        other.put(20, "Twenty");

        names.putAll(other);

        assert names.size() == 4;
        assert names.get(7).equals("Updated");
        assert names.get(20).equals("Twenty");

        // 來源 Map 不變
        assert other.size() == 2;
        assert other.get(6) == null;

        // 比較相同內容、不同插入順序的 BSTMap
        Map<Integer, String> expected = new BSTMap<>();
        expected.put(20, "Twenty");
        expected.put(15, "Fifteen");
        expected.put(7, "Updated");
        expected.put(6, "Six");

        assert names.equals(expected);
        assert expected.equals(names);
        assert !names.equals(null);

        // 同 key、不同 value，應不相等
        expected.put(7, "Different");
        assert !names.equals(expected);

        // keySet 是獨立集合
        Set<Integer> copiedKeys = names.keySet();
        copiedKeys.clear();
        assert names.size() == 4;

        // 自己覆蓋自己
        names.putAll(names);
        assert names.size() == 4;

        // null value 的配對也能刪除
        names.put(30, null);
        assert names.size() == 5;
        assert names.keySet().contains(30);

        removed = names.remove(30);
        assert removed == null;
        assert names.size() == 4;
        assert !names.keySet().contains(30);

        // 清空後重用
        names.clear();
        assert names.isEmpty();

        names.put(1, "One");
        assert names.get(1).equals("One");

        removed = names.remove(1);
        assert removed.equals("One");
        assert names.isEmpty();

    }
}