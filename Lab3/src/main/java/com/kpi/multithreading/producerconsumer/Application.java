package com.kpi.multithreading.producerconsumer;

public class Application {

    private static final int SIZE = 100;
    public static void main(String[] args) {
        Drop<Integer> drop = new Drop<>();
        (new Thread(new IntegerProducer(drop, SIZE))).start();
        (new Thread(new Consumer(drop, -1))).start();
    }
}
