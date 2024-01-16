package org.example.dataStructures.hashTable;

import java.util.Iterator;
import java.util.stream.Stream;

public class MonoHashTable<T> implements Iterable<KeyValue<Integer, T>> {
    private final HashTable<Integer, T> hashTable = new HashTable<>();
    @Override
    public Iterator<KeyValue<Integer, T>> iterator() {
        return hashTable.iterator();
    }

    public T add(T value) {
        hashTable.add(value.hashCode(), value);
        return value;
    }

    public boolean addOrReplace(T value) {
        return hashTable.addOrReplace(value.hashCode(), value);
    }

    public T get(Integer hashCode) {
        return hashTable.get(hashCode);
    }

    public Integer getOrder(Integer hashCode) {
        return hashTable.find(hashCode).getOrder();
    }

    public KeyValue<Integer, T> find(Integer hashCode) {
        return hashTable.find(hashCode);
    }

    public int size() {
        return hashTable.size();
    }

    public int capacity() {
        return hashTable.capacity();
    }

    public boolean containsKey(Integer hashCode) {
        return hashTable.containsKey(hashCode);
    }

    public boolean containsValue(T value) {
        for(T r : hashTable.values()) {
            if (value.equals(r)) {
                return true;
            }
        }
        return false;
    }

    public boolean remove(Integer hashCode) {
        return hashTable.remove(hashCode);
    }

    public void clear() {
        hashTable.clear();
    }

    public Iterable<Integer> keys() {
        return hashTable.keys();
    }

    public Iterable<T> values() {
        return hashTable.values();
    }

    public long collisionCount() {
        return hashTable.collisionCount();
    }

    public int getCollisions() {
        return hashTable.getCollisions();
    }
}
