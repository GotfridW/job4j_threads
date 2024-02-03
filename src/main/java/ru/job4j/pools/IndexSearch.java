package ru.job4j.pools;

import lombok.AllArgsConstructor;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

@AllArgsConstructor
public class IndexSearch<T> extends RecursiveTask<Integer> {
    private final T[] array;
    private final int from;
    private final int to;
    private final T key;

    private int linearSearch() {
        int result = -1;
        for (int i = from; i <= to; i++) {
            if (key.equals(array[i])) {
                result = i;
                break;
            }
        }
        return result;
    }

    @Override
    protected Integer compute() {
        if (to - from <= 10) {
            return linearSearch();
        }
        int middle = (from + to) / 2;
        IndexSearch<T> leftSearch = new IndexSearch<>(array, from, middle, key);
        IndexSearch<T> rightSearch = new IndexSearch<>(array, middle + 1, to, key);
        leftSearch.fork();
        rightSearch.fork();
        Integer leftIndex = leftSearch.join();
        Integer rightIndex = rightSearch.join();
        return Math.max(leftIndex, rightIndex);
    }

    public static <T> int search(T[] array, T key) {
        return new ForkJoinPool().invoke(new IndexSearch<>(array, 0, array.length - 1, key));
    }
}
