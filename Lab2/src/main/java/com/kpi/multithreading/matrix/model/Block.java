package com.kpi.multithreading.matrix.model;

public class Block extends Matrix {

    private final int offsetI;

    private final int offsetJ;

    private final int sizeI;

    private final int sizeJ;

    public Block(Matrix m, int offsetI, int offsetJ, int sizeI, int sizeJ) {
        super(m.getTable());
        this.offsetI = offsetI;
        this.offsetJ = offsetJ;
        this.sizeI = sizeI;
        this.sizeJ = sizeJ;
    }

    public int getOffsetI() {
        return offsetI;
    }

    public int getOffsetJ() {
        return offsetJ;
    }

    public int getSizeI() {
        return sizeI;
    }

    public int getSizeJ() {
        return sizeJ;
    }
}
