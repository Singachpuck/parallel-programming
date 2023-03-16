package com.kpi.multithreading.queuesystems.model;

import org.apache.commons.collections4.queue.CircularFifoQueue;

import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class RandomTaskProducer implements Producer, Runnable {

    private final long prodIntervalFrom;

    private final long prodIntervalTo;

    private final long taskExecutionFrom;

    private final long taskExecutionTo;

    private final CircularFifoQueue<Task> taskQueue;

    private final AtomicInteger rejected;

    private final AtomicBoolean isServing;

    private final Random random = new Random();

    public RandomTaskProducer(long prodIntervalFrom,
                              long prodIntervalTo,
                              long taskExecutionFrom,
                              long taskExecutionTo,
                              CircularFifoQueue<Task> taskQueue,
                              AtomicInteger rejected, AtomicBoolean isServing) {
        this.prodIntervalFrom = prodIntervalFrom;
        this.prodIntervalTo = prodIntervalTo;
        this.taskExecutionFrom = taskExecutionFrom;
        this.taskExecutionTo = taskExecutionTo;
        this.taskQueue = taskQueue;
        this.rejected = rejected;
        this.isServing = isServing;
    }

    @Override
    public void run() {
        this.produce();
    }

    @Override
    public void produce() {
        try {
            while (true) {
                if (Thread.currentThread().isInterrupted()) {
                    throw new InterruptedException();
                }
                synchronized (taskQueue) {
                    if (taskQueue.isAtFullCapacity()) {
                        rejected.incrementAndGet();
                    } else {
                        if (isServing.get()) {
                            final boolean notify = taskQueue.isEmpty();
                            taskQueue.add(new Task(random.nextLong(taskExecutionFrom, taskExecutionTo)));
                            if (notify) {
                                taskQueue.notifyAll();
                            }
                        }
                    }
                }

                Thread.sleep(random.nextLong(prodIntervalFrom, prodIntervalTo));
            }
        } catch (InterruptedException e) {
            System.out.println("Production is finished! Thread: " + Thread.currentThread().getName());
        }
    }
}
