package com.tracv.model;

import java.awt.*;

public class Enemy extends MoveableComponent {
    private int distanceToEnd;
    private int health;
    private int dmg;

    public Enemy(int distanceToEnd, int health, int dmg, double speed, double x, double y, String iconPath) {
            super(speed, x, y, iconPath);
            this.distanceToEnd = distanceToEnd;
            this.health = health;
            this.dmg = dmg;
    }

    public void draw(Graphics g) {

    }

    public int getDistanceToEnd() {
        return distanceToEnd;
    }

    public int getHealth() {
        return health;
    }

    public int getDmg() {
        return dmg;
    }

    public void setDistanceToEnd(int d){
        this.distanceToEnd = d;
    }

    public void setHealth(int h) {
        this.health = h;
    }

    public void setDmg(int a) {
        this.dmg = a;
    }

    public void setSpeed(double s) {
        this.speed = s;

    }

    public boolean takeDmg(int dmg) {
        this.health -= dmg;
        return health <= 0;
    }
}
