package com.kpi.multithreading.transfer;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Bank {
    public static final int NTEST = 10000;

    private final int[] accounts;

    /** Solution 2 */
//    private final AtomicIntegerArray accounts;

    private final AtomicLong ntransacts;

    private final Lock lock = new ReentrantLock();

    public Bank(int n, int initialBalance) {
        accounts = new int[n];
        int i;
        for (i = 0; i < accounts.length; i++)
            accounts[i] = initialBalance;
        ntransacts = new AtomicLong(0);
    }

    /** Solution 2 */
//    public Bank(int n, int initialBalance) {
//        accounts = new AtomicIntegerArray(n);
//        int i;
//        for (i = 0; i < accounts.length(); i++)
//            accounts.set(i, initialBalance);
//        ntransacts = new AtomicLong(0);
//    }

    /**
     *  Solution 1
     */
//    public synchronized void transfer(int from, int to, int amount) {
//        accounts[from] -= amount;
//        accounts[to] += amount;
//        ntransacts.incrementAndGet();
//        if (ntransacts.get() % NTEST == 0) {
//            test();
//            Thread.currentThread().interrupt();
//        }
//    }

    /** Solution 2 */
//    public void transfer(int from, int to, int amount) {
//        accounts.addAndGet(from, -amount);
//        accounts.addAndGet(to, amount);
//
//        ntransacts.incrementAndGet();
//        if (ntransacts.get() % NTEST == 0) {
//            test();
//            Thread.currentThread().interrupt();
//        }
//    }

    /** Solution 3 */
    public void transfer(int from, int to, int amount) {
        try {
            lock.lock();
            accounts[from] -= amount;
            accounts[to] += amount;
            if (ntransacts.incrementAndGet() % NTEST == 0) {
                test();
                Thread.currentThread().interrupt();
            }
        } finally {
            lock.unlock();
        }
    }

    /** Solution 4 */
//    public void transfer(int from, int to, int amount) {
//        synchronized (accounts) {
//            accounts[from] -= amount;
//            accounts[to] += amount;
//            if (ntransacts.incrementAndGet() % NTEST == 0) {
//                test();
//                Thread.currentThread().interrupt();
//            }
//        }
//    }

    public void test() {
        int sum = 0;
        for (int i = 0; i < accounts.length; i++)
            sum += accounts[i];
        System.out.println("Transactions:" + ntransacts
                + " Sum: " + sum);
    }

    /** Solution 2 */
//    public void test() {
//        int sum = 0;
//        for (int i = 0; i < accounts.length(); i++)
//            sum += accounts.get(i);
//        System.out.println("Transactions:" + ntransacts
//                + " Sum: " + sum);
//    }

    public int size() {
        return accounts.length;
    }

    /** Solution 2 */
//    public int size() {
//        return accounts.length();
//    }
}