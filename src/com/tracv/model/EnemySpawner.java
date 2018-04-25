package com.tracv.model;

import com.tracv.gamecomponents.Enemy;
import com.tracv.types.EnemyType;

import java.util.*;


/**
 * Spawns enemies in accordance to the json's instructions
 */
public class EnemySpawner{

    private LevelJsonParser parser;
    private Evolver evolver;
    private GameState gs;

    private Random random;

    private double msSinceLastSpawn = 0;
    private int wave;
    private int maxWave;



    private Queue<List<EnemyType>> spawnQueue;
    private Map<List<EnemyType>, Integer> spawnTimer;

    public EnemySpawner(Evolver evolver) {
        this.evolver = evolver;
        this.random = new Random();
    }

    public void loadLevel(Map<List<EnemyType>, Integer> spawnTimer, Queue<List<EnemyType>> spawnQueue){
        this.spawnQueue = spawnQueue;
        this.spawnTimer = spawnTimer;
    }

    /**
     *
     * @param timeMillis
     * @return - True if level is complete with spawning
     */
    public void update(int timeMillis){
        if(spawnQueue.isEmpty()){
            System.err.println("Calling update when spawn empty");
            return; //Done spawn
        }

        msSinceLastSpawn += timeMillis;

        boolean spawnRepeat = true;

        while(spawnRepeat) { //Can spawn multiple waves if lag... lol
            int timeToNext = spawnTimer.get(spawnQueue.peek());

            if (timeToNext <= msSinceLastSpawn / 1000) { //Convert to seconds.
                System.out.println("Spawning Wave");
                wave++;
                msSinceLastSpawn -= timeToNext*1000;
                List<EnemyType> toSpawnTypes = spawnQueue.poll();

                List<Enemy> mobs = createMobs(toSpawnTypes);
                mobs.forEach(e-> {
                            //Random delay of up to 2000 seconds.
                            int randomTimeMS = random.nextInt(2000);
                            evolver.addEnemyToQueue(e, randomTimeMS);
                        });

                spawnTimer.remove(toSpawnTypes);

                if(spawnQueue.isEmpty()){
                    spawnRepeat = false;
                }
            }else{
                spawnRepeat = false;
            }
        }

    }

    /**
     * Craete a list of enemies from a list of enemy types.
     * @param next
     * @return
     */
    private List<Enemy> createMobs(List<EnemyType> next) {
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

    public String getWave(){
        return String.valueOf(wave) + "/" + String.valueOf(maxWave);
    }

    public void reset() {
        msSinceLastSpawn = 0;
        spawnQueue = parser.getSpawnQueue();
        maxWave = spawnQueue.size();
        wave = 0;
        spawnTimer = parser.getSpawnTime();
    }

    public int getTimeToNextWave() {
        return spawnTimer.get(spawnQueue.peek()) - (int)(msSinceLastSpawn /1000.0);
    }

    public boolean isDoneSpawn() {
        return spawnQueue.isEmpty();
    }
}
