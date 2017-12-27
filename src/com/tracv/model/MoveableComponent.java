package com.tracv.model;

import java.awt.*;

public abstract class MoveableComponent extends GameComponent {
    protected double velocity;

    public MoveableComponent (double velocity, int x, int y) {
        super(x, y);
        this.velocity = velocity;
    }
    abstract void draw(Graphics g);

    public double getVelocity() {
        return velocity;
    }
}
