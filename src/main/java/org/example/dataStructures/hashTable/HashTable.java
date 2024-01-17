package org.example.dataStructures.hashTable;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class HashTable<K, V> implements Iterable<KeyValue<K, V>> {
    private static final int INITIAL_CAPACITY = 16;
    private static final double LOAD_FACTOR = 0.80d;
    private int collisions = 0;
    private LinkedList<KeyValue<K, V>>[] slots;
    private int size;

    public HashTable() {
        this(INITIAL_CAPACITY);
    }

    @SuppressWarnings("unchecked")
    public HashTable(int capacity) {
        slots = new LinkedList[capacity];
        size = 0;
    }

    public boolean containsValue(V value) {
        for (int i = 0; i < size; i++) {
            if (slots[i] != null) {
                for (KeyValue<K, V> kv : slots[i]) {
                    if (kv.getValue().equals(value)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void add(K key, V value) {
        addOrReplace(key, value);
    }

    private int findSlotNumber(K key) {
        int hashCode = key.hashCode();
        return Math.abs(hashCode) % slots.length;
    }

    private void growIfNeeded() {
        if ((double) (size + 1) / slots.length > LOAD_FACTOR) {
            grow();
        }
    }

    @SuppressWarnings("unchecked")
    private void grow() {
        int newCapacity = slots.length * 2;
        LinkedList<KeyValue<K, V>>[] newTable = new LinkedList[newCapacity];

        for (LinkedList<KeyValue<K, V>> list : slots) {
            if (list != null) {
                for (KeyValue<K, V> entry : list) {
                    int newSlot = Math.abs(entry.getKey().hashCode()) % newCapacity;
                    if (newTable[newSlot] == null) {
                        newTable[newSlot] = new LinkedList<>();
                    }
                    newTable[newSlot].add(entry);
                }
            }
        }
        slots = newTable;
    }

    public int size() {
        return size;
    }

    public int capacity() {
        return slots.length;
    }

    public boolean addOrReplace(K key, V value) {
        int slot = findSlotNumber(key);

        if (slots[slot] == null) {
            slots[slot] = new LinkedList<>();
        }

        for (KeyValue<K, V> entry : slots[slot]) {
            if (entry.getKey().equals(key)) {
                entry.setValue(value);
                return false;
            }
        }

        if (!slots[slot].isEmpty()) {
            collisions += 1;
        }
        slots[slot].add(new KeyValue<>(key, value, size++));
        growIfNeeded();
        return true;
    }

    public V get(K key) {
        int slot = findSlotNumber(key);
        if (slots[slot] != null) {
            for (KeyValue<K, V> entry : slots[slot]) {
                if (entry.getKey().equals(key)) {
                    return entry.getValue();
                }
            }
        }
        throw new NoSuchElementException(MessageFormat.format("No such element with key {0}", key));
    }

    public KeyValue<K, V> find(K key) {
        int slot = findSlotNumber(key);
        if (slots[slot] != null) {
            for (KeyValue<K, V> entry : slots[slot]) {
                if (entry.getKey().equals(key)) {
                    return entry;
                }
            }
        }
        throw new NoSuchElementException(MessageFormat.format("No such element with key {0}", key));
    }

    public boolean containsKey(K key) {
        try {
            find(key);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean remove(K key) {
        int slot = findSlotNumber(key);
        if (slots[slot] != null) {
            Iterator<KeyValue<K, V>> iterator = slots[slot].iterator();
            while (iterator.hasNext()) {
                KeyValue<K, V> entry = iterator.next();
                if (entry.getKey().equals(key)) {
                    iterator.remove();
                    size--;
                    return true;
                }
            }
        }
        return false;
    }

    public void clear() {
        Arrays.fill(slots, null);
        size = 0;
    }

    public Iterable<K> keys() {
        ArrayList<K> keyList = new ArrayList<>();
        for (LinkedList<KeyValue<K, V>> list : slots) {
            if (list != null) {
                for (KeyValue<K, V> kv : list) {
                    keyList.add(kv.getKey());
                }
            }
        }
        return keyList;
    }

    public Iterable<V> values() {
        ArrayList<V> valueList = new ArrayList<>();
        for (LinkedList<KeyValue<K, V>> list : slots) {
            if (list != null) {
                for (KeyValue<K, V> kv : list) {
                    valueList.add(kv.getValue());
                }
            }
        }
        return valueList;
    }


    public long collisionCount() {
        return Arrays.stream(slots)
                .filter(list -> list != null && !list.isEmpty())
                .count();
    }

    public int getCollisions() {
        return collisions;
    }

    @Override
    public Iterator<KeyValue<K, V>> iterator() {
        return new HashTableIterator<>(slots);
    }

    public static class HashTableIterator<K, V> implements Iterator<KeyValue<K, V>> {
        private final LinkedList<KeyValue<K, V>>[] slots;
        private int currentIndex = 0;
        private int listIndex = 0;

        public HashTableIterator(LinkedList<KeyValue<K, V>>[] slots) {
            this.slots = slots;
        }

        @Override
        public boolean hasNext() {
            while (listIndex < slots.length && currentIndex >= slots[listIndex].size()) {
                listIndex++;
                currentIndex = 0;
            }
            return listIndex < slots.length;
        }

        @Override
        public KeyValue<K, V> next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return slots[listIndex].findById(currentIndex++);
        }
    }
}