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

    private static <T> int linearSearch(T[] array, int from, int to, T key) {
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
        int result;
        if (to - from <= 10) {
            result = linearSearch(array, from, to, key);
        } else {
            int middle = (from + to) / 2;
            IndexSearch<T> leftSearch = new IndexSearch<>(array, from, middle, key);
            IndexSearch<T> rightSearch = new IndexSearch<>(array, middle + 1, to, key);
            leftSearch.fork();
            rightSearch.fork();
            Integer leftIndex = leftSearch.join();
            Integer rightIndex = rightSearch.join();
            result = Math.max(leftIndex, rightIndex);
        }
        return result;
    }

    public static <T> int search(T[] array, T key) {
        return new ForkJoinPool().invoke(new IndexSearch<>(array, 0, array.length - 1, key));
    }
}
