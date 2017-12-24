package com.tracv.model;

import java.awt.*;

public class Tower extends GameComponent {
    private int atkDmg;
    private double fireRate;
    private int cost;
    private int size;
    private int range;

    public Tower(int atkDmg, double fireRate, int cost, int size, int range, int x, int y) {
        super(x,y);
        this.atkDmg = atkDmg;
        this.fireRate = fireRate;
        this.cost = cost;
        this.size = size;
        this.range = range;
    }

    @Override
    public void draw(Graphics g) {

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
