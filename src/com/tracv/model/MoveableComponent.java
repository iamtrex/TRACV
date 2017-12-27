package com.tracv.model;

import java.awt.*;

public abstract class MoveableComponent extends GameComponent {
    protected double velocity;

    public MoveableComponent (double velocity, double x, double y) {
        super(x, y);
        this.velocity = velocity;
    }
    abstract void draw(Graphics g);

    public double getVelocity() {
        return velocity;
    }
}
