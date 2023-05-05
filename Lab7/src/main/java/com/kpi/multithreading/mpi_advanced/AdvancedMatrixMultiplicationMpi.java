package com.kpi.multithreading.mpi_advanced;

import com.kpi.multithreading.mpi.MatrixUtil;
import mpi.MPI;

public class AdvancedMatrixMultiplicationMpi {

    private static final int MASTER = 0;

    public static void main(String[] args) {
        MPI.Init(args);

        try {
            final int rank = MPI.COMM_WORLD.Rank();
            final int size = MPI.COMM_WORLD.Size();

//            final int nWorkers = size - 1;

            final int[] matrixSize = MatrixUtil.extractMatrixSize(args);

            if (matrixSize[0] % size != 0) {
                throw new IllegalStateException("Rows can't be distributed evenly");
            }
            final int nRows = matrixSize[0] / size;

            int[][] m1 = new int[matrixSize[0]][matrixSize[1]],
                    m2 = new int[matrixSize[2]][matrixSize[3]],
                    result = new int[matrixSize[0]][matrixSize[3]];

            long before = 0;

            if (rank == MASTER) {
                m1 = MatrixUtil.generateRandomMatrix(matrixSize[0], matrixSize[1]);
                m2 = MatrixUtil.generateRandomMatrix(matrixSize[2], matrixSize[3]);

                System.out.println("Matrix 1:");
                MatrixUtil.printMatrix(m1);
                System.out.println("Matrix 2:");
                MatrixUtil.printMatrix(m2);

                before = System.nanoTime();
            }

            final int[][] rows = new int[nRows][matrixSize[1]];
            final int[][] countRows = new int[nRows][matrixSize[3]];

            MPI.COMM_WORLD.Scatter(m1, 0, nRows, MPI.OBJECT, rows, 0, nRows, MPI.OBJECT, MASTER);
            MPI.COMM_WORLD.Bcast(m2, 0, matrixSize[2], MPI.OBJECT, MASTER);

            for (int k = 0; k < matrixSize[3]; k++) {
                for (int i = 0; i < nRows; i++) {
                    countRows[i][k] = 0;
                    for (int j = 0; j < matrixSize[2]; j++)
                        countRows[i][k] += rows[i][j] * m2[j][k];
                }
            }

            MPI.COMM_WORLD.Gather(countRows, 0, nRows, MPI.OBJECT, result, 0, nRows, MPI.OBJECT, MASTER);

            if (rank == MASTER) {
                long after = System.nanoTime();
                System.out.println("Result:");
                MatrixUtil.printMatrix(result);
                System.out.println("Elapsed time: " + (after - before) / 1_000_000_000D);
            }
        } finally {
            MPI.Finalize();
        }
    }
}
