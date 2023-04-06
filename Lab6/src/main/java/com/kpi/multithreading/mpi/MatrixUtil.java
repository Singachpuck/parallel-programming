package com.kpi.multithreading.mpi;

import java.util.Random;

public class MatrixUtil {

    public static int[] extractMatrixSize(String[] args) {
        final int[] size = new int[4];
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-m1")) {
                if (i == args.length - 1) {
                    throw new IllegalArgumentException("Invalid matrix 1 argument");
                }
                final String[] m1Size = args[i + 1].split(",");
                size[0] = Integer.parseInt(m1Size[0]);
                size[1] = Integer.parseInt(m1Size[1]);
            } else if (args[i].equals("-m2")) {
                if (i == args.length - 1) {
                    throw new IllegalArgumentException("Invalid cols arguments");
                }
                final String[] m2Size = args[i + 1].split(",");
                size[2] = Integer.parseInt(m2Size[0]);
                size[3] = Integer.parseInt(m2Size[1]);
            }
        }

        for (int i = 0; i < size.length; i++) {
            if (size[i] <= 0) {
                throw new IllegalArgumentException("Size is not set!");
            }
        }
        return size;
    }

    public static int[][] generateRandomMatrix(int i, int j) {
        final Random random = new Random();
        final int[][] matrix = new int[i][j];

        for (int k = 0; k < i; k++) {
            for (int l = 0; l < j; l++) {
                matrix[k][l] = random.nextInt(10);
            }
        }

        return matrix;
    }

    public static void printMatrix(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }
}
