package com.kpi.multithreading.mpi;

public enum Tags {
    SEND_ROW(1), SEND_COL(2), RECV_ROWS(3);

    private final int value;

    Tags(int value) {
        this.value = value;
    }

    public int get() {
        return value;
    }
}
