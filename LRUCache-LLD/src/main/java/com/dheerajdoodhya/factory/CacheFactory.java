package com.dheerajdoodhya.factory;

import com.dheerajdoodhya.cache.Cache;
import com.dheerajdoodhya.cache.LRUCache;

public class CacheFactory {
    public Cache createCache(String cacheType, int size) {
        if(cacheType.equals("LRU")) {
            return new LRUCache(size);
        }
        else
            throw new IllegalArgumentException();
    }
}
