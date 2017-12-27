package com.tracv.model;

import java.awt.*;

public abstract class GameComponent {
    protected double x;
    protected double y;

    public GameComponent(double x, double y) {
        this.x = x;
        this.y = y;
    }

    abstract void draw(Graphics g);

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }
}
