package com.kpi.multithreading.matrix.model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FoxMultiplyMatrix implements MultiplyMatrix {

    private final int THREAD_N;

    private final List<Future<?>> tasks = new ArrayList<>();

    public FoxMultiplyMatrix() {
        this.THREAD_N = 10;
    }

    public FoxMultiplyMatrix(int thread_n) {
        this.THREAD_N = thread_n;
    }

    @Override
    public Matrix multiply(Matrix m1, Matrix m2) {
        if (m1.getWidth() != m2.getHeight() || m1.getWidth() != m1.getHeight() || m2.getWidth() != m2.getHeight()) {
            throw new IllegalArgumentException();
        }
        final Matrix result = new Matrix(m1.getHeight(), m2.getWidth());

        final int blockSize = this.findBlockSize(m1.getWidth());
        final int blockAmount = result.getWidth() / blockSize;
        final Block[][] blocks1 = m1.split(blockSize, blockSize);
        final Block[][] blocks2 = m2.split(blockSize, blockSize);
        final SyncMultiplyMatrix syncMultiplyMatrix = new SyncMultiplyMatrix();

        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_N);

        try {
            for (int i = 0; i < blockAmount; i++) {
                for (int j = 0; j < blockAmount; j++) {
                    final int taskI = i;
                    final int taskJ = j;

                    final Runnable countItem = () -> {
                        for (int stage = 0; stage < blockAmount; stage++) {
                            int k_bar = (taskI + stage) % blockAmount;
                            result.addBlock(syncMultiplyMatrix.multiplyBlock(blocks1[taskI][k_bar], blocks2[k_bar][taskJ]), taskI, taskJ);
                        }
                    };

                    final Future<?> task = executorService.submit(countItem);

                    tasks.add(task);
                }
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

    private int findBlockSize(int matrixSize) {
        for (int i = Math.min(10, matrixSize - 1); i > 1; i--) {
            if (matrixSize % i == 0) {
                return i;
            }
        }

        return 1;
    }

    public int getThreadNumber() {
        return THREAD_N;
    }
}
