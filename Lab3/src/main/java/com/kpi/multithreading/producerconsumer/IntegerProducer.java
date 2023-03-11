package com.kpi.multithreading.producerconsumer;

import java.util.Random;

public class IntegerProducer implements Runnable {

    private final int size;
    private Drop<Integer> drop;

    public IntegerProducer(Drop<Integer> drop, int size) {
        this.drop = drop;
        this.size = size;
    }

    public void run() {
        final Random random = new Random();

        for (int i = 0; i < size; i++) {
            drop.put(random.nextInt(Integer.MAX_VALUE));
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {}
        }
        drop.put(-1);
    }

}
