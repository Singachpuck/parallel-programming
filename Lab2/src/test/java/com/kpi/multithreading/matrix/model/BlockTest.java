package com.kpi.multithreading.matrix.model;

import com.kpi.multithreading.matrix.service.SyncMultiplyMatrix;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BlockTest {

    private final double[][] t1 = new double[][] {
            {2, 1},
            {-3, 0},
            {4, -1}
    };

    private final double[][] t2 = new double[][] {
            {5, -1, 6},
            {-3, 0, 7}
    };

    private final double[][] result = new double[][] {
            {3, -18},
            {-4, 17}
    };

    private Matrix m1;

    private Matrix m2;

    private Matrix resultM;

    private final SyncMultiplyMatrix syncMultiplyMatrix = new SyncMultiplyMatrix();

    @BeforeEach
    void setUp() {
        m1 = new Matrix(t1);
        m2 = new Matrix(t2);
        resultM = new Matrix(result);
    }

    @Test
    void multiplicationBlockTest() {
        final Block b1 = new Block(m1, 1, 0, 2, 2);
        final Block b2 = new Block(m2, 0, 1, 2, 2);

        assertMatrix(syncMultiplyMatrix.multiplyBlock(b1, b2));
    }

    @Test
    void toBlock() {
        final Block[][] blocks = m1.split(1, 2);

        Assertions.assertEquals(3, blocks.length);
        Assertions.assertEquals(1, blocks[0].length);
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