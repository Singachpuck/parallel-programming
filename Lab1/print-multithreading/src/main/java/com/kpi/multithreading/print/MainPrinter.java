package com.kpi.multithreading.print;

public class MainPrinter {

    private static final Object lock = new Object();

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Print ordered:");
        printOrdered();
//        System.out.println("Print unordered");
//        printUnordered();
    }

    private static void printUnordered() throws InterruptedException {
        final Thread t1 = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                for (int j = 0; j < 100; j++) {
                    System.out.print('-');
                }
                System.out.println();
            }
        });
        final Thread t2 = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                for (int j = 0; j < 100; j++) {
                    System.out.print('|');
                }
                System.out.println();
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
            for (int i = 0; i < 100; i++) {
                for (int j = 0; j < 100; j++) {
                    synchronized (lock) {
                        lock.notifyAll();
                        System.out.print('-');
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                        }
                    }
                }
            }
        });
        final Thread t2 = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                for (int j = 0; j < 100; j++) {
                    synchronized (lock) {
                        lock.notifyAll();
                        System.out.print('|');
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                        }
                    }
                }
            }
        });

        t1.start();
        t2.start();

        Thread.sleep(500);
        t1.interrupt();
        t2.interrupt();
        System.out.println('\n');
    }
}
