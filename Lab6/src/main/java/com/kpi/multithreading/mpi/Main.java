package com.kpi.multithreading.mpi;

import mpi.MPI;
import mpi.Status;

import java.util.Random;

public class Main {

    public static void main(String[] args) {
        final Random random = new Random();
        try {
            MPI.Init(args);
            int me = MPI.COMM_WORLD.Rank();
            int size = MPI.COMM_WORLD.Size();
            System.out.println(me + " " + size);

            int next = (me + 1) % size;
            int prev = me == 0 ? size - 1 : me - 1;
            int[] receiveBuf = new int[1];
            final Status status = MPI.COMM_WORLD.Sendrecv(
                    new int[]{me},
                    0,
                    1,
                    MPI.INT,
                    next,
                    1,
                    receiveBuf,
                    0,
                    1,
                    MPI.INT,
                    prev,
                    1
            );

            System.out.println("Received from " + prev + " - " + receiveBuf[0]);
        } finally {
            MPI.Finalize();
        }
    }
}
