package com.kpi.multithreading.matrix.model;

public class SyncMultiplyMatrix implements MultiplyMatrix {

    @Override
    public Matrix multiply(Matrix m1, Matrix m2) {
        if (m1.getWidth() != m2.getHeight()) {
            throw new IllegalArgumentException();
        }
        final Matrix result = new Matrix(m1.getHeight(), m2.getWidth());

        final int resultHeight = result.getHeight();
        final int resultWidth = result.getWidth();
        final double[][] table1 = m1.getTable();
        final double[][] table2 = m2.getTable();
        final double[][] resultTable = result.getTable();

        for (int i = 0, items = resultWidth * resultHeight; i < items; i++) {
            final int resultI = i / resultWidth;
            final int resultJ = i % resultWidth;

            double value = 0;
            for (int k = 0; k < m1.getWidth(); k++) {
                value += table1[resultI][k] * table2[k][resultJ];
            }
            resultTable[resultI][resultJ] = value;
        }

        return result;
    }

    public Matrix multiplyBlock(Block b1, Block b2) {
        if (b1.getWidth() != b2.getHeight()) {
            throw new IllegalArgumentException();
        }
        final Matrix result = new Matrix(b1.getSizeI(), b2.getSizeJ());

        final int resultHeight = result.getHeight();
        final int resultWidth = result.getWidth();
        final double[][] table1 = b1.getTable();
        final double[][] table2 = b2.getTable();
        final double[][] resultTable = result.getTable();

        for (int i = 0, items = resultWidth * resultHeight; i < items; i++) {
            final int resultI = i / resultWidth;
            final int resultJ = i % resultWidth;

            double value = 0;
            for (int k = 0; k < b1.getSizeJ(); k++) {
                value += table1[resultI + b1.getOffsetI()][k + b1.getOffsetJ()] *
                        table2[k + b2.getOffsetI()][resultJ + b2.getOffsetJ()];
            }
            resultTable[resultI][resultJ] = value;
        }

        return result;
    }
}
