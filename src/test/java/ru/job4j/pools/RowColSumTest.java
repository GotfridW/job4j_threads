package ru.job4j.pools;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static org.assertj.core.api.Assertions.*;
import static ru.job4j.pools.RowColSum.*;

class RowColSumTest {

    @Test
    void whenSum() {
        int[][] matrix = new int[3][3];
        int value = 1;
        for (int[] ints : matrix) {
            Arrays.fill(ints, value++);
        }
        Sums[] expected = new Sums[] {
                new Sums(3, 6),
                new Sums(6, 6),
                new Sums(9, 6)
        };
        assertThat(sum(matrix)).isEqualTo(expected);
    }

    @Test
    void whenAsyncSum() {
        int[][] matrix = new int[4][4];
        int value = 1;
        for (int[] array : matrix) {
            Arrays.fill(array, value++);
        }
        Sums[] expected = new Sums[] {
                new Sums(4, 10),
                new Sums(8, 10),
                new Sums(12, 10),
                new Sums(16, 10)
        };
        assertThat(asyncSum(matrix)).isEqualTo(expected);
    }

    @Test
    void whenSumAndAsyncSumThenSameResult() {
        int[][] matrix = new int[500][500];
        Random r = new Random();
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                matrix[i][j] = r.nextInt(10);
            }
        }
        assertThat(sum(matrix)).isEqualTo(asyncSum(matrix));
    }
}