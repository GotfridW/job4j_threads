package ru.job4j.pools;

import lombok.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class RowColSum {

    @AllArgsConstructor
    @Getter
    @Setter
    @EqualsAndHashCode
    public static class Sums {
        private int rowSum;
        private int colSum;
    }

    public static Sums sumRowCol(int[][] matrix, int index) {
        int rowSum = 0;
        int colSum = 0;
        for (int cell = 0; cell < matrix.length; cell++) {
            rowSum += matrix[index][cell];
            colSum += matrix[cell][index];
        }
        return new Sums(rowSum, colSum);
    }

    public static Sums[] sum(int[][] matrix) {
        Sums[] result = new Sums[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            result[i] = sumRowCol(matrix, i);
        }
        return result;
    }

    public static Sums[] asyncSum(int[][] matrix) {
        Sums[] result = new Sums[matrix.length];
        Map<Integer, CompletableFuture<Sums>> futures = new HashMap<>();
        for (int i = 0; i < result.length; i++) {
            int finalI = i;
            futures.put(i, CompletableFuture.supplyAsync(() ->
                    sumRowCol(matrix, finalI)));
            for (Integer key : futures.keySet()) {
                result[i] = futures.get(key).join();
            }
        }
        return result;
    }
}
