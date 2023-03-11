package com.kpi.multithreading.transfer.thread;

import com.kpi.multithreading.transfer.Bank;

public class TransferThread extends Thread {
    private Bank bank;
    private int fromAccount;
    private int maxAmount;
    private static final int REPS = 1000;
    public TransferThread(Bank b, int from, int max){
        bank = b;
        fromAccount = from;
        maxAmount = max;
    }
    @Override
    public void run(){
        while (true) {
            if (Thread.currentThread().isInterrupted()) {
                break;
            }
            int toAccount = (int) (bank.size() * Math.random());
            int amount = (int) (maxAmount * Math.random()/REPS);
            bank.transfer(fromAccount, toAccount, amount);
        }
    }
}
