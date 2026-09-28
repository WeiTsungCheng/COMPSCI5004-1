package ABSTRACT_DATA_TYPES;

import ABSTRACT_DATA_TYPES.ADTs.map.LinkedMap;
import ABSTRACT_DATA_TYPES.interfaces.Map;
import ABSTRACT_DATA_TYPES.interfaces.Set;

import java.util.Iterator;

public class Main6 {

    public static void main(String[] args) {
        Map<String, Integer> scores = new LinkedMap<>();

        assert scores.isEmpty();
        assert scores.size() == 0;

        // 插入尾端、前端及中間位置
        Integer oldValue = scores.put("S003", 70);
        assert oldValue == null;

        scores.put("S001", 80);
        scores.put("S002", 90);
        scores.put("S004", 60);

        assert scores.size() == 4;
        assert scores.get("S001").equals(80);
        assert scores.get("S003").equals(70);
        assert scores.get("S999") == null;

        // key 應維持排序
        Iterator<String> keys = scores.keySet().iterator();

        assert keys.next().equals("S001");
        assert keys.next().equals("S002");
        assert keys.next().equals("S003");
        assert keys.next().equals("S004");
        assert !keys.hasNext();

        // 更新既有 key，不增加大小
        oldValue = scores.put("S002", 95);

        assert oldValue.equals(90);
        assert scores.get("S002").equals(95);
        assert scores.size() == 4;

        // 刪除第一個節點
        Integer removed = scores.remove("S001");
        assert removed.equals(80);
        assert scores.get("S001") == null;

        // 刪除中間節點
        removed = scores.remove("S003");
        assert removed.equals(70);
        assert scores.get("S003") == null;

        // 刪除尾端節點
        removed = scores.remove("S004");
        assert removed.equals(60);
        assert scores.size() == 1;

        // 刪除不存在的 key
        removed = scores.remove("S999");
        assert removed == null;
        assert scores.size() == 1;

        // 刪除最後剩下的節點
        removed = scores.remove("S002");
        assert removed.equals(95);
        assert scores.isEmpty();

        // 空 Map 上移除
        removed = scores.remove("S001");
        assert removed == null;

        // 重新新增
        scores.put("S001", 80);
        scores.put("S002", 90);

        Map<String, Integer> other = new LinkedMap<>();
        other.put("S002", 100);
        other.put("S003", 75);

        // 覆蓋：更新 S002，新增 S003
        scores.putAll(other);

        assert scores.size() == 3;
        assert scores.get("S001").equals(80);
        assert scores.get("S002").equals(100);
        assert scores.get("S003").equals(75);

        // 來源 Map 不變
        assert other.size() == 2;
        assert other.get("S001") == null;

        // 比較兩個 LinkedMap
        Map<String, Integer> expected = new LinkedMap<>();
        expected.put("S003", 75);
        expected.put("S001", 80);
        expected.put("S002", 100);

        assert scores.equals(expected);
        assert expected.equals(scores);
        assert !scores.equals(null);

        expected.put("S002", 50);
        assert !scores.equals(expected);

        // keySet 是獨立集合
        Set<String> copiedKeys = scores.keySet();
        copiedKeys.clear();
        assert scores.size() == 3;

        // 自己覆蓋自己
        scores.putAll(scores);
        assert scores.size() == 3;
        assert scores.get("S002").equals(100);

        scores.clear();
        assert scores.isEmpty();

        scores.put("S005", 85);
        assert scores.get("S005").equals(85);

    }
}