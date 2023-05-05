package com.kpi.multithreading.mpi;

import mpi.MPI;
import mpi.Request;

import java.util.ArrayList;
import java.util.List;

public class AsyncSimpleMatrixMultiplicationMpi {

    private static final int MASTER = 0;

    public static void main(String[] args) {
        int numtasks,
                taskid,
                numworkers,
                source,
                dest,
                rows, /* rows of matrix A sent to each worker */
                averow, extra,
                i, j, k, rc = -1;
        final int[] matrixSize = MatrixUtil.extractMatrixSize(args);

        final int NRA = matrixSize[0];
        final int NCA = matrixSize[1];
        final int NCB = matrixSize[3];
        int[][] a = new int[NRA][NCA], /* matrix A to be multiplied */
                b = new int[NCA][NCB], /* matrix B to be multiplied */
                c = new int[NRA][NCB]; /* result matrix C */
        MPI.Init(args);
        try {
            numtasks = MPI.COMM_WORLD.Size();
            taskid = MPI.COMM_WORLD.Rank();
            if (numtasks < 2) {
                System.err.println("Need at least two MPI tasks. Quitting...");
                MPI.COMM_WORLD.Abort(rc);
                return;
            }
            numworkers = numtasks - 1;
            if (taskid == MASTER) {
                System.out.println("mpi_mm has started with " + numtasks + " tasks.");
                for (i = 0; i < NRA; i++)
                    for (j = 0; j < NCA; j++)
                        a[i][j] = 10;
                for (i = 0; i < NCA; i++)
                    for (j = 0; j < NCB; j++)
                        b[i][j] = 10;

//                System.out.println("Matrix a:");
//                MatrixUtil.printMatrix(a);
//                System.out.println("Matrix b:");
//                MatrixUtil.printMatrix(b);
                averow = NRA / numworkers;
                extra = NRA % numworkers;
                int offset = 0;
                long before = System.nanoTime();
                for (dest = 1; dest <= numworkers; dest++) {
                    rows = (dest <= extra) ? averow + 1 : averow;
//                    System.out.printf("Sending %d rows to task %d offset= %d\n%n",
//                            rows, dest, offset);
                    MPI.COMM_WORLD.Isend(new int[]{ offset }, 0, 1, MPI.INT, dest, Tags.SEND_ROW_SHIFT.get());
                    MPI.COMM_WORLD.Isend(new int[]{ rows }, 0, 1, MPI.INT, dest, Tags.SEND_ROW_SIZE.get());
                    MPI.COMM_WORLD.Isend(a, offset, rows, MPI.OBJECT, dest, Tags.SEND_ROW.get());
                    MPI.COMM_WORLD.Isend(b, 0, NCA, MPI.OBJECT, dest, Tags.SEND_COL.get());
                    offset += rows;
                }

                final int[] recvOffset = new int[1];
                final int[] recvRows = new int[1];
                /* Receive results from worker tasks */
                final List<Request> requests  = new ArrayList<>();
                for (source = 1; source <= numworkers; source++) {
                    MPI.COMM_WORLD.Recv(recvOffset, 0, 1, MPI.INT, source, Tags.RECV_ROWS_SHIFT.get());
                    MPI.COMM_WORLD.Recv(recvRows, 0, 1, MPI.INT, source, Tags.RECV_ROWS_SIZE.get());
//                    MPI.COMM_WORLD.Irecv(c, recvOffset[0], recvRows[0], MPI.OBJECT, source, Tags.RECV_ROWS.get());
                    requests.add(MPI.COMM_WORLD.Irecv(c, recvOffset[0], recvRows[0], MPI.OBJECT, source, Tags.RECV_ROWS.get()));
                }

                for (Request request : requests) {
                    request.Wait();
                }
                long after = System.nanoTime();
                /* Print results */
                System.out.println("****");
                System.out.println("Result Matrix:");
                for (i=0; i<NRA; i++) {
                    System.out.println();
                    for (j=0; j<NCB; j++)
                        System.out.printf("%d ", c[i][j]);
                }
                System.out.println("\n********\n");
                System.out.println("Done.");

                System.out.println("Elapsed time: " + (after - before) / 1_000_000_000D);
            }
            // ************ worker task ***************** \\
            else { /* if (taskid > MASTER) */
                final Request irecvB = MPI.COMM_WORLD.Irecv(b, 0, NCA, MPI.OBJECT, MASTER, Tags.SEND_COL.get());
                final int[] recvOffset = new int[1];
                final int[] recvRows = new int[1];
                MPI.COMM_WORLD.Recv(recvOffset, 0, 1, MPI.INT, MASTER, Tags.SEND_ROW_SHIFT.get());
                MPI.COMM_WORLD.Recv(recvRows, 0, 1, MPI.INT, MASTER, Tags.SEND_ROW_SIZE.get());
                final Request irecvA = MPI.COMM_WORLD.Irecv(a, 0, recvRows[0], MPI.OBJECT, MASTER, Tags.SEND_ROW.get());
                MPI.COMM_WORLD.Isend(recvOffset, 0, 1, MPI.INT, MASTER, Tags.RECV_ROWS_SHIFT.get());
                MPI.COMM_WORLD.Isend(recvRows, 0, 1, MPI.INT, MASTER, Tags.RECV_ROWS_SIZE.get());
                irecvA.Wait();
                irecvB.Wait();
                for (k = 0; k < NCB; k++)
                    for (i = 0; i < recvRows[0]; i++) {
                        c[i][k] = 0;
                        for (j = 0; j < NCA; j++)
                            c[i][k] = c[i][k] + a[i][j] * b[j][k];
                    }
                MPI.COMM_WORLD.Isend(c, 0, recvRows[0], MPI.OBJECT, MASTER, Tags.RECV_ROWS.get());
            }
        } finally {
            MPI.Finalize();
        }
    }
}
