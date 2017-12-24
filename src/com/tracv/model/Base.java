package com.tracv.model;

import java.awt.*;

public class Base extends GameComponent{
    private int health;

    public Base(int health, int x, int y) {
        super(x,y);
        this.health = health;
    }

    @Override
    public void draw(Graphics g) {
        
    }

    public int getHealth() {
        return health;
    }
}
