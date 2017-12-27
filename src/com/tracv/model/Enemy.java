package com.tracv.model;

import java.awt.*;

public class Enemy extends MoveableComponent {
    private int distanceToEnd;
    private int health;
    private int dmg;
    private double speed;

    public Enemy(int distanceToEnd, int health, int dmg, double speed, double velocity, double x, double y) {
        super(velocity, x, y);
        this.distanceToEnd = distanceToEnd;
        this.health = health;
        this.dmg = dmg;
        this.speed = speed;
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
}
