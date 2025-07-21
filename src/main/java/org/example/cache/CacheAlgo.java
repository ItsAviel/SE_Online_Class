package org.example.cache;

public interface CacheAlgo<K, V> {
    void put(K key, V value);
    V get(K key);
    boolean containsKey(K key);
    int size();

    // -- Help method to remove an entry from the cache(used in delete operations):
    void remove(K key);
}
