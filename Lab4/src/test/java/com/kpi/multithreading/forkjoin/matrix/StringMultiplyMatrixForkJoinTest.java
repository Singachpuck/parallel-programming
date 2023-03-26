package com.kpi.multithreading.forkjoin.matrix;

import com.kpi.multithreading.matrix.model.Matrix;
import com.kpi.multithreading.matrix.model.MatrixFactory;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class StringMultiplyMatrixForkJoinTest {

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
    @Order(4)
    void forkJoinMultiplyMatrixTestSmall(ForkJoinMultiplyMatrix stringMultiplyMatrix) {
        long before = System.nanoTime();
        Matrix multiplyM = stringMultiplyMatrix.multiply(m1, m2);
        long after = System.nanoTime();

        assertMatrix(multiplyM);
        System.out.println("Execution time forkJoinMultiplyMatrixTestSmall: " + (after - before) / 1_000_000_000D);
    }

    @ParameterizedTest
    @MethodSource("stringMultiplyMatrixSupply")
    @Order(5)
    void forkJoinMultiplyMatrixTestMedium(ForkJoinMultiplyMatrix stringMultiplyMatrix) {
        final Matrix m1 = MatrixFactory.getRandomMatrix(100, 100);
        final Matrix m2 = MatrixFactory.getRandomMatrix(100, 100);

        long before = System.nanoTime();
        stringMultiplyMatrix.multiply(m1, m2);
        long after = System.nanoTime();

        System.out.println("Execution time forkJoinMultiplyMatrixTestMedium: " + (after - before) / 1_000_000_000D);
    }

    @ParameterizedTest
    @MethodSource("stringMultiplyMatrixSupply")
    @Order(6)
    void forkJoinMultiplyMatrixTestBig(ForkJoinMultiplyMatrix stringMultiplyMatrix) {
        final Matrix m1 = MatrixFactory.getRandomMatrix(1000, 1000);
        final Matrix m2 = MatrixFactory.getRandomMatrix(1000, 1000);

        long before = System.nanoTime();
        stringMultiplyMatrix.multiply(m1, m2);
        long after = System.nanoTime();

        System.out.println("Execution time forkJoinMultiplyMatrixTestBig: " + (after - before) / 1_000_000_000D);
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