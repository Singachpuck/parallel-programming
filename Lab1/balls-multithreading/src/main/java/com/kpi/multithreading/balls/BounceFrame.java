package com.kpi.multithreading.balls;

import com.kpi.multithreading.balls.model.Ball;
import com.kpi.multithreading.balls.model.BallFactory;
import com.kpi.multithreading.balls.model.GameMode;
import com.kpi.multithreading.balls.multithreading.BallThread;
import com.kpi.multithreading.balls.multithreading.WaitingBallThread;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Consumer;

public class BounceFrame extends JFrame {

    public static final int WIDTH = 450;

    public static final int HEIGHT = 350;

    private final BallCanvas canvas;

    private final List<Thread> usedThreads = new ArrayList<>();

    private final BallFactory ballFactory = new BallFactory();

    private final ResourceBundle rb = ResourceBundle.getBundle("application");

    public BounceFrame() {
        final GameMode gm = GameMode.valueOf(rb.getString("game.mode"));
        this.setSize(WIDTH, HEIGHT);
        this.setTitle("com.kpi.multithreading.balls.Bounce program");
        this.canvas = new BallCanvas();
        System.out.println("In Frame Thread name = "
                + Thread.currentThread().getName());
        Container content = this.getContentPane();
        content.add(this.canvas, BorderLayout.CENTER);
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.lightGray);
        JButton buttonStart = new JButton("Start");
        JButton buttonStop = new JButton("Stop");
        Consumer<ActionEvent> ballProducer = e -> {

            Ball b = ballFactory.getBall(canvas);
            canvas.add(b);

            Thread thread = null;
            if (gm == GameMode.DEFAULT) {
                thread = new BallThread(b);
            } else if (gm == GameMode.WAITING) {
                thread = new WaitingBallThread(b, usedThreads.isEmpty() ? null : usedThreads.get(0));
            }
            usedThreads.add(thread);
            thread.start();
            System.out.println("Thread name = " +
                    thread.getName());
        };
        buttonStart.addActionListener(e -> {
            for (int i = 0; i < 1; i++) {
                ballProducer.accept(e);
            }
        });
        buttonStop.addActionListener(e -> {
            usedThreads.forEach(Thread::interrupt);
            System.exit(0);
        });

        buttonPanel.add(buttonStart);
        buttonPanel.add(buttonStop);

        content.add(buttonPanel, BorderLayout.SOUTH);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                usedThreads.forEach(Thread::interrupt);
            }
        });
    }
}
