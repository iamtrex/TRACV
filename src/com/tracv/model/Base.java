package com.tracv.model;

import java.awt.*;

public class Base extends GameComponent{
    private int health;

    public Base(int health, double x, double y, String iconPath) {
        super(x, y, iconPath);
        this.health = health;
    }

    public void draw(Graphics g) {

    }

    public int getHealth() {
        return health;
    }
}
