package com.kpi.multithreading.counter;

public class SyncCounter {

    private int count;

    /** Solution 1 */
    public synchronized void increment() {
        count++;
    }

    public synchronized void decrement() {
        count--;
    }

    /** Solution 2 */
//    public void increment() {
//        synchronized (this) {
//            count++;
//        }
//    }
//
//    public void decrement() {
//        synchronized (this) {
//            count--;
//        }
//    }

    public int getCount() {
        return count;
    }
}
