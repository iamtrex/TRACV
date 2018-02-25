package com.tracv.model;

import com.tracv.gamecomponents.Enemy;
import com.tracv.types.EnemyType;

import java.util.*;


/**
 * Spawns enemies in accordance to the json's instructions
 */
public class EnemySpawner{

    private LevelJsonParser parser;
    private GameMap map;
    private GameState gs;


    private double millisFromLastSpawn = 0;

    private int wave;
    private int maxWave;
    public EnemySpawner(LevelJsonParser parser, GameState gs){
        this.parser = parser;
        this.map = gs.getMap();
        this.gs = gs;

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

        boolean spawnRepeat = true;

        while(spawnRepeat) { //Can spawn multiple waves if lag... lol

            int timeToNext = timeToSpawn.get(next);
            if (timeToNext <= millisFromLastSpawn / 1000.0) { //Convert to seconds.
                System.out.println("Spawning Wave");
                wave++;
                updateGSWave();

                millisFromLastSpawn -= timeToNext*1000;
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


    public String getWave(){
        return String.valueOf(wave) + "/" + String.valueOf(maxWave);
    }
    //TODO - I dont' like this implementation, but can't think of a better way.
    public void updateGSWave(){
        gs.updateWave();
    }

    private List<Enemy> spawn(List<EnemyType> next) {
        List<Enemy> enemies = new LinkedList<>();
        Random random = new Random();
        int x = 0, y = 0;

        for(EnemyType type : next){
            enemies.add(new Enemy(type, x, y));
            x -= random.nextInt(10)*10;
            y = random.nextInt(10) * 5;

        }
        return enemies;
    }

    public void reset() {

        millisFromLastSpawn = 0;
        toSpawn = parser.getSpawnQueue();
        wave = 0;
        maxWave = toSpawn.size();
        timeToSpawn = parser.getSpawnTime();
        next = toSpawn.poll();

    }

    public int getTimeToNextWave() {
        return timeToSpawn.get(next) - (int)(millisFromLastSpawn/1000.0);
    }
}
