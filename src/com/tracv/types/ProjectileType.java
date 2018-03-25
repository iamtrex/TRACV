package com.tracv.types;

/**
 * Types of Projectiles
 */
public enum ProjectileType {
    MAGIC(5),
    BASIC(5);


    ProjectileType(double speed){
        this.speed = speed;
    }
    private double speed;

    public double getSpeed() {
        return speed;
    }
}
