package com.tracv.model;

import com.tracv.types.EnemyType;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * Spawns enemies in accordance to the json's instructions
 */
public class EnemySpawner {

    private LevelJsonParser parser;
    private GameMap map;

    private double millisFromLastSpawn = 0;

    public EnemySpawner(LevelJsonParser parser, GameMap map){
        this.parser = parser;
        this.map = map;

    }


    private Queue<List<EnemyType>> toSpawn;
    private Map<List<EnemyType>, Integer> timeToSpawn;

    private List<EnemyType> next;

    /**
     *
     * @param timeMillis
     * @return - True if level is complete with spawning
     */
    public boolean update(int timeMillis){
        millisFromLastSpawn += timeMillis;
        int timeToNext = timeToSpawn.get(next);

        boolean spawnRepeat = true;

        while(spawnRepeat) { //Can spawn multiple waves if lag... lol
            if (timeToNext <= millisFromLastSpawn / 1000.0) { //Convert to seconds.
                millisFromLastSpawn -= timeToNext;
                map.addEnemies(spawn(next)); // Saves some time with Casting of all the comps to enemies...?

                timeToSpawn.remove(next);
                if(toSpawn.isEmpty()){
                    return true;
                }
                next = toSpawn.poll(); //get ready next wave!
            }else{
                spawnRepeat = false;
            }
        }

        return false;
    }

    private List<Enemy> spawn(List<EnemyType> next) {
        List<Enemy> enemies = new LinkedList<>();

        for(EnemyType type : next){
            enemies.add(new Enemy(type, 0, 0));

        }
        return enemies;
    }

    public void reset() {

        millisFromLastSpawn = 0;
        toSpawn = parser.getSpawnQueue();
        timeToSpawn = parser.getSpawnTime();
        next = toSpawn.poll();
    }
}
