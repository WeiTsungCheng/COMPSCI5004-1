package ABSTRACT_DATA_TYPES.interfaces;

public interface Map<K, V> {

    public boolean isEmpty();
    public int size();
    // 根據 key 查詢 value
    // key 不存在時回傳 null
    public V get(K key);

    // 比較兩個 Map 是否包含相同的 key-value 配對
    public boolean equals(Map<K, V> that);

    // 回傳所有 key 組成的集合
    public Set<K> keySet();

    // 清空所有配對
    public void  clear();

    // 移除指定 key 的配對，回傳被移除的 value
    // key 不存在時回傳 null
    public V remove(K key);

    // 新增或更新 key-value 配對
    // key 已存在：更新 value，回傳舊 value
    // key 不存在：新增配對，回傳 null
    public V put(K key, V val);

    public void putAll(Map<K, V> that);
    
}
