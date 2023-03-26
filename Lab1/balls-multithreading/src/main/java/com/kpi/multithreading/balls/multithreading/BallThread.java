package com.kpi.multithreading.balls.multithreading;

import com.kpi.multithreading.balls.model.Ball;

public class BallThread extends Thread {
    private final Ball b;

    public BallThread(Ball ball){
        super.setPriority(ball.getPriority());
        b = ball;
    }
    @Override
    public void run(){
        try{
            for(;;){
                b.move();
                if (b.scored) {
                    throw new InterruptedException();
                }
                System.out.println("Thread name = "
                        + Thread.currentThread().getName());
                Thread.sleep(5);
            }
        } catch(InterruptedException ex){
            System.out.println("Exiting thread: " + Thread.currentThread().getName());
        }
    }
}
