package com.dheerajdoodhya.cache;

/*
* Below <K,V> can be used in multipe cachging scenarios.
* Ex. LRU cache needed for pagination in OS; K -> page number, V -> page content
* Ex. LRU cache needed in instagram for fetching number of followers; K -> userId, V -> No. of followers
* Ex. LRU cache needed in instagram for fetching last 5 pics of user; K -> userId, update V to List<image-URLs>
* */
public interface Cache {
    public void set(int key, String value);
    public String get(int key);
    public void printCache();
    public int size();
}
