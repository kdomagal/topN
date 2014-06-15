package com.domagala.topn.sort;

import com.domagala.topn.reader.NumberReader;
import com.google.common.collect.Lists;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;
import java.util.List;
import java.util.NavigableSet;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertTrue;

public class InMemorySorterTest {

    MockNumberReader mockReader;

    @Before
    public void setup() {
        mockReader = new MockNumberReader();
    }

    @Test
    public void testSort() {
        mockReader.setList(Lists.newArrayList(5, 3, 7, 1, 8, 9));
        Sorter sorter = new InMemorySorter(mockReader);
        NavigableSet<Integer> sorted = sorter.sort(5);
        assertSorting(sorted);
        Assert.assertThat(sorted.first(), equalTo(9));
        Assert.assertThat(sorted.last(), equalTo(3));
    }

    @Test
    public void testSortDuplicatedNumbers_shouldNotHaveDuplicates() {
        mockReader.setList(Lists.newArrayList(5, 3, 7, 1, 8, 9, 5, 5));
        Sorter sorter = new InMemorySorter(mockReader);
        NavigableSet<Integer> sorted = sorter.sort(5);
        assertSorting(sorted);
        Assert.assertThat(sorted.first(), equalTo(9));
        Assert.assertThat(sorted.last(), equalTo(3));
    }

    @Test
    public void testSortEmptyList() {
        mockReader.setList(Lists.newArrayList());
        Sorter sorter = new InMemorySorter(mockReader);
        NavigableSet<Integer> sorted = sorter.sort(5);
        Assert.assertThat(sorted.size(), is(0));
    }

    private void assertSorting(NavigableSet<Integer> sorted) {
        Iterator<Integer> iter = sorted.iterator();
        Integer current;
        Integer previous = iter.next();
        while (iter.hasNext()) {
            current = iter.next();
            assertTrue(current < previous);
            previous = current;
        }
    }

    private class MockNumberReader implements NumberReader {
        Iterator<Integer> iterator;

        public void setList(List<Integer> list) {
            iterator = list.iterator();
        }

        @Override
        public Integer read() {
            return iterator.next();
        }

        @Override
        public boolean hasNext() {
            return iterator.hasNext();
        }
    }
}
