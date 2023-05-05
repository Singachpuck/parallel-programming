package com.kpi.multithreading.client_server;

import com.kpi.multithreading.mpi.MatrixUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class GenerateMatrixTest {

    public static final String dstDirectory = "TODO:";

    @ParameterizedTest
    @ValueSource(ints = {50, 100, 500, 1000, 1500, 2000})
    void generate(int size) throws IOException {
        int[][] m1 = MatrixUtil.generateRandomMatrix(size, size);
        int[][] m2 = MatrixUtil.generateRandomMatrix(size, size);

        final String m1FileName = "m1_" + size;
        final String m2FileName = "m2_" + size;

        Path m1File = Paths.get(dstDirectory + "\\" + m1FileName);
        Path m2File = Paths.get(dstDirectory + "\\" + m2FileName);

        StringBuilder sb = new StringBuilder();
        Assertions.assertEquals(size, m1.length);
        for (int i = 0; i < m1.length; i++) {
            Assertions.assertEquals(size, m1[i].length);
            for (int j = 0; j < m1[i].length; j++) {
                sb.append(m1[i][j]).append(' ');
            }
            sb.append('\n');
        }
        Files.write(m1File, sb.toString().getBytes());

        sb = new StringBuilder();
        Assertions.assertEquals(size, m2.length);
        for (int i = 0; i < m2.length; i++) {
            Assertions.assertEquals(size, m2[i].length);
            for (int j = 0; j < m2[i].length; j++) {
                sb.append(m2[i][j]).append(' ');
            }
            sb.append('\n');
        }
        Files.write(m2File, sb.toString().getBytes());
    }
}
