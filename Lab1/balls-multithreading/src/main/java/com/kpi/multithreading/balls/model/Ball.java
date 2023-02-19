package com.kpi.multithreading.balls.model;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.List;

public class Ball {

    private final java.util.List<Rectangle2D.Double> rectangles;
    private final Component canvas;
    private static final int XSIZE = 20;
    private static final int YSIZE = 20;
    private final int priority;
    private int x;
    private int y;
    private int dx;
    private int dy;

    private final Color color;

    public boolean scored = false;


    public Ball(Component c, int x, int y, int dx, int dy, Color color, int priority){
        this.canvas = c;
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
        this.color = color;
        this.priority = priority;
        this.rectangles = List.of(new Rectangle2D.Double(0, 0, 20, 20),
                new Rectangle2D.Double(0, canvas.getHeight() - 20, 20, 20),
                new Rectangle2D.Double(canvas.getWidth() - 20, 0, 20, 20),
                new Rectangle2D.Double(canvas.getWidth() - 20, canvas.getHeight() - 20, 20, 20));
    }

    public void draw (Graphics2D g2){
        g2.setColor(color);
        g2.fill(new Ellipse2D.Double(x,y,XSIZE,YSIZE));
    }

    public void move(){
        this.checkScored();
        if (scored) {
            return;
        }
        x+=dx;
        y+=dy;
        if(x<0){
            x = 0;
            dx = -dx;
        }
        if(x+XSIZE>=this.canvas.getWidth()){
            x = this.canvas.getWidth()-XSIZE;
            dx = -dx;
        }
        if(y<0){
            y=0;
            dy = -dy;
        }
        if(y+YSIZE>=this.canvas.getHeight()){
            y = this.canvas.getHeight()-YSIZE;
            dy = -dy;
        }
        this.canvas.repaint();
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public int getWidth() {
        return XSIZE;
    }

    public int getHeight() {
        return YSIZE;
    }

    public int getPriority() {
        return priority;
    }

    public void checkScored() {
        for (Rectangle2D.Double d : rectangles) {
            if (d.intersects(this.getX(), this.getY(), this.getWidth(), this.getHeight())) {
                scored = true;
                return;
            }
        }
        scored = false;
    }
}
