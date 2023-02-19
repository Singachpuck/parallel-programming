package com.kpi.multithreading.balls.model;

import java.awt.*;
import java.util.Random;
import java.util.ResourceBundle;

public class BallFactory {

    private final ResourceBundle rb = ResourceBundle.getBundle("application");

    private boolean red = true;

    private final SpawnMode spawnMode;

    private final ColorMode colorMode;

    {
        spawnMode = SpawnMode.valueOf(rb.getString("spawn.mode"));
        colorMode = ColorMode.valueOf(rb.getString("color.mode"));
    }

    private final Random random = new Random();

    public Ball getBall(Component c) {
        int x = 0, y = 0, dx = 0, dy = 0, priority = Thread.NORM_PRIORITY;
        Color color = null;
//        if(Math.random()<0.5){
//            x = new Random().nextInt(this.canvas.getWidth());
//            y = 0;
//        }else{
//            x = 0;
//            y = new Random().nextInt(this.canvas.getHeight());
//        }
        if (spawnMode == SpawnMode.RANDOM) {
            if(Math.random()<0.5){
                x = random.nextInt(c.getWidth());
            }else{
                y = random.nextInt(c.getHeight());
            }
            dx = 2;
            dy = 2;
        } else if (spawnMode == SpawnMode.FIXED) {
            x = Integer.parseInt(rb.getString("spawn.x"));
            y = Integer.parseInt(rb.getString("spawn.y"));
            dx = Integer.parseInt(rb.getString("spawn.dx"));
            dy = Integer.parseInt(rb.getString("spawn.dy"));
        }

        if (colorMode == ColorMode.RED_BLUE) {
            if (red) {
                color = Color.RED;
                priority = Thread.MAX_PRIORITY;
                red = false;
            } else {
                color = Color.BLUE;
                priority = Thread.MIN_PRIORITY;
            }
        } else if (colorMode == ColorMode.GREY) {
            color = Color.lightGray;
        } else if (colorMode == ColorMode.RANDOM) {
            color = new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));
        }

        return new Ball(c, x, y, dx, dy, color, priority);
    }
}
