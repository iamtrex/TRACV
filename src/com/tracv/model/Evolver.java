package com.tracv.model;

import com.tracv.directional.Geometry;
import com.tracv.gamecomponents.Enemy;
import com.tracv.gamecomponents.GameComponent;
import com.tracv.gamecomponents.Projectile;
import com.tracv.observerpattern.Observable;
import com.tracv.util.Constants;
import com.tracv.util.Logger;
import com.tracv.util.LoggerLevel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.awt.Point;

import static com.tracv.model.State.TERMINATED;

public class Evolver extends Observable {
    private EnemySpawner spawner;

    private EvolutionThread evolutionThread;

    private volatile State state;
    private volatile boolean evolving;

    private GameState gs;
    private GameMap map;


    private List<Enemy> retarget;
    private Map<Enemy, Integer> toSpawn;


    public Evolver(){
        state = TERMINATED;
        evolving = false;
        evolutionThread = new EvolutionThread();

        this.gs = new GameState();
        this.map = gs.getGameMap();


        retarget = new ArrayList<>();
        toSpawn = new HashMap<>();

    }

    public GameState getGameState(){
        return gs;
    }

    public State getState() {
        return state;
    }

    public void addEnemyToQueue(Enemy e, int randomTimeMS) {
        toSpawn.put(e, randomTimeMS);
    }

    /**
     * Updates GameState components on a delay.
     */
    private class EvolutionThread extends Thread{
        private long delay;

        @Override
        public void run(){
            Logger.getInstance().log("Evolver Thread Running", LoggerLevel.STATUS);
            while(evolving){
                long start = System.nanoTime();

                updateGameState();
                delay = Math.round((System.nanoTime() - start)/1000000.0);
                Logger.getInstance().updateDelay(delay);
                long sleepTime = Constants.REFRESH_DELAY - delay;
                if(sleepTime <=0){
                    Logger.getInstance().log("Lagging! No sleep!", LoggerLevel.WARNING);
                }else{
                    try{
                        Thread.sleep(sleepTime);
                    }catch(InterruptedException ie){
                        ie.printStackTrace(); //TODO Can prob ignore.
                    }
                }
            }
            Logger.getInstance().log("Evolver Thread Terminated", LoggerLevel.STATUS);
        }

        public void cancel(){
            evolving = false;

        }

        public void reset() {

        }

    }

    /**
     * Updates the game state
     * TODO - Can this be modified to use more threads and would that be useful? or would the thread switching actually
     * TODO -       just be more of a pain in the ass?
     */
    private void updateGameState() {
        gs.increaseTime(Constants.REFRESH_DELAY);
        notifyObservers(Constants.OBSERVER_TIME_MODIFIED);
        checkGameEnd();
        updateSpawns();
        updateEnemies();
        updateProjectiles();
        updateTowers();
        updateRetargettingEnemies();
        notifyObservers(Constants.OBSERVER_GAME_TICK);
    }

    private void updateTowers() {
        synchronized(map.getTowers()) {
            map.getTowers().forEach(t -> {
                t.decrementCooldown(Constants.REFRESH_DELAY);
                if (t.canFire()) {
                    double range = t.getRange();
                    Point towerPt = new Point((int) t.getX(), (int) t.getY());
                    synchronized(map.getEnemies()) {
                        for (Enemy e : map.getEnemies()) {
                            Point enemyPt = new Point((int) e.getX(), (int) e.getY());
                            if (Geometry.getDistance(towerPt, enemyPt) < range) {
                                map.addComponent(new Projectile(e, t, t.getProjectileType()));
                                t.setFired();
                                break;
                            }
                        }
                    }
                }
            });
        }
    }

    private void updateRetargettingEnemies() {
        synchronized(map.getTargetMap()) {
            Map<Enemy, List<Projectile>> target = map.getTargetMap();

            retarget.forEach(e -> {
                List<Projectile> projs = target.get(e);
                projs.forEach(p -> {
                    synchronized(map.getEnemies()) {
                        for (Enemy e2 : map.getEnemies()) {
                            if (e2.getX() > 0 && e2.getY() > 0) { //TODO fix for other directions too.
                                if (!retarget.contains(e2)) {
                                    if (Geometry.withinRange(e2, p.getTower(), p.getTower().getRange())) {
                                        p.setTarget(e2);
                                        map.setTarget(p, e2);
                                        break; //goto next enemy.
                                    }
                                }
                            }
                        }
                    }
                });
            });

            retarget.clear();
        }
    }


    private void updateProjectiles() {
        List<GameComponent> temp = new ArrayList<>();
        synchronized(map.getProjectiles()) {
            map.getProjectiles().forEach(p -> {
                boolean hitEnemy = ProjectileMotion.updateProjectile(p, Constants.REFRESH_DELAY);
                if (hitEnemy) {
                    Enemy e = p.getTarget();
                    boolean dead = e.takeDmg(p.getDmg());
                    if (dead) {
                        temp.add(e);
                        retarget.add(e);
                        gs.gainGold(e.getKillGold());
                    }
                    temp.add(p);

                }
            });
        }
    }

    private void updateEnemies() {
        List<Enemy> temp = new ArrayList<>();
        synchronized(map.getEnemies()) {
            map.getEnemies().forEach(e -> {
                boolean reachedBase = EnemyMotion.updateEnemy(e, Constants.REFRESH_DELAY);
                if (reachedBase) {
                    temp.add(e);
                    map.getBase().takeDmg(e.getDmg());
                    notifyObservers(Constants.OBSERVER_BASE_HEALTH_CHANGED);
                }
            });
        }

        temp.forEach(e-> map.removeComponent(e));
    }

    private void updateSpawns() {
        spawner.update(Constants.REFRESH_DELAY);
        toSpawn.forEach((k, v)->{
            v--;
            if(v <=0){
                map.addComponent(k);
                toSpawn.remove(k);
            }
        });
    }


    /**
     * Checks if game won or lost
     */
    private void checkGameEnd(){
        if(map.getBase().isExploded()){
            changeState(TERMINATED);
            gs.levelFailed();
        }

        if(!spawner.isDoneSpawn()){
            spawner.update(Constants.REFRESH_DELAY);
        } else {
            if(map.getEnemies().isEmpty() && toSpawn.isEmpty()){
                changeState(TERMINATED);
                gs.levelCompleted();

            }
        }
    }


    /**
     * Changes Game's State Status.
     * @param s - Enum State, can either be set to PLAYING, PAUSED, OR TERMINATED.
     */
    public void changeState(State s){
        if(s == state){
            Logger.getInstance().log("Trying to make same state", LoggerLevel.WARNING);
            return;
        }

        if(s == State.PLAYING){
            if(state == State.PAUSED){//Resume
                evolutionThread.notify();
            }else if(state == TERMINATED){ //Restart
                evolving = true;
                evolutionThread.start();
            }

        }else if(s == State.PAUSED){
            try {
                evolutionThread.wait();
            }catch(InterruptedException ie){
                ie.printStackTrace();
            }

        }else if(s == TERMINATED){
            evolutionThread.cancel();
            evolving = false;
            evolutionThread.reset();

        }
    }

}
