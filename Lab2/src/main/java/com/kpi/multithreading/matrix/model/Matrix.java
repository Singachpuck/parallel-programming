package com.kpi.multithreading.matrix.model;

public class Matrix {

    protected final double[][] table;
    protected final int width;

    protected final int height;

    public Matrix(int i, int j) {
        this.width = j;
        this.height = i;
        this.table = new double[i][j];
    }

    public Matrix(double[][] table) {
        if (table.length == 0) {
            throw new IllegalArgumentException();
        }
        this.height = table.length;
        this.width = table[0].length;
        this.table = table;
    }

    public void addBlock(Matrix m, int i, int j) {
        final int blockHeight = m.getHeight();
        final int blockWidth = m.getWidth();
        final double[][] blockTable = m.getTable();
        for (int k = 0; k < blockHeight; k++) {
            for (int l = 0; l < blockWidth; l++) {
                this.table[i * blockHeight + k][j * blockWidth + l] += blockTable[k][l];
            }
        }
    }

    public Block[][] split(int blockHeight, int blockWidth) {
        final int blockCountX = width / blockWidth;
        final int blockCountY = height / blockHeight;
        final Block[][] blocks = new Block[blockCountY][blockCountX];

        for (int i = 0; i < blockCountY; i++) {
            for (int j = 0; j < blockCountX; j++) {
                blocks[i][j] = new Block(this, i * blockHeight, j * blockWidth, blockHeight, blockWidth);
            }
        }
        return blocks;
    }

//    public Matrix

    public double[][] getTable() {
        return table;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}
