package com.tracv.model;

import com.tracv.types.TowerType;

import java.awt.*;

/**
 * Represents a Tower on the GameMap
 * No fields should be null, atkDmg, fireRate, cost, size and range should be greater than 0
 */
public class Tower extends GameComponent {
    private TowerType type;



    private double timeTilNextFire;
    private double fireRate;

    /**
     *
     * @param x - Center x position of tower
     * @param y - Center y position of tower
     * @param type - Type of tower, contains properties such as range, dmg, fire rate, width/height, and image.
     *
     */
    public Tower(double x, double y, TowerType type){
        super(x, y, null);
        this.type = type;

        timeTilNextFire = type.getFireRate();
        fireRate = type.getFireRate();

    }

    /**
     * @param g Swing Graphics
     */
    public void draw(Graphics g) {
        int xPos = (int) (x - getWidth()/2);
        int yPos = (int) (y - getHeight()/2);
        g.drawImage(type.getSprite(), xPos, yPos, null);


    }

    public double getAtkDmg() {
        return type.getDmg();
    }

    public double getWidth(){
        return type.getWidth();
    }
    public double getHeight(){
        return type.getHeight();
    }
    public double getRange() {
        return type.getRange();
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
