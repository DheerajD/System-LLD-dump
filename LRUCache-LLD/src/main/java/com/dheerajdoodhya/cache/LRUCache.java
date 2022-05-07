package com.dheerajdoodhya.cache;

import com.dheerajdoodhya.exception.DataNotFoundException;

import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

public class LRUCache implements Cache{
    private final int SIZE;

    /*Using Deque for easier implementation. Deque is a queue implemented with Doubly Linked List.
    https://www.geeksforgeeks.org/deque-interface-java-example/
    We can instead write our own DLL implementation as well and use that*/
    private Deque<Integer> doublyLinkedList;
    private HashMap<Integer, String> hashMap;

    public LRUCache(int size) {
        SIZE = size;
        doublyLinkedList = new LinkedList<>();
        hashMap = new HashMap<>();
    }

    @Override
    public void set(int key, String value) {
        System.out.println("Adding key to cache: " + key);
        if(!hashMap.containsKey(key)) {
            if(doublyLinkedList.size()==SIZE) {
                int last = doublyLinkedList.removeLast();
                hashMap.remove(last);
            }
        }
        else {
            doublyLinkedList.remove(key);
        }
        doublyLinkedList.addFirst(key);
        hashMap.put(key, value);
    }

    @Override
    public String get(int key) {
        if(hashMap.containsKey(key)) {
            doublyLinkedList.remove(key);
            doublyLinkedList.addFirst(key);
            return hashMap.get(key);
        }
        else try {
            throw new DataNotFoundException("Given key not present");
        } catch (DataNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void printCache() {
        Iterator itr = doublyLinkedList.iterator();
        System.out.println("Cache contents are: ");
        while(itr.hasNext()) {
            int num = (int) itr.next();
            String val = hashMap.get(num);
            System.out.println(num + "-> " + val);
        }
    }

    @Override
    public int size() {
        return doublyLinkedList.size();
    }
}
