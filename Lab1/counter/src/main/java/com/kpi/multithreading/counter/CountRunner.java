package com.kpi.multithreading.counter;

public class CountRunner {

    public static void main(String[] args) throws InterruptedException {
        countAsync();
        countSync();
    }

    private static void countAsync() throws InterruptedException {
        final Counter counter = new Counter();

        final Thread t1 = new Thread(() -> {
            for (int i = 0; i < 10_000_000; i++) {
                counter.increment();
            }
        });
        final Thread t2 = new Thread(() -> {
            for (int i = 0; i < 10_000_000; i++) {
                counter.decrement();
            }
        });

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println(counter.getCount());
    }

    private static void countSync() throws InterruptedException {
        final SyncCounter counter = new SyncCounter();

        final Thread t1 = new Thread(() -> {
            for (int i = 0; i < 10_000_000; i++) {
                counter.increment();
            }
        });
        final Thread t2 = new Thread(() -> {
            for (int i = 0; i < 10_000_000; i++) {
                counter.decrement();
            }
        });

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println(counter.getCount());
    }
}
