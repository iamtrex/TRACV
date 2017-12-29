package com.tracv.model;

import com.tracv.model.Tower;

public class TowerFactory {
    public Tower buildBasicTower(int xPos, int yPos) {
        Tower t = new Tower(5, 1, 1, 3, 10, xPos, yPos, null); // default specs for a basic tower
        return t;       //^atkDmg, fireRate, cost, size, range, x, y
    }

    public Tower buildLaserTower(int xPos, int yPos) {
        Tower t = new Tower(1, 10, 2, 3, 10, xPos, yPos, null); // Laser tower attacks faster, lower damage
        return t;
    }
}
