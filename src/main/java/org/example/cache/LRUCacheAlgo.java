package org.example.cache;
import java.util.LinkedHashMap;
import java.util.Map;

public class LRUCacheAlgo<K, V> implements CacheAlgo<K, V> {
    private final int capacity;
    private final Map<K, V> cacheMap;

    public LRUCacheAlgo(int capacity) {
        this.capacity = capacity;
        this.cacheMap = new LinkedHashMap<>(capacity, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
                return size() > LRUCacheAlgo.this.capacity;
            }
        };
    }

    @Override
    public void put(K key, V value) {
        cacheMap.put(key, value);
    }

    @Override
    public V get(K key) {
        return cacheMap.get(key);
    }

    @Override
    public boolean containsKey(K key) {
        return cacheMap.containsKey(key);
    }

    @Override
    public int size() {
        return cacheMap.size();
    }

    @Override
    public void remove(K key) {
        cacheMap.remove(key);
    }
}
