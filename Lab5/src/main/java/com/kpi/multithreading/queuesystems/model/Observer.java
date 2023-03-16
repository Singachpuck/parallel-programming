package com.kpi.multithreading.queuesystems.model;

public class Observer implements Runnable {

    private static final long CHECK_INTERVAL = 200;

    private static final int PRINT_FREQUENCY = 5;

    private final int taskProcessedThreshold;

    private long queueSizeSum;

    private long queueTimesCheck;

    private final QueuingModel qm;

    private final Statistics statistics = new Statistics();

    public Observer(QueuingModel qm, int taskProcessedThreshold) {
        this.qm = qm;
        this.taskProcessedThreshold = taskProcessedThreshold;
    }

    @Override
    public void run() {
        try {
            int processed, i = 0;
            while ((processed = qm.getProcessed().get()) < taskProcessedThreshold) {
                final int rejected = qm.getRejected().get();
                queueSizeSum += qm.getTaskQueue().size();
                queueTimesCheck++;
                statistics.setAverageQueueSize((double) queueSizeSum / queueTimesCheck);
                statistics.setDeclineProbability((double) rejected / processed);
                if (i >= PRINT_FREQUENCY) {
                    System.out.println(statistics);
                    i = -1;
                }
                i++;
                Thread.sleep(CHECK_INTERVAL);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            statistics.setAverageQueueSize((double) queueSizeSum / queueTimesCheck);
            statistics.setDeclineProbability((double) qm.getRejected().get() / qm.getProcessed().get());
            System.out.println("Observing finished. Thread: " + Thread.currentThread().getName());
        }
    }

    public Statistics getStatistics() {
        return statistics;
    }
}
