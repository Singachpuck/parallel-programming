package com.kpi.multithreading.queuesystems.model;

import java.text.MessageFormat;

public class Statistics {

    private double averageQueueSize;

    private double declineProbability;

    public double getAverageQueueSize() {
        return averageQueueSize;
    }

    public void setAverageQueueSize(double averageQueueSize) {
        this.averageQueueSize = averageQueueSize;
    }

    public double getDeclineProbability() {
        return declineProbability;
    }

    public void setDeclineProbability(double declineProbability) {
        this.declineProbability = declineProbability;
    }

    @Override
    public String toString() {
        return MessageFormat.format("""
                Observed statistics:
                - Average queue size: {0}
                - Rejected/accepted ratio: {1}
                """, averageQueueSize, declineProbability);
    }
}
