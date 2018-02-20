package com.tracv.model;

import java.util.LinkedList;
import java.util.Queue;


public class EnemyFactory {
    private Queue<Enemy> toSpawn;
    String map; // temp, depending on what map it is it will create a different queue
    //might take in a special map class to determine?

    public EnemyFactory() {
        toSpawn = new LinkedList<Enemy>();
        for (int i = 0; i < 100; i++) {
            toSpawn.add(new Enemy(0, 30, 10, 3, 1920, 540, null, 10));
        }                                   //^change this
        for (int i = 0; i < 100; i++) {
            toSpawn.add(new Enemy(0, 5, 10, 3, 1920, 540, null, 25));
        }                                   //^change this distance
    }

    //for special uses only
    public void addEnemy(Enemy e) {
        toSpawn.add(e);
    }

    //spawns one enemy
    public Enemy spawn() {
        return toSpawn.poll();
    }
}
