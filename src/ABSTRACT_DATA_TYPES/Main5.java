package ABSTRACT_DATA_TYPES;

import ABSTRACT_DATA_TYPES.ADTs.map.ArrayMap;
import ABSTRACT_DATA_TYPES.interfaces.Map;
import ABSTRACT_DATA_TYPES.interfaces.Set;

import java.util.Iterator;

public class Main5 {

    public static void main(String[] args) {

        // 5. Map
        // ArrayMap, LinkedMap

        Map<String, Integer> scores = new ArrayMap<>(5);

        assert scores.isEmpty();
        assert scores.size() == 0;

        // 不依排序順序新增，內部仍應按 key 排序
        Integer oldValue = scores.put("S003", 70);
        assert oldValue == null;

        scores.put("S001", 80);
        scores.put("S002", 90);

        assert scores.size() == 3;
        assert scores.get("S001").equals(80);
        assert scores.get("S999") == null;

        // 更新既有 key：回傳舊值，大小不變
        oldValue = scores.put("S001", 95);

        assert oldValue.equals(80);
        assert scores.get("S001").equals(95);
        assert scores.size() == 3;

        // keySet 依 key 排序
        Iterator<String> keys = scores.keySet().iterator();

        assert keys.next().equals("S001");
        assert keys.next().equals("S002");
        assert keys.next().equals("S003");
        assert !keys.hasNext();

        // 回傳的 keySet 是獨立集合
        Set<String> copiedKeys = scores.keySet();
        copiedKeys.remove("S001");
        assert scores.get("S001").equals(95);

        // 移除既有 key
        Integer removed = scores.remove("S002");

        assert removed.equals(90);
        assert scores.get("S002") == null;
        assert scores.size() == 2;

        // 移除不存在的 key
        removed = scores.remove("S999");
        assert removed == null;
        assert scores.size() == 2;

        Map<String, Integer> other = new ArrayMap<>(5);
        other.put("S001", 100);
        other.put("S004", 60);

        // 覆蓋：更新 S001，新增 S004
        scores.putAll(other);

        assert scores.size() == 3;
        assert scores.get("S001").equals(100);
        assert scores.get("S003").equals(70);
        assert scores.get("S004").equals(60);

        // 來源 Map 不變
        assert other.size() == 2;

        // 相同內容，即使插入順序不同，也相等
        Map<String, Integer> expected = new ArrayMap<>(5);
        expected.put("S004", 60);
        expected.put("S003", 70);
        expected.put("S001", 100);

        assert scores.equals(expected);
        assert expected.equals(scores);
        assert !scores.equals(null);

        // 自己覆蓋自己，內容不變
        scores.putAll(scores);
        assert scores.equals(expected);

        // 容量滿時，仍能更新既有 key
        Map<String, Integer> bounded = new ArrayMap<>(1);
        bounded.put("A", 10);

        oldValue = bounded.put("A", 20);
        assert oldValue.equals(10);
        assert bounded.get("A").equals(20);

        // 但不能新增不同的 key
        boolean rejected = false;

        try {
            bounded.put("B", 30);
        } catch (IllegalStateException e) {
            rejected = true;
        }

        assert rejected;
        assert bounded.size() == 1;

        // 清空後重用
        scores.clear();
        assert scores.isEmpty();
        assert scores.size() == 0;

        scores.put("S005", 85);
        assert scores.get("S005").equals(85);

    }
}