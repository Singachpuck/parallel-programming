package com.kpi.multithreading.matrix.model;

import java.util.Random;

public class MatrixFactory {

    public static Matrix getRandomMatrix(int rows, int columns) {
        final Random random = new Random();
        final Matrix m = new Matrix(rows, columns);
        final double[][] table = m.getTable();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                table[i][j] = random.nextDouble();
            }
        }

        return m;
    }
}
