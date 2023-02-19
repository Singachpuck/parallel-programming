package com.kpi.multithreading.print;

public class MainPrinter {

    public static void main(String[] args) throws InterruptedException {
        printOrdered();
        printUnordered();
    }

    private static void printUnordered() throws InterruptedException {
        final Thread t1 = new Thread(() -> {
            for (int i = 0; i < 50; i++) {
                System.out.print('-');
            }
        });
        final Thread t2 = new Thread(() -> {
            for (int i = 0; i < 50; i++) {
                System.out.print('|');
            }
        });

        t1.start();
        t2.start();

        t1.join();
        t2.join();
        System.out.println('\n');
    }

    private static void printOrdered() throws InterruptedException {
        final Thread t1 = new Thread(() -> {
            for (int i = 0; i < 50; i++) {
                System.out.print('-');
            }
        });
        final Thread t2 = new Thread(() -> {
            try {
                t1.join();
                for (int i = 0; i < 50; i++) {
                    System.out.print('|');
                }
            } catch (InterruptedException e) {
                System.err.println("Interrupted!");
            }
        });

        t1.start();
        t2.start();

        t1.join();
        t2.join();
        System.out.println('\n');
    }
}
