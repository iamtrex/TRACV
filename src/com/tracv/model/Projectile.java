package com.tracv.model;

import java.awt.*;
import java.lang.annotation.Target;

public class Projectile extends MoveableComponent {
    private Enemy target;
    private int dmg;
    private double speed;

    public Projectile (Enemy target, int dmg, double speed, double x, double y) {
        super (speed, x, y);
        this.target = target;
        this.dmg = dmg;
        this.speed = speed;
    }

    public void draw(Graphics g) {

    }

    public Enemy getTarget() {return target;}

    public int getDmg() {return dmg;}

    public double getSpeed() {return speed;}

    public void setTarget(Enemy t) {this.target = t;}

    public void setDmg(int d) {this.dmg = d;}

    public void setSpeed(double s) {this.speed = s;}

}
