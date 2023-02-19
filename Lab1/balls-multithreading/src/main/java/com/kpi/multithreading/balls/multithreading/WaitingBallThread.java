package com.kpi.multithreading.balls.multithreading;

import com.kpi.multithreading.balls.model.Ball;

public class WaitingBallThread extends Thread {

    private final Ball b;

    private final Thread toWait;

    public WaitingBallThread(Ball ball, Thread toWait){
        super.setPriority(ball.getPriority());
        this.b = ball;
        this.toWait = toWait;
    }
    @Override
    public void run(){
        try{
            if (toWait != null) {
                toWait.join();
            }
            for(;;){
                b.move();
                System.out.println("Thread name = "
                        + Thread.currentThread().getName());
                Thread.sleep(5);
            }
        } catch(InterruptedException ex){
            System.out.println("Exiting thread: " + Thread.currentThread().getName());
        }
    }
}
