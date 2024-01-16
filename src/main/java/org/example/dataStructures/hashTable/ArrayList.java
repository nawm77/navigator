package org.example.dataStructures.hashTable;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;

public class ArrayList<T> implements Iterable<T> {
    private T[] array;
    private int size;
    private final int CAPACITY = 10;

    @SuppressWarnings("unchecked")
    public ArrayList() {
        this.array = (T[]) new Object[CAPACITY];
        this.size = 0;
    }

    public ArrayList(T[] array) {
        this.array = array;
        this.size = array.length;
    }

    @SuppressWarnings("unchecked")
    public ArrayList(int capacity) {
        this.array = (T[]) new Object[capacity];
        this.size = 0;
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayListIterator();
    }

    private void growIfNeed() {
        if (size == array.length) {
            int newCapacity = array.length * 2;
            array = Arrays.copyOf(array, newCapacity);
        }
    }

    public T add(T element) {
        growIfNeed();
        array[size++] = element;
        return element;
    }

    public T remove(T element) {
        for (int i = 0; i < array.length; i++) {
            if (element.equals(array[i])) {
                array[i] = null;
            }
        }
        return element;
    }

    public T remove(int id) {
        if (id > size) {
            throw new IndexOutOfBoundsException(MessageFormat.format("Index {0} is more than size", id));
        }
        T element = array[id];
        array[id] = null;
        return element;
    }

    public T[] sort(Comparator<T> comparator) {
        Arrays.sort(array, (a, b) -> {
            if (a == null && b == null) return 0;
            if (a == null) return -1;
            if (b == null) return 1;
            return comparator.compare(a, b);
        });
        return array;
    }

    public int indexOf(T value) {
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(value)) {
                return i;
            }
        }
        return -1;
    }

    public int size() {
        return this.size;
    }

    private class ArrayListIterator implements Iterator<T> {
        int currentIndex = 0;

        @Override
        public boolean hasNext() {
            return currentIndex < size;
        }
        @Override
        public T next() {
            if (!hasNext()) {
                throw new ArrayIndexOutOfBoundsException();
            }
            else {
                return array[currentIndex++];
            }
        }
    }
}
