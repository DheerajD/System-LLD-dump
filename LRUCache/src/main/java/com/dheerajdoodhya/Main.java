package com.dheerajdoodhya;

import com.dheerajdoodhya.cache.Cache;
import com.dheerajdoodhya.factory.CacheFactory;

public class Main {
    public static void main(String args[]) {
        CacheFactory cacheFactory = new CacheFactory();
        Cache lruCache = cacheFactory.createCache("LRU", 4);
        lruCache.set(10, "A");
        lruCache.printCache();
        lruCache.set(15, "B");
        lruCache.printCache();
        lruCache.set(20, "C");
        lruCache.printCache();
        lruCache.set(25, "D");
        lruCache.printCache();
        lruCache.set(20, "C");
        lruCache.printCache();
        lruCache.set(40, "E");
        lruCache.printCache();
        lruCache.set(25, "D");
        lruCache.printCache();
    }
}
