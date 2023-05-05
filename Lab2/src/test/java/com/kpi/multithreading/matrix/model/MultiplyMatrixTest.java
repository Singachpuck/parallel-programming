package com.kpi.multithreading.matrix.model;

import com.kpi.multithreading.matrix.service.FoxMultiplyMatrix;
import com.kpi.multithreading.matrix.service.StringMultiplyMatrix;
import com.kpi.multithreading.matrix.service.SyncMultiplyMatrix;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MultiplyMatrixTest {

//    private final double[][] t1 = new double[][] {
//            {4, 2},
//            {9, 0}
//    };
//
//    private final double[][] t2 = new double[][] {
//            {3, 1},
//            {-3, 4}
//    };
//
//    private final double[][] result = new double[][] {
//            {6, 12},
//            {27, 9}
//    };

//    private final double[][] t1 = new double[][] {
//            {2, 1},
//            {-3, 0},
//            {4, -1}
//    };
//
//    private final double[][] t2 = new double[][] {
//            {5, -1, 6},
//            {-3, 0, 7}
//    };
//
//    private final double[][] result = new double[][] {
//            {7,	-2, 19},
//            {-15, 3, -18},
//            {23, -4, 17}
//    };

//    private final double[][] t1 = new double[][] {
//            {1, 2, 1},
//            {0, 1, 2},
//            {1, 1, 1}
//    };
//
//    private final double[][] t2 = new double[][] {
//            {1, 0, 2},
//            {2, 0, 3},
//            {1, 2, 1}
//    };
//
//    private final double[][] result = new double[][] {
//            {6, 2, 9},
//            {4, 4, 5},
//            {4, 2, 6}
//    };

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

    private final SyncMultiplyMatrix syncMultiplyMatrix = new SyncMultiplyMatrix();

    static Stream<Arguments> stringMultiplyMatrixSupply() {
        return Stream.of(
                Arguments.of(new StringMultiplyMatrix(2)),
                Arguments.of(new StringMultiplyMatrix(4)),
                Arguments.of(new StringMultiplyMatrix(6)),
                Arguments.of(new StringMultiplyMatrix(8)),
                Arguments.of(new StringMultiplyMatrix(10)),
                Arguments.of(new StringMultiplyMatrix(12)),
                Arguments.of(new StringMultiplyMatrix(14)),
                Arguments.of(new StringMultiplyMatrix(20))
        );
    }

    static Stream<Arguments> foxMultiplyMatrixSupply() {
        return Stream.of(
                Arguments.of(new FoxMultiplyMatrix(2)),
                Arguments.of(new FoxMultiplyMatrix(4)),
                Arguments.of(new FoxMultiplyMatrix(6)),
                Arguments.of(new FoxMultiplyMatrix(8)),
                Arguments.of(new FoxMultiplyMatrix(10)),
                Arguments.of(new FoxMultiplyMatrix(12)),
                Arguments.of(new FoxMultiplyMatrix(14)),
                Arguments.of(new FoxMultiplyMatrix(20))
        );
    }

    @BeforeEach
    void setUp() {
        m1 = new Matrix(t1);
        m2 = new Matrix(t2);
        resultM = new Matrix(result);
    }

    @Test
    @Disabled
    @Order(1)
    void syncMultiplyTestSmall() {
        long before = System.nanoTime();
        Matrix multiplyM = syncMultiplyMatrix.multiply(m1, m2);
        long after = System.nanoTime();

        assertMatrix(multiplyM);
        System.out.println("Execution time syncMultiplyTestSmall: " + (after - before) / 1_000_000_000D);
    }

    @Test
    @Disabled
    @Order(2)
    void syncMultiplyTest50() {
        final Matrix m1 = MatrixFactory.getRandomMatrix(50, 50);
        final Matrix m2 = MatrixFactory.getRandomMatrix(50, 50);

        long before = System.nanoTime();
        syncMultiplyMatrix.multiply(m1, m2);
        long after = System.nanoTime();

        System.out.println("Execution time syncMultiplyTestMedium: " + (after - before) / 1_000_000_000D);
    }

    @Test
    @Disabled
    @Order(2)
    void syncMultiplyTest100() {
        final Matrix m1 = MatrixFactory.getRandomMatrix(100, 100);
        final Matrix m2 = MatrixFactory.getRandomMatrix(100, 100);

        long before = System.nanoTime();
        syncMultiplyMatrix.multiply(m1, m2);
        long after = System.nanoTime();

        System.out.println("Execution time syncMultiplyTestMedium: " + (after - before) / 1_000_000_000D);
    }

    @Test
    @Disabled
    @Order(2)
    void syncMultiplyTest500() {
        final Matrix m1 = MatrixFactory.getRandomMatrix(500, 500);
        final Matrix m2 = MatrixFactory.getRandomMatrix(500, 500);

        long before = System.nanoTime();
        syncMultiplyMatrix.multiply(m1, m2);
        long after = System.nanoTime();

        System.out.println("Execution time syncMultiplyTestMedium: " + (after - before) / 1_000_000_000D);
    }

    @Test
    @Disabled
    @Order(2)
    void syncMultiplyTest1000() {
        final Matrix m1 = MatrixFactory.getRandomMatrix(1000, 1000);
        final Matrix m2 = MatrixFactory.getRandomMatrix(1000, 1000);

        long before = System.nanoTime();
        syncMultiplyMatrix.multiply(m1, m2);
        long after = System.nanoTime();

        System.out.println("Execution time syncMultiplyTestMedium: " + (after - before) / 1_000_000_000D);
    }

    @Test
    @Disabled
    @Order(3)
    void syncMultiplyTest1500() {
        final Matrix m1 = MatrixFactory.getRandomMatrix(1500, 1500);
        final Matrix m2 = MatrixFactory.getRandomMatrix(1500, 1500);

        long before = System.nanoTime();
        syncMultiplyMatrix.multiply(m1, m2);
        long after = System.nanoTime();

        System.out.println("Execution time syncMultiplyTest1500: " + (after - before) / 1_000_000_000D);
    }

    @Test
    @Disabled
    @Order(3)
    void syncMultiplyTest2000() {
        final Matrix m1 = MatrixFactory.getRandomMatrix(2000, 2000);
        final Matrix m2 = MatrixFactory.getRandomMatrix(2000, 2000);

        long before = System.nanoTime();
        syncMultiplyMatrix.multiply(m1, m2);
        long after = System.nanoTime();

        System.out.println("Execution time syncMultiplyTestBig: " + (after - before) / 1_000_000_000D);
    }

    @ParameterizedTest
    @MethodSource("stringMultiplyMatrixSupply")
    @Order(4)
    void stringMultiplyTestSmall(StringMultiplyMatrix stringMultiplyMatrix) {
        long before = System.nanoTime();
        Matrix multiplyM = stringMultiplyMatrix.multiply(m1, m2);
        long after = System.nanoTime();

        assertMatrix(multiplyM);
        System.out.println("Threads: " + stringMultiplyMatrix.getThreadNumber() + ". Execution time stringMultiplyTestSmall: " + (after - before) / 1_000_000_000D);
    }

    @ParameterizedTest
    @MethodSource("stringMultiplyMatrixSupply")
    @Order(5)
    void stringMultiplyTest50(StringMultiplyMatrix stringMultiplyMatrix) {
        final Matrix m1 = MatrixFactory.getRandomMatrix(50, 50);
        final Matrix m2 = MatrixFactory.getRandomMatrix(50, 50);

        long before = System.nanoTime();
        stringMultiplyMatrix.multiply(m1, m2);
        long after = System.nanoTime();

        System.out.println("Threads: " + stringMultiplyMatrix.getThreadNumber() + ". Execution time stringMultiplyTestMedium: " + (after - before) / 1_000_000_000D);
    }

    @ParameterizedTest
    @MethodSource("stringMultiplyMatrixSupply")
    @Order(5)
    void stringMultiplyTest100(StringMultiplyMatrix stringMultiplyMatrix) {
        final Matrix m1 = MatrixFactory.getRandomMatrix(100, 100);
        final Matrix m2 = MatrixFactory.getRandomMatrix(100, 100);

        long before = System.nanoTime();
        stringMultiplyMatrix.multiply(m1, m2);
        long after = System.nanoTime();

        System.out.println("Threads: " + stringMultiplyMatrix.getThreadNumber() + ". Execution time stringMultiplyTestMedium: " + (after - before) / 1_000_000_000D);
    }

    @ParameterizedTest
    @MethodSource("stringMultiplyMatrixSupply")
    @Order(5)
    void stringMultiplyTest500(StringMultiplyMatrix stringMultiplyMatrix) {
        final Matrix m1 = MatrixFactory.getRandomMatrix(500, 500);
        final Matrix m2 = MatrixFactory.getRandomMatrix(500, 500);

        long before = System.nanoTime();
        stringMultiplyMatrix.multiply(m1, m2);
        long after = System.nanoTime();

        System.out.println("Threads: " + stringMultiplyMatrix.getThreadNumber() + ". Execution time stringMultiplyTestMedium: " + (after - before) / 1_000_000_000D);
    }

    @ParameterizedTest
    @MethodSource("stringMultiplyMatrixSupply")
    @Order(5)
    void stringMultiplyTest1000(StringMultiplyMatrix stringMultiplyMatrix) {
        final Matrix m1 = MatrixFactory.getRandomMatrix(1000, 1000);
        final Matrix m2 = MatrixFactory.getRandomMatrix(1000, 1000);

        long before = System.nanoTime();
        stringMultiplyMatrix.multiply(m1, m2);
        long after = System.nanoTime();

        System.out.println("Threads: " + stringMultiplyMatrix.getThreadNumber() + ". Execution time stringMultiplyTestMedium: " + (after - before) / 1_000_000_000D);
    }

    @ParameterizedTest
    @MethodSource("stringMultiplyMatrixSupply")
    @Order(6)
    void stringMultiplyTest2000(StringMultiplyMatrix stringMultiplyMatrix) {
        final Matrix m1 = MatrixFactory.getRandomMatrix(2000, 2000);
        final Matrix m2 = MatrixFactory.getRandomMatrix(2000, 2000);

        long before = System.nanoTime();
        stringMultiplyMatrix.multiply(m1, m2);
        long after = System.nanoTime();

        System.out.println("Threads: " + stringMultiplyMatrix.getThreadNumber() + ". Execution time stringMultiplyTestBig: " + (after - before) / 1_000_000_000D);
    }

    @ParameterizedTest
    @MethodSource("foxMultiplyMatrixSupply")
    @Order(7)
    void foxMultiplyTestSmall(FoxMultiplyMatrix foxMultiplyMatrix) {
        long before = System.nanoTime();
        Matrix multiplyM = foxMultiplyMatrix.multiply(m1, m2);
        long after = System.nanoTime();

        assertMatrix(multiplyM);
        System.out.println("Threads: " + foxMultiplyMatrix.getThreadNumber() + ". Execution time foxMultiplyTestSmall: " + (after - before) / 1_000_000_000D);
    }

    @ParameterizedTest
    @MethodSource("foxMultiplyMatrixSupply")
    @Order(8)
    void foxMultiplyTest50(FoxMultiplyMatrix foxMultiplyMatrix) {
        final Matrix m1 = MatrixFactory.getRandomMatrix(50, 50);
        final Matrix m2 = MatrixFactory.getRandomMatrix(50, 50);

        long before = System.nanoTime();
        foxMultiplyMatrix.multiply(m1, m2);
        long after = System.nanoTime();

        System.out.println("Threads: " + foxMultiplyMatrix.getThreadNumber() + ". Execution time foxMultiplyTestMedium: " + (after - before) / 1_000_000_000D);
    }

    @ParameterizedTest
    @MethodSource("foxMultiplyMatrixSupply")
    @Order(8)
    void foxMultiplyTest100(FoxMultiplyMatrix foxMultiplyMatrix) {
        final Matrix m1 = MatrixFactory.getRandomMatrix(100, 100);
        final Matrix m2 = MatrixFactory.getRandomMatrix(100, 100);

        long before = System.nanoTime();
        foxMultiplyMatrix.multiply(m1, m2);
        long after = System.nanoTime();

        System.out.println("Threads: " + foxMultiplyMatrix.getThreadNumber() + ". Execution time foxMultiplyTestMedium: " + (after - before) / 1_000_000_000D);
    }

    @ParameterizedTest
    @MethodSource("foxMultiplyMatrixSupply")
    @Order(8)
    void foxMultiplyTest500(FoxMultiplyMatrix foxMultiplyMatrix) {
        final Matrix m1 = MatrixFactory.getRandomMatrix(500, 500);
        final Matrix m2 = MatrixFactory.getRandomMatrix(500, 500);

        long before = System.nanoTime();
        foxMultiplyMatrix.multiply(m1, m2);
        long after = System.nanoTime();

        System.out.println("Threads: " + foxMultiplyMatrix.getThreadNumber() + ". Execution time foxMultiplyTestMedium: " + (after - before) / 1_000_000_000D);
    }

    @ParameterizedTest
    @MethodSource("foxMultiplyMatrixSupply")
    @Order(8)
    void foxMultiplyTest1000(FoxMultiplyMatrix foxMultiplyMatrix) {
        final Matrix m1 = MatrixFactory.getRandomMatrix(1000, 1000);
        final Matrix m2 = MatrixFactory.getRandomMatrix(1000, 1000);

        long before = System.nanoTime();
        foxMultiplyMatrix.multiply(m1, m2);
        long after = System.nanoTime();

        System.out.println("Threads: " + foxMultiplyMatrix.getThreadNumber() + ". Execution time foxMultiplyTestMedium: " + (after - before) / 1_000_000_000D);
    }

    @ParameterizedTest
    @MethodSource("foxMultiplyMatrixSupply")
    @Order(9)
    void foxMultiplyTest2000(FoxMultiplyMatrix foxMultiplyMatrix) {
        final Matrix m1 = MatrixFactory.getRandomMatrix(2000, 2000);
        final Matrix m2 = MatrixFactory.getRandomMatrix(2000, 2000);

        long before = System.nanoTime();
        foxMultiplyMatrix.multiply(m1, m2);
        long after = System.nanoTime();

        System.out.println("Threads: " + foxMultiplyMatrix.getThreadNumber() + ". Execution time foxMultiplyTestBig: " + (after - before) / 1_000_000_000D);
    }

    void assertMatrix(Matrix m) {
        if (m.getWidth() != resultM.getWidth() || m.getHeight() != resultM.getHeight()) {
            Assertions.fail();
        }

        final double[][] assertTable = m.getTable();
        for (int i = 0; i < m.getHeight(); i++) {
            for (int j = 0; j < m.getWidth(); j++) {
                Assertions.assertEquals(assertTable[i][j], result[i][j]);
            }
        }
    }
}