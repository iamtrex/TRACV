package com.tracv.model;

import java.awt.*;

public abstract class GameComponent {
    protected int x;
    protected int y;

    public GameComponent(int x, int y) {
        this.x = x;
        this.y = y;
    }
    abstract void draw(Graphics g);

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}
