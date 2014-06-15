package com.domagala.topn.sort;

import java.util.NavigableSet;

public interface Sorter {

    /**
     * Sorts in descending order top N highest numbers.
     * @param N Count of sorted numbers.
     * @return Sorted set.
     */
    NavigableSet<Integer> sort(int N);
}
