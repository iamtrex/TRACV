package com.tracv.model;

import java.awt.*;
import java.awt.geom.Ellipse2D;

/**
 * Represents a Tower on the GameMap
 * No fields should be null, atkDmg, fireRate, cost, size and range should be greater than 0
 */
public class Tower extends GameComponent {
    private int atkDmg;
    private double fireRate;
    private int cost;
    private int size;
    private int range;

    private double timeTilNextFire; //In milliseconds.

    /**
     * Constructor for a Tower
     * @param atkDmg the attack damage value of the tower
     * @param fireRate the fire rate of the tower
     * @param cost the amount the tower costs to construct
     * @param size the width and height of the tower, the tower is a square (width = height)
     * @param range the attack range of the tower
     * @param x the x coordinate of the tower's position
     * @param y the y coordinate of the tower's position
     * @param iconPath directory of the tower's icon
     */
    public Tower(int atkDmg, double fireRate, int cost, int size, int range, double x, double y, String iconPath) {
        super(x, y, iconPath);
        this.atkDmg = atkDmg;
        this.fireRate = fireRate;
        this.cost = cost;
        this.size = size;
        this.range = range;

        this.timeTilNextFire = 1000.0/fireRate;
    }

    /**
     * CURRENTLY DRAWS A CENTERED RED CIRCLE WITH SIZE EQUALS TO size
     * @param g Swing Graphics
     */
    public void draw(Graphics g) {
        g.setColor(Color.RED);
        Graphics2D g2d = (Graphics2D)g;
        double centerX = x - (size/2);
        double centerY = y - (size/2);
        Ellipse2D.Double circle = new Ellipse2D.Double(centerX, centerY, size, size);
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


    public boolean canFire(){
        return timeTilNextFire <= 0;

    }
    public void decrementCooldown(double v) {
        if(timeTilNextFire > 0) {
            this.timeTilNextFire -= v;
        }
    }

    public void setFired() {
        timeTilNextFire = 1000.0/fireRate;
    }
}
