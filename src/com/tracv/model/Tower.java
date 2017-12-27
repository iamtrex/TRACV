package com.tracv.model;

import java.awt.*;
import java.awt.geom.Ellipse2D;

public class Tower extends GameComponent {
    private int atkDmg;
    private double fireRate;
    private int cost;
    private int size;
    private int range;

    public Tower(int atkDmg, double fireRate, int cost, int size, int range, double x, double y) {
        super(x,y);
        this.atkDmg = atkDmg;
        this.fireRate = fireRate;
        this.cost = cost;
        this.size = size;
        this.range = range;
    }

    public void draw(Graphics g) {
        g.setColor(Color.RED);
        Graphics2D g2d = (Graphics2D)g;
        double centerX = x - (1/2);
        double centerY = y - (1/2);
        Ellipse2D.Double circle = new Ellipse2D.Double(centerX, centerY, 1, 1);
        g2d.fill(circle);
    }

    public int getAtkDmg() {
        return atkDmg;
    }

    public double getFireRate() {
        return fireRate;
    }

    public int getCost() {
        return cost;
    }

    public int getSize() {
        return size;
    }

    public int getRange() {
        return range;
    }


}
