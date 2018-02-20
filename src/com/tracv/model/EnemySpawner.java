package com.tracv.model;

import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * Spawns enemies in accordance to the json's instructions
 */
public class EnemySpawner {

    public EnemySpawner(){

    }


    private Queue<List<Enemy>> toSpawn;
    private Map<List<Enemy>, Integer> timeToSpawn;


    public void loadLevel(){

    }

}
