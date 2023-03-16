package com.kpi.multithreading.queuesystems.model;

import java.util.Queue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class QMTaskConsumer implements Consumer, Runnable {

    private final AtomicInteger processed;

    private final Queue<Task> taskQueue;

    private final AtomicBoolean isServing;

    public QMTaskConsumer(AtomicInteger processed, Queue<Task> taskQueue, AtomicBoolean isServing) {
        this.processed = processed;
        this.taskQueue = taskQueue;
        this.isServing = isServing;
    }

    @Override
    public void run() {
        this.consume();
    }

    @Override
    public void consume() {
        try {
            while (true) {
                if (Thread.currentThread().isInterrupted()) {
                    throw new InterruptedException();
                }

                final Task task;
                synchronized (taskQueue) {
                    if (taskQueue.isEmpty()) {
                        isServing.compareAndSet(false, true);
                        taskQueue.wait();
                        continue;
                    } else {
                        task = taskQueue.poll();
                    }
                }
                task.execute();
                processed.incrementAndGet();
            }
        } catch (InterruptedException e) {
            System.out.println("Consumption finished. Thread: " + Thread.currentThread().getName());
        }
    }
}
