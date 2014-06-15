package com.domagala.topn.sort;

import com.domagala.topn.reader.NumberReader;

import java.util.NavigableSet;
import java.util.TreeSet;

public class InMemorySorter implements Sorter {

    private final NumberReader reader;

    public InMemorySorter(NumberReader reader) {
        this.reader = reader;
    }

    @Override
    public NavigableSet<Integer> sort(int N) {
        TreeSet<Integer> topSorted = new TreeSet<>((o1, o2) -> o1.compareTo(o2));
        Integer number;

        while (reader.hasNext()) {
            number = reader.read();

            if (number == null) continue;

            topSorted.add(number);
            removeLastIfFull(N, topSorted);
        }
        // remove N+1 record
        removeLastIfFull(N, topSorted);

        return topSorted.descendingSet();
    }

    private void removeLastIfFull(int N, TreeSet<Integer> topSorted) {
        if (isSetFull(N, topSorted)) {
            topSorted.pollFirst();
        }
    }

    private boolean isSetFull(int N, TreeSet<Integer> topSorted) {
        return topSorted.size() == N+1;
    }
}
