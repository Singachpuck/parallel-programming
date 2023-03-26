package com.kpi.multithreading.queuesystems.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

class QueuingModelTest {

    @Test
    void testQm() {
        final QueuingModel qm = new QueuingModel(10, 2,
                20, 150,
                1000, 2000,
                100);

        qm.launch();

        Statistics statistics = qm.getObserver().getStatistics();
        assertTrue(statistics.getDeclineProbability() > 0);
        assertTrue(statistics.getAverageQueueSize() > 0);
        synchronized (System.out) {
            System.out.println("Final statistics of QM:");
            System.out.println(statistics);
        }
    }

    @ParameterizedTest
    @ValueSource(ints = {4})
    void testQMParallel(int qmAmount) throws InterruptedException {
        final List<Thread> parallel = new ArrayList<>();
        for (int i = 0; i < qmAmount; i++) {
            final int index = i;
            final Thread t = new Thread(() -> {
                final QueuingModel qm = new QueuingModel(10, 2,
                        50, 150,
                        1000, 2000,
                        1000);

                qm.launch();

                Statistics statistics = qm.getObserver().getStatistics();
                synchronized (System.out) {
                    System.out.println("Final statistics of QM " + index + ":");
                    System.out.println(statistics);
                }
            });

            t.start();
            parallel.add(t);
        }

        for (Thread t : parallel) {
            t.join();
        }
    }
}