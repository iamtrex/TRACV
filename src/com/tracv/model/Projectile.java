package com.tracv.model;

import java.awt.*;

public class Projectile extends MoveableComponent {
    private Enemy target;
    private int dmg;
    private double speed;

    public Projectile (Enemy target, int dmg, double speed, double x, double y, String iconPath) {
        super (speed, x, y, iconPath);
        this.target = target;
        this.dmg = dmg;
        this.speed = speed;
    }

    public void draw(Graphics g) {
        g.setColor(Color.ORANGE);
        g.fillOval((int) x, (int) y, 10, 10);
    }

    public Enemy getTarget() {return target;}

    public int getDmg() {return dmg;}

    public void setTarget(Enemy t) {this.target = t;}

    public void setDmg(int d) {this.dmg = d;}

    public void setSpeed(double s) {this.speed = s;}
}
