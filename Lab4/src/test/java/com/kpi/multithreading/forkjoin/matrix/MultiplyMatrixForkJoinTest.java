package com.kpi.multithreading.forkjoin.matrix;

import com.kpi.multithreading.matrix.model.Matrix;
import com.kpi.multithreading.matrix.model.MatrixFactory;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MultiplyMatrixForkJoinTest {

    private final double[][] t1 = new double[][] {
            {1, 2, 3, 4},
            {5, 6, 7, 8},
            {9, 10, 11, 12},
            {13, 14, 15, 16}
    };

    private final double[][] t2 = new double[][] {
            {16, 15, 14, 13},
            {12, 11, 10, 9},
            {8, 7, 6, 5},
            {4, 3, 2, 1}
    };

    private final double[][] result = new double[][] {
            {80, 70, 60, 50},
            {240, 214, 188, 162},
            {400, 358, 316, 274},
            {560, 502, 444, 386}
    };

    private Matrix m1;

    private Matrix m2;

    private Matrix resultM;

    static Stream<Arguments> stringMultiplyMatrixSupply() {
        return Stream.of(
                Arguments.of(new ForkJoinMultiplyMatrix())
        );
    }

    @BeforeEach
    void setUp() {
        m1 = new Matrix(t1);
        m2 = new Matrix(t2);
        resultM = new Matrix(result);
    }

    @ParameterizedTest
    @MethodSource("stringMultiplyMatrixSupply")
    @Order(1)
    void forkJoinMultiplyMatrixTestSmall(ForkJoinMultiplyMatrix stringMultiplyMatrix) {
        long before = System.nanoTime();
        Matrix multiplyM = stringMultiplyMatrix.multiply(m1, m2);
        long after = System.nanoTime();

        assertMatrix(multiplyM);
        System.out.println("Execution time forkJoinMultiplyMatrixTestSmall: " + (after - before) / 1_000_000_000D);
    }

    @ParameterizedTest
    @MethodSource("stringMultiplyMatrixSupply")
    @Order(2)
    void forkJoinMultiplyMatrixTest50(ForkJoinMultiplyMatrix forkJoinMultiplyMatrix) {
        final Matrix m1 = MatrixFactory.getRandomMatrix(50, 50);
        final Matrix m2 = MatrixFactory.getRandomMatrix(50, 50);

        long before = System.nanoTime();
        forkJoinMultiplyMatrix.multiply(m1, m2);
        long after = System.nanoTime();

        System.out.println("Execution time forkJoinMultiplyMatrixTest50: " + (after - before) / 1_000_000_000D);
    }

    @ParameterizedTest
    @MethodSource("stringMultiplyMatrixSupply")
    @Order(3)
    void forkJoinMultiplyMatrixTest100(ForkJoinMultiplyMatrix forkJoinMultiplyMatrix) {
        final Matrix m1 = MatrixFactory.getRandomMatrix(100, 100);
        final Matrix m2 = MatrixFactory.getRandomMatrix(100, 100);

        long before = System.nanoTime();
        forkJoinMultiplyMatrix.multiply(m1, m2);
        long after = System.nanoTime();

        System.out.println("Execution time forkJoinMultiplyMatrixTest100: " + (after - before) / 1_000_000_000D);
    }

    @ParameterizedTest
    @MethodSource("stringMultiplyMatrixSupply")
    @Order(4)
    void forkJoinMultiplyMatrixTest500(ForkJoinMultiplyMatrix forkJoinMultiplyMatrix) {
        final Matrix m1 = MatrixFactory.getRandomMatrix(500, 500);
        final Matrix m2 = MatrixFactory.getRandomMatrix(500, 500);

        long before = System.nanoTime();
        forkJoinMultiplyMatrix.multiply(m1, m2);
        long after = System.nanoTime();

        System.out.println("Execution time forkJoinMultiplyMatrixTest500: " + (after - before) / 1_000_000_000D);
    }

    @ParameterizedTest
    @MethodSource("stringMultiplyMatrixSupply")
    @Order(5)
    void forkJoinMultiplyMatrixTest1000(ForkJoinMultiplyMatrix stringMultiplyMatrix) {
        final Matrix m1 = MatrixFactory.getRandomMatrix(1000, 1000);
        final Matrix m2 = MatrixFactory.getRandomMatrix(1000, 1000);

        long before = System.nanoTime();
        stringMultiplyMatrix.multiply(m1, m2);
        long after = System.nanoTime();

        System.out.println("Execution time forkJoinMultiplyMatrixTest1000: " + (after - before) / 1_000_000_000D);
    }

    @ParameterizedTest
    @MethodSource("stringMultiplyMatrixSupply")
    @Order(6)
    void forkJoinMultiplyMatrixTest2000(ForkJoinMultiplyMatrix forkJoinMultiplyMatrix) {
        final Matrix m1 = MatrixFactory.getRandomMatrix(2000, 2000);
        final Matrix m2 = MatrixFactory.getRandomMatrix(2000, 2000);

        long before = System.nanoTime();
        forkJoinMultiplyMatrix.multiply(m1, m2);
        long after = System.nanoTime();

        System.out.println("Execution time forkJoinMultiplyMatrixTest2000: " + (after - before) / 1_000_000_000D);
    }

    void assertMatrix(Matrix m) {
        assertMatrix(m, resultM);
    }

    void assertMatrix(Matrix m1, Matrix m2) {
        if (m1.getWidth() != m2.getWidth() || m1.getHeight() != m2.getHeight()) {
            Assertions.fail();
        }

        final double[][] assertTable = m1.getTable();
        final double[][] verifyTable = m2.getTable();
        for (int i = 0; i < m1.getHeight(); i++) {
            for (int j = 0; j < m1.getWidth(); j++) {
                Assertions.assertTrue(Math.abs(assertTable[i][j] - verifyTable[i][j]) < 1e-5);
            }
        }
    }
}