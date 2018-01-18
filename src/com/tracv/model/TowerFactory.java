package com.tracv.model;

import com.tracv.model.Tower;
import com.tracv.types.TowerType;

public class TowerFactory {
    /*
    public Tower buildBasicTower(int xPos, int yPos) {
        Tower t = new Tower(5, 1, 1, 3, 10, xPos, yPos, null); // default specs for a basic tower
        return t;       //^atkDmg, fireRate, cost, size, range, x, y
    }

    public Tower buildLaserTower(int xPos, int yPos) {
        Tower t = new Tower(1, 10, 2, 3, 10, xPos, yPos, null); // Laser tower attacks faster, lower damage
        return t;
    }
    */
    public Tower buildTower(double xPos, double yPos, TowerType tower) {
        String name = tower.getName();
        if (name.equals(TowerType.BASE_TOWER.getName())) { // names are up for change, temp for now to see what its like
            Tower t = new Tower(5, 1, 1, 30, 10, xPos, yPos, null); // default specs for a basic tower
            return t;       //^atkDmg, fireRate, cost, size, range, x, y
        } else if (name.equals("Laser Tower")) {
            Tower t = new Tower(1, 10, 2, 3, 10, xPos, yPos, null); // Laser tower attacks faster, lower damage
            return t;
        }

        System.out.println("Tower type not found " + name);
        return null;
    }
}
