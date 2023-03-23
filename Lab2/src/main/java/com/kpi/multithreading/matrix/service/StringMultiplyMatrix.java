package com.kpi.multithreading.matrix.service;

import com.kpi.multithreading.matrix.model.Matrix;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class StringMultiplyMatrix implements MultiplyMatrix {

    private final int THREAD_N;

    private final List<Future<?>> tasks = new ArrayList<>();

    public StringMultiplyMatrix() {
        this.THREAD_N = 10;
    }

    public StringMultiplyMatrix(int thread_n) {
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
        final double[][] table1 = m1.getTable();
        final double[][] table2 = m2.getTable();
        final double[][] resultTable = result.getTable();

        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_N);

        try {
            for (int i = 0; i < resultHeight; i++) {
                final int resultI = i;

                // Process that counts line
                final Runnable countLine = () -> {
                    // Inside process, we count iteratively each cell
                    for (int resultJ = 0; resultJ < resultWidth; resultJ++) {
                        double value = 0;
                        for (int k = 0; k < m1.getWidth(); k++) {
                            value += table1[resultI][k] * table2[k][resultJ];
                        }
                        resultTable[resultI][resultJ] = value;
                    }
                };

                final Future<?> task = executorService.submit(countLine);

                tasks.add(task);
            }


            for (Future<?> task : tasks) {
                task.get();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException("Unexpected interruption!");
        } catch (ExecutionException e) {
            throw new RuntimeException("Computation error!");
        } finally {
            executorService.shutdown();
        }

        return result;
    }

    public int getThreadNumber() {
        return THREAD_N;
    }
}
