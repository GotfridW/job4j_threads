package ru.job4j.pools;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class IndexSearchTest {

    @Test
    void whenSearchIntegerAndAndSizeIsThirty() {
        Integer[] array = new Integer[30];
        for (int i = 0; i < array.length; i++) {
            array[i] = i;
        }
        assertThat(IndexSearch.search(array, 21)).isEqualTo(21);
    }

    @Test
    void whenSearchStringAndSizeBelowTen() {
        String[] array = new String[9];
        for (int i = 0; i < array.length; i++) {
            array[i] = String.valueOf(i);
        }
        assertThat(IndexSearch.search(array, "6")).isEqualTo(6);
    }

    @Test
    void whenElementNotFoundThenNegativeOne() {
        Integer[] array = new Integer[45];
        for (int i = 0; i < array.length; i++) {
            array[i] = i;
        }
        assertThat(IndexSearch.search(array, 50)).isNegative();
    }
}