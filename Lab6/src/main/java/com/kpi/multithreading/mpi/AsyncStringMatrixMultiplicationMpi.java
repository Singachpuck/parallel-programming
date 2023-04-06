package com.kpi.multithreading.mpi;

import mpi.MPI;
import mpi.Request;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class AsyncStringMatrixMultiplicationMpi {

    public static void main(String[] args) {
        MPI.Init(args);

        try {
            final int numberOfProcessors = MPI.COMM_WORLD.Size();
            final int processorRank = MPI.COMM_WORLD.Rank();

            // Do not change
            final int distributeProcessor = 0;

            final int[] matrixSize = MatrixUtil.extractMatrixSize(args);

            final int numberOfWorkers;
            final int rows, cols;
            final int lastRows, lastCols;
            if (matrixSize[0] < numberOfProcessors - 1) {
                numberOfWorkers = matrixSize[0];
                rows = 1;
                lastRows = 1;
            } else {
                numberOfWorkers = numberOfProcessors - 1;
                rows = matrixSize[0] / numberOfWorkers;
                lastRows = rows + matrixSize[0] % numberOfWorkers;
            }

            if (matrixSize[3] < numberOfWorkers) {
                cols = 1;
                lastCols = 1;
            } else {
                cols = matrixSize[3] / numberOfWorkers;
                lastCols = cols + matrixSize[3] % numberOfWorkers;
            }

            if (processorRank == distributeProcessor) {
                try {
                    final int[][] matrix1 = MatrixUtil.generateRandomMatrix(matrixSize[0], matrixSize[1]);
                    final int[][] matrix2 = MatrixUtil.generateRandomMatrix(matrixSize[2], matrixSize[3]);

//                    System.out.println("Matrix 1:");
//                    MatrixUtil.printMatrix(matrix1);
//
//                    System.out.println("Matrix 2:");
//                    MatrixUtil.printMatrix(matrix2);

                    if (matrix1[0].length != matrix2.length) {
                        throw new IllegalArgumentException("Matrices are no compatible!");
                    }

                    for (int i = 0; i < numberOfWorkers; i++) {
                        final int nRows = i == numberOfWorkers - 1 ? lastRows : rows;
                        final int nCols = i == numberOfWorkers - 1 ? lastCols : cols;

                        final int rowsShift = i * cols;
                        final int[][] sendRows = new int[nRows][matrix1[0].length];
                        for (int j = 0; j < nRows; j++) {
                            System.arraycopy(matrix1[rowsShift + j], 0, sendRows[j], 0, sendRows[j].length);
                        }
                        final int colsShift = cols * i;
                        final int[][] sendCols = new int[nCols][matrixSize[2]];
                        for (int j = 0; j < nCols; j++) {
                            for (int k = 0; k < matrixSize[2]; k++) {
                                sendCols[j][k] = matrix2[k][colsShift + j];
                            }
                        }

                        MPI.COMM_WORLD.Isend(sendRows, 0, nRows, MPI.OBJECT, i + 1, Tags.SEND_ROW.get());
                        MPI.COMM_WORLD.Isend(new int[] { colsShift }, 0, 1, MPI.INT, i + 1, Tags.SEND_COL_SHIFT.get());
                        MPI.COMM_WORLD.Isend(new int[] { nCols }, 0, 1, MPI.INT, i + 1, Tags.SEND_COL_SIZE.get());
                        MPI.COMM_WORLD.Isend(sendCols, 0, nCols, MPI.OBJECT, i + 1, Tags.SEND_COL.get());
                    }

                    final int[] nRows = new int[1];
                    final int[][] result = new int[matrixSize[0]][matrixSize[3]];
                    int current = 0, i = 0;

                    final List<Request> requests = new ArrayList<>();
                    long before = System.nanoTime();
                    while (current < matrixSize[0]) {
                        MPI.COMM_WORLD.Recv(nRows, 0, 1, MPI.INT, i + 1, Tags.RECV_ROWS_SIZE.get());
                        final Request request = MPI.COMM_WORLD.Irecv(result,
                                current,
                                nRows[0],
                                MPI.OBJECT,
                                i + 1,
                                Tags.RECV_ROWS.get());
                        requests.add(request);
                        current += nRows[0];
                        i++;
                    }

                    for (Request request : requests) {
                        request.Wait();
                    }
                    long after = System.nanoTime();

//                    System.out.println("Result:");
//                    MatrixUtil.printMatrix(result);
                    System.out.println("Elapsed time: " + (after - before) / 1e9);
                } catch (RuntimeException e) {
                    System.out.println(e.getMessage());
                    MPI.COMM_WORLD.Abort(-1);
                }
            } else if (processorRank <= numberOfWorkers) {
                final boolean isLast = processorRank == numberOfWorkers;
                final int nRows = isLast ? lastRows : rows;

                final int[][] recvRows = new int[nRows][matrixSize[1]];
                MPI.COMM_WORLD.Recv(recvRows, 0, nRows, MPI.OBJECT, distributeProcessor, Tags.SEND_ROW.get());
//            for (int i = 0; i < nRows; i++) {
//                System.out.println(processorRank + " received rows:" + Arrays.toString(recvRows[i]));
//            }

                final int prev = processorRank == numberOfWorkers ? 1 : processorRank + 1;
                final int next = processorRank == 1 ? numberOfWorkers : processorRank - 1;
                int[] nCols = new int[1];
                int[] colsShift = new int[1];
                Request nColsRequest = MPI.COMM_WORLD.Irecv(nCols,
                        0,
                        1, MPI.INT,
                        distributeProcessor,
                        Tags.SEND_COL_SIZE.get());
                nColsRequest.Wait();
                int[][] recvCols = new int[nCols[0]][matrixSize[2]];
                Request recvColsRequest = MPI.COMM_WORLD.Irecv(recvCols,
                        0, nCols[0],
                        MPI.OBJECT,
                        distributeProcessor,
                        Tags.SEND_COL.get());
                final int[][] calcRow = new int[nRows][matrixSize[3]];
                MPI.COMM_WORLD.Recv(colsShift, 0, 1, MPI.INT, 0, Tags.SEND_COL_SHIFT.get());
                int current = colsShift[0];
                for (int col = 0; col < numberOfWorkers; col++) {
                    MPI.COMM_WORLD.Isend(new int[]{ nCols[0] },
                            0,
                            1,
                            MPI.INT,
                            next,
                            Tags.SEND_COL_SIZE.get());
                    recvColsRequest.Wait();

//                    if (processorRank == 2) {
//                        for (int i = 0; i < nCols[0]; i++) {
//                            System.out.println(processorRank + " received cols:" + Arrays.toString(recvCols[i]));
//                        }
//                    }
                    final int[][] sendCols = new int[nCols[0]][matrixSize[3]];
                    for (int j = 0; j < nCols[0]; j++) {
                        System.arraycopy(recvCols[j], 0, sendCols[j], 0, recvCols[j].length);
                    }
                    MPI.COMM_WORLD.Isend(sendCols,
                            0,
                            sendCols.length,
                            MPI.OBJECT,
                            next,
                            Tags.SEND_COL.get());
                    final int lastCurrent = current;
                    for (int i = 0; i < nRows; i++) {
                        for (int j = 0; j < nCols[0]; j++) {
                            for (int k = 0; k < matrixSize[2]; k++) {
                                calcRow[i][current] += recvRows[i][k] * recvCols[j][k];
                            }
                            current = (current + 1) % matrixSize[3];
                        }
                        if (i != nRows - 1) {
                            current = lastCurrent;
                        }
                    }

                    nColsRequest = MPI.COMM_WORLD.Irecv(
                            nCols,
                            0,
                            1,
                            MPI.INT,
                            prev,
                            Tags.SEND_COL_SIZE.get());

                    nColsRequest.Wait();
                    if (recvCols.length < nCols[0]) {
                        recvCols = new int[nCols[0]][matrixSize[3]];
                    }
                    recvColsRequest = MPI.COMM_WORLD.Irecv(
                            recvCols,
                            0,
                            nCols[0],
                            MPI.OBJECT,
                            prev,
                            Tags.SEND_COL.get());

//                if (processorRank == 2 && col == 0) {
//                    System.out.println();
//                    printMatrix(calcRow);
//                }

//                if (processorRank == 2) {
//                    printMatrix(calcRow);
//                }

//                    if (col == numberOfWorkers - 1) {
//                        break;
//                    }
//                    final int[][] sendCols = new int[nCols[0]][matrixSize[3]];
//                    for (int j = 0; j < nCols[0]; j++) {
//                        System.arraycopy(recvCols[j], 0, sendCols[j], 0, recvCols[j].length);
//                    }
//                    MPI.COMM_WORLD.Sendrecv(
//                            new int[]{ nCols[0] },
//                            0,
//                            1,
//                            MPI.INT,
//                            next,
//                            Tags.SEND_COL.get(),
//                            nCols,
//                            0,
//                            1,
//                            MPI.INT,
//                            prev,
//                            Tags.SEND_COL.get()
//                    );
//                    if (recvCols.length < nCols[0]) {
//                        recvCols = new int[nCols[0]][matrixSize[3]];
//                    }
//                    MPI.COMM_WORLD.Sendrecv(sendCols,
//                            0,
//                            sendCols.length,
//                            MPI.OBJECT,
//                            next,
//                            Tags.SEND_COL.get(),
//                            recvCols,
//                            0,
//                            nCols[0],
//                            MPI.OBJECT,
//                            prev,
//                            Tags.SEND_COL.get());
                }
//            if (processorRank == 3) {
//                System.out.println();
//                printMatrix(calcRow);
//            }
                MPI.COMM_WORLD.Send(new int[] { nRows }, 0, 1, MPI.INT, 0, Tags.RECV_ROWS_SIZE.get());
                MPI.COMM_WORLD.Send(calcRow, 0, nRows, MPI.OBJECT, 0, Tags.RECV_ROWS.get());
//                for (int i = 0; i < nRows; i++) {
//                    System.out.println(Arrays.toString(calcRow[i]));
//                }
            }
        } finally {
            MPI.Finalize();
        }
    }
}
