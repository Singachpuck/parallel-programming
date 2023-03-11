package com.kpi.multithreading.producerconsumer;

public class Consumer implements Runnable {
    private Object terminationObject;

    private Drop<?> drop;

    public Consumer(Drop<?> drop, Object terminationMessage) {
        this.drop = drop;
        this.terminationObject = terminationMessage;
    }

    public void run() {
        for (Object message = drop.take();
             ! message.equals(terminationObject);
             message = drop.take()) {
            System.out.format("MESSAGE RECEIVED: %s%n", message);
        }
    }
}
