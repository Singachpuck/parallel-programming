package com.kpi.multithreading.balls;

import com.kpi.multithreading.balls.model.Ball;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

public class BallCanvas extends JPanel {
    private final ArrayList<Ball> balls = new ArrayList<>();

    public void add(Ball b){
        this.balls.add(b);
    }
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        List<Rectangle2D.Double> doubles = List.of(new Rectangle2D.Double(0, 0, 20, 20),
                new Rectangle2D.Double(0, this.getHeight() - 20, 20, 20),
                new Rectangle2D.Double(this.getWidth() - 20, 0, 20, 20),
                new Rectangle2D.Double(this.getWidth() - 20, this.getHeight() - 20, 20, 20));
        g2.setColor(Color.BLACK);
        doubles.forEach(g2::fill);

        for(int i = 0; i < balls.size(); i++){
            Ball b = balls.get(i);
            if (!b.scored) {
                b.draw(g2);
            }
        }
    }
}
