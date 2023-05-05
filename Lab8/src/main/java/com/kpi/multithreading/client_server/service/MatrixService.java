package com.kpi.multithreading.client_server.service;

import com.kpi.multithreading.client_server.model.ResultModel;
import com.kpi.multithreading.matrix.model.Matrix;
import com.kpi.multithreading.matrix.service.MultiplyMatrix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Service
public class MatrixService {

    @Autowired
    private Map<Integer, MultiplyMatrix> matrixMultiplies;

    public ResultModel calculateMatrix(int nThread, byte[] matrixFile1, byte[] matrixFile2) {
        final double[][] table1;
        final double[][] table2;

        table1 = this.readMatrix(new ByteArrayInputStream(matrixFile1));
        table2 = this.readMatrix(new ByteArrayInputStream(matrixFile2));

        final Matrix m1 = new Matrix(table1);
        final Matrix m2 = new Matrix(table2);
        final Matrix result = matrixMultiplies.get(nThread).multiply(m1, m2);
        return new ResultModel(result.toString());
    }

    public String calculateMatrix(int nThread, MultipartFile matrixFile1, MultipartFile matrixFile2) {
        final double[][] table1;
        final double[][] table2;

        try {
            table1 = this.readMatrix(matrixFile1.getInputStream());
            table2 = this.readMatrix(matrixFile2.getInputStream());
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error reading file!", e);
        }

        final Matrix m1 = new Matrix(table1);
        final Matrix m2 = new Matrix(table2);
        final Matrix result = matrixMultiplies.get(nThread).multiply(m1, m2);
        return result.toString();
    }

    public ResultModel extractMatricesLocallyAndCalculate(int size, int nThread) {
        final double[][] table1 = this.readMatrix(getClass().getClassLoader().getResourceAsStream("static/matrices/m1_" + size));
        final double[][] table2 = this.readMatrix(getClass().getClassLoader().getResourceAsStream("static/matrices/m2_" + size));

        final Matrix m1 = new Matrix(table1);
        final Matrix m2 = new Matrix(table2);
        final Matrix result = matrixMultiplies.get(nThread).multiply(m1, m2);
        return new ResultModel(result.toString());
    }

    private double[][] readMatrix(InputStream is) {
        final List<double[]> listMatrix = new ArrayList<>();
        try (Scanner scanner = new Scanner(is)) {
            while (scanner.hasNextLine()) {
                final String line = scanner.nextLine();

                if (!line.isBlank()) {
                    listMatrix.add(Arrays.stream(line.trim().split("\\s+")).mapToDouble(Double::parseDouble).toArray());
                }
            }
        }

        return listMatrix.toArray(double[][]::new);
    }
}
