package com.kpi.multithreading.forkjoin.commonwords;

public class Position {

    private final int begin;

    private final int end;

    public Position(int begin, int end) {
        this.begin = begin;
        this.end = end;
    }

    public int getBegin() {
        return begin;
    }

    public int getEnd() {
        return end;
    }
}
