package com.kpi.multithreading.matrix.model;

public class MatrixUtil {

    public static Matrix concatVertically(Matrix m1, Matrix m2) {
        final int m1Height = m1.getHeight();
        final int m2Height = m2.getHeight();
        final int resultHeight = m1Height + m2Height;
        final int resultWidth = m1.getWidth();
        if (resultWidth != m2.getWidth()) {
            throw new IllegalArgumentException("Can't concatenate");
        }

        final Matrix matrix = new Matrix(resultHeight, resultWidth);
        final double[][] m1Table = m1.getTable();
        final double[][] m2Table = m2.getTable();
        final double[][] matrixTable = matrix.getTable();
        for (int i = 0; i < m1Height; i++) {
            System.arraycopy(m1Table[i], 0, matrixTable[i], 0, resultWidth);
        }
        for (int i = 0; i < m2Height; i++) {
            System.arraycopy(m2Table[i], 0, matrixTable[m1Height + i], 0, resultWidth);
        }
        return matrix;
    }

    public static Matrix concatHorizontally(Matrix m1, Matrix m2) {
        final int m1Width = m1.getWidth();
        final int m2Width = m2.getWidth();
        final int resultWidth = m1Width + m2Width;
        final int resultHeight = m1.getHeight();
        if (resultHeight != m2.getHeight()) {
            throw new IllegalArgumentException("Can't concatenate");
        }

        final Matrix matrix = new Matrix(resultHeight, resultWidth);
        final double[][] m1Table = m1.getTable();
        final double[][] m2Table = m2.getTable();
        final double[][] matrixTable = matrix.getTable();
        for (int i = 0; i < m1Width; i++) {
            for (int j = 0; j < resultHeight; j++) {
                matrixTable[j][i] = m1Table[j][i];
            }
        }
        for (int i = 0; i < m2Width; i++) {
            for (int j = 0; j < resultHeight; j++) {
                matrixTable[j][m1Width + i] = m2Table[j][i];
            }
        }
        return matrix;
    }
}
