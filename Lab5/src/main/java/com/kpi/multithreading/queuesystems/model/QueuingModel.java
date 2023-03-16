package com.kpi.multithreading.queuesystems.model;

import org.apache.commons.collections4.queue.CircularFifoQueue;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class QueuingModel {

    private final CircularFifoQueue<Task> taskQueue;

    private final RandomTaskProducer producer;

    private final List<QMTaskConsumer> consumers;

    private final Observer observer;

    private final AtomicInteger processed;

    private final AtomicInteger rejected;

    private final AtomicBoolean isServing;

    // Set with Defaults
    public QueuingModel() {
        this.processed = new AtomicInteger();
        this.rejected = new AtomicInteger();
        this.isServing = new AtomicBoolean(false);
        this.taskQueue = new CircularFifoQueue<>(2);
        this.consumers = IntStream
                .range(0, 10)
                .mapToObj(i -> new QMTaskConsumer(processed, taskQueue, isServing))
                .collect(Collectors.toList());
        this.producer = new RandomTaskProducer(50,
                200,
                1000,
                2000,
                taskQueue,
                rejected, isServing);
        this.observer = new Observer(this, 1000);
    }

    public QueuingModel(int consumerSize, int taskQueueSize,
                        long prodIntervalFrom, long prodIntervalTo,
                        long taskExecutionFrom, long taskExecutionTo,
                        int taskProcessedThreshold) {
        this.processed = new AtomicInteger();
        this.rejected = new AtomicInteger();
        this.isServing = new AtomicBoolean(false);
        this.taskQueue = new CircularFifoQueue<>(taskQueueSize);
        this.consumers = IntStream
                .range(0, consumerSize)
                .mapToObj(i -> new QMTaskConsumer(processed, taskQueue, isServing))
                .collect(Collectors.toList());
        this.producer = new RandomTaskProducer(prodIntervalFrom,
                prodIntervalTo,
                taskExecutionFrom,
                taskExecutionTo,
                taskQueue,
                rejected, isServing);
        this.observer = new Observer(this, taskProcessedThreshold);
    }

    public void launch() {
        final int workersSize = consumers.size() + 2;

        final ExecutorService executorService = Executors.newFixedThreadPool(workersSize);

        try {
            Future<?> observerFuture =  executorService.submit(observer);
            for (Runnable consumer : consumers) {
                executorService.submit(consumer);
            }

            executorService.submit(producer);

            observerFuture.get();
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            System.out.println("Execution finished!");
        } finally {
            executorService.shutdownNow();
        }
    }

    public CircularFifoQueue<Task> getTaskQueue() {
        return taskQueue;
    }

    public Observer getObserver() {
        return observer;
    }

    public AtomicInteger getProcessed() {
        return processed;
    }

    public AtomicInteger getRejected() {
        return rejected;
    }
}
