package com.kpi.multithreading.queuesystems.model;

public class Task {
    private final long executionTime;

    public Task(long executionTime) {
        this.executionTime = executionTime;
    }

    public void execute() throws InterruptedException {
        Thread.sleep(executionTime);
    }

    public long getExecutionTime() {
        return executionTime;
    }
}
