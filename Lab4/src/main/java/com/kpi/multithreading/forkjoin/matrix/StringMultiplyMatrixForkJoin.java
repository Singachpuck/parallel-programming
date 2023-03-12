package com.kpi.multithreading.forkjoin.matrix;

import com.kpi.multithreading.matrix.model.Matrix;
import com.kpi.multithreading.matrix.service.MultiplyMatrix;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;

public class StringMultiplyMatrixForkJoin implements MultiplyMatrix {

    private final int THREAD_N;

    public StringMultiplyMatrixForkJoin() {
        this.THREAD_N = 10;
    }

    public StringMultiplyMatrixForkJoin(int thread_n) {
        this.THREAD_N = thread_n;
    }

    @Override
    public Matrix multiply(Matrix m1, Matrix m2) {
        if (m1.getWidth() != m2.getHeight()) {
            throw new IllegalArgumentException();
        }
        final Matrix result = new Matrix(m1.getHeight(), m2.getWidth());

        final int resultHeight = result.getHeight();
        final int resultWidth = result.getWidth();
        final double[][] resultTable = result.getTable();

        final ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
        for (int i = 0, items = resultWidth * resultHeight; i < items; i++) {
            final int index = i;

            final RecursiveAction cellAction = new RecursiveAction() {
                @Override
                protected void compute() {
                    final int resultI = index / resultWidth;
                    final int resultJ = index % resultWidth;
                    final RecursiveTask<Double> countCell = new CountCellRecursiveTask(
                            m1, m2, result, index, 0
                    );
                    ForkJoinTask.invokeAll(countCell);
                    resultTable[resultI][resultJ] = countCell.join();
                }
            };

            forkJoinPool.invoke(cellAction);
        }

        return result;

    }

    public int getThreadNumber() {
        return THREAD_N;
    }

    private static class CountCellRecursiveTask extends RecursiveTask<Double> {

        private final Matrix m1;

        private final Matrix m2;

        private final Matrix result;

        private final int index;

        private final int k;

        public CountCellRecursiveTask(Matrix m1, Matrix m2, Matrix result, int index, int k) {
            this.m1 = m1;
            this.m2 = m2;
            this.result = result;
            this.index = index;
            this.k = k;
        }

        @Override
        protected Double compute() {
            if (k < m1.getWidth()) {
                final CountCellRecursiveTask countNextCell = new CountCellRecursiveTask(
                        m1, m2, result, index, k + 1
                );
                final int resultI = index / result.getWidth();
                final int resultJ = index % result.getWidth();
                ForkJoinTask.invokeAll(countNextCell);
                return m1.getTable()[resultI][k] * m2.getTable()[k][resultJ] + countNextCell.join();
            } else {
                return 0.0;
            }
        }
    }
}
