package com.tracv.model;

import com.tracv.directional.Vector;

import java.util.List;

/**
 * Handles Projectile Motion calculation including "heatseeking" of missles and enemy redesignation
 *
 */
public class ProjectileMotion {

    //TODO -
    //Currently undecided on whether or not ProjMotion needs GameMap
    public ProjectileMotion(/*GameMap map*/){

    }

    /**
     * Updates the projectile's position.
     * @param p - Projectile
     */
    public void updateProjectile(Projectile p){
        Enemy e = p.getTarget();
        //TODO eval if enemy is within range of movement. If so, set projectile to enemy and autocollide.

        double x2 = e.getX();
        double y2 = e.getY();

        double x1 = p.getX();
        double y1 = p.getY();

        Vector v = new Vector(x2-x1, y2-y1);
        double magnitude = p.getSpeed();

        //Evaluate the dX and dY based off of vector values and magnitude.
        double dx = magnitude * v.getXRatio();
        double dy = magnitude * v.getYRatio();

        /* //TODO - Waiting on implementation in Moveable component: addX(double) and addY(double).
        p.addX(dx);
        p.addY(dy);
        */

    }

    /**
     * Establishes a new enemy if the current enemy is no longer existant.
     * @param enemies
     * @param p
     */
    public void setNewTarget(List<Enemy> enemies, Projectile p){

    }







}
