package fise2.hpp.ok.utils;

import java.util.Comparator;

public class ExampleComparator<T extends Comparable<T>> implements Comparator<T> {
    public int compare(T obj1, T obj2) {
        if(obj1 == obj2) {
            return 0;
        }
        if(obj1 == null) {
            return -1;
        }
        if(obj2 == null) {
            return 1;
        }
        return obj1.compareTo(obj2);
    }
}