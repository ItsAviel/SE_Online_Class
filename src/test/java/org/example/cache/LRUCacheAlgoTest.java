package org.example.cache;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LRUCacheAlgoTest {

    @Test
    void testPutAndGet() {
        CacheAlgo<Integer, String> cache = new LRUCacheAlgo<>(2);
        cache.put(1, "A");
        cache.put(2, "B");
        assertEquals("A", cache.get(1));
        assertEquals("B", cache.get(2));
    }

    @Test
    void testEviction() {
        CacheAlgo<Integer, String> cache = new LRUCacheAlgo<>(2);
        cache.put(1, "A");
        cache.put(2, "B");
        cache.get(1); // make 1 most recently used
        cache.put(3, "C"); // should evict 2

        assertTrue(cache.containsKey(1));
        assertFalse(cache.containsKey(2)); // evicted
        assertTrue(cache.containsKey(3));
    }

    @Test
    void testSize() {
        CacheAlgo<Integer, String> cache = new LRUCacheAlgo<>(2);
        cache.put(1, "A");
        cache.put(2, "B");
        assertEquals(2, cache.size());
    }
}
