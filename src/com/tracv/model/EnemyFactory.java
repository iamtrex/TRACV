package com.tracv.model;

import com.tracv.model.Enemy;

import java.util.LinkedList;
import java.util.Queue;


public class EnemyFactory {
    private Queue<Enemy> toSpawn;
    String map; // temp, depending on what map it is it will create a different queue
    //might take in a special map class to determine?

    public EnemyFactory() {
        toSpawn = new LinkedList<Enemy>();
        for (int i = 0; i < 10; i++) {
            toSpawn.add(new Enemy(0, 5, 1, 5, 1920, 540, null));
        }                                   //^change this
        for (int i = 0; i < 10; i++) {
            toSpawn.add(new Enemy(0, 5, 1, 5, 1920, 540, null));
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
