package com.tracv.model;

import com.tracv.directional.Geometry;
import com.tracv.gamecomponents.*;
import com.tracv.observerpattern.Observable;
import com.tracv.types.TerrainType;
import com.tracv.types.TowerType;
import com.tracv.util.Constants;
import com.tracv.util.Logger;
import com.tracv.util.LoggerLevel;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class GameState extends Observable implements Iterable<GameComponent> {


    //private List<GameComponent> gameComponents;
    private GameMap map;

    private LevelJsonParser parser;
    private EnemySpawner spawner;

    private GameThread gameThread;

    private Tower selectedTower; //Selected by user.

    private int gold;
    private int score;
    private int timeElapsed;
    private int level;

    //private boolean doneSpawn; //Keeps track of if there are still anymore enemy waves left to spawn (used to check if level complete)

    //Keeps track of game running state/refresh
    private long lastTimeNano;

    private boolean running;

    public GameState() {
        running = false;
        parser = new LevelJsonParser();
        map = new GameMap();
        spawner = new EnemySpawner(parser, this);
    }

    /**
     * Initiates a new game.
     */
    public void loadNewGame(int level) {
        Logger.getInstance().log("Loading Level " + level, LoggerLevel.STATUS);
        running = false;

        if (gameThread != null) {
            Logger.getInstance().log("Game Thread not properly terminated", LoggerLevel.WARNING);
            try {
                gameThread.join();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                gameThread = null; //reset the timer (tbh won't really change much since refresh rate is so fast, but why not.. :P)
            }
        }

        //Reset values.
        gold = 500; // temp value, 500 cuz league
        score = 0;
        timeElapsed = 0;
        selectedTower = null;

        //Load level based on inputted string.
        this.level = level;
        parser.readLevel(level);
        spawner.reset();
        map.reset();
        map.loadLevel(parser.getFile());

        //Reset other components such as selected rectangle in GamePane
        notifyObservers(Constants.OBSERVER_NEW_GAME);
    }


    /**
     * Start or stop game running
     *
     * @param b - true = start, false = pause.
     */
    public void setGameRunning(boolean b) {
        System.out.println("Setting game runnning " + b);

        if (gameThread == null) {
            gameThread = new GameThread();
        }

        //if (running != b) {
            running = b;
            if (b) {
                lastTimeNano = System.nanoTime();
                gameThread.start();
                Logger.getInstance().log("Starting/Resuming Game!", LoggerLevel.STATUS);
                notifyObservers(Constants.OBSERVER_GAME_RESUMED);
            } else {
                /*
                try{
                    gameThread.join();
                }catch(Exception e){
                    e.printStackTrace();
                }finally{
                    gameThread = null;
                }*/

                gameThread = null;
                Logger.getInstance().log("Stopping/Pausing Game!", LoggerLevel.STATUS);
                notifyObservers(Constants.OBSERVER_GAME_PAUSED);
            }
        //} else {
        //    Logger.getInstance().log("GameState was messed up, called running = " + b + " when running already " + running, LoggerLevel.WARNING);
        //}
    }

    public boolean isRunning() {
        return running;
    }

    public void restartLevel() {
        Logger.getInstance().log("Restarting Level", LoggerLevel.STATUS);
        loadNewGame(this.level);
        //setGameRunning(true);
    }

    private class GameThread extends Thread {
        public void run() {
            while (running) {
                long nowTime = System.nanoTime();
                long delay = Math.round((nowTime - lastTimeNano) / 1000000.0);
                lastTimeNano = nowTime;

                Logger.getInstance().updateDelay(delay);
                updateState(delay);
                notifyObservers(Constants.OBSERVER_GAME_TICK); //Call UI to redraw

                long loadDelay = Math.round((System.nanoTime() - nowTime) / 1000000.0); //Include time for UI to redraw.
                Logger.getInstance().updateLoadDelay(loadDelay);


                //Want sleep to be approx REFRESH_DELAY...
                long sleepTime = Constants.REFRESH_DELAY - loadDelay;
                if (sleepTime <= 0) {
                    Logger.getInstance().log("No Sleep!", LoggerLevel.WARNING);
                }
                if (sleepTime > 0) {
                    try {
                        Thread.sleep(sleepTime);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            Logger.getInstance().log("Terminating Game Thread", LoggerLevel.STATUS);
        }
    }


    private void updateEnemies(List<GameComponent> toDel, List<Enemy> needToRetarget, long updateTime) {
        for (Enemy e : map.getEnemies()) {
            if (toDel.contains(e)) {
                continue; // Skip.
            }
            boolean reachedBase = EnemyMotion.updateEnemy(e, updateTime);

            //Delete e if it reaches base
            if (reachedBase) {
                Logger.getInstance().log("Enemy Crashed! Dealing " + e.getDmg() + " damage!", LoggerLevel.STATUS);
                map.getBase().takeDmg(e.getDmg());

                if (map.getBase().isExploded()) {
                    //Base exploded.
                    setLevelFailure();
                }

                notifyObservers(Constants.OBSERVER_BASE_HEALTH_CHANGED);
                needToRetarget.add(e);
                toDel.add(e);
            }
        }
    }

    private void updateTowers(List<GameComponent> toDel, List<GameComponent> toAdd) {
        for (Tower t : map.getTowers()) {
            t.decrementCooldown(1000.0 / Constants.REFRESH_RATE);
            boolean fire = t.canFire();
            if (fire) {
                //Search enemies in range.
                double range = t.getRange();
                Point towerPt = new Point((int) t.getX(), (int) t.getY());

                for (Enemy e : map.getEnemies()) {
                    if (toDel.contains(e)) {
                        continue;
                    }
                    Point enemyPt = new Point((int) e.getX(), (int) e.getY());
                    if (Geometry.getDistance(towerPt, enemyPt) < range) {
                        toAdd.add(new Projectile(e, t, t.getProjectileType()));
                        t.setFired();
                        break;
                    }
                }
            }
        }
    }

    private void updateProjectiles(List<GameComponent> toDel, List<Enemy> needToRetarget) {
        for (Projectile p : map.getProjectiles()) {
            if (toDel.contains(p)) {
                continue; // Skip.
            }
            boolean crashed = ProjectileMotion.updateProjectile(p);
            if (crashed) {
                Enemy e = p.getTarget();
                boolean dead = e.takeDmg(p.getDmg());
                toDel.add(p);
                if (dead) {
                    toDel.add(e);
                    needToRetarget.add(e);
                    gainGold(e.getKillGold());
                }
            }
        }
    }

    private void updateRetargetEnemies(List<Enemy> needToRetarget, List<GameComponent> toDel) {
        for (Enemy e : needToRetarget) {
            for (Projectile p : map.getProjectiles()) {
                if (p.getTarget().equals(e)) {
                    System.out.println("Retargetting for dead enemy");
                    if (map.getEnemies().size() > 0) {
                        for (Enemy e2 : map.getEnemies()) {
                            if (e2.getX() > 0 && e2.getY() > 0) { //TODO fix for other directions too.
                                if (!needToRetarget.contains(e2)) {
                                    if (Geometry.withinRange(e2, p.getTower(), p.getTower().getRange())) {
                                        p.setTarget(e2);
                                        break;
                                    }
                                }
                            }
                        }
                    } else {
                        toDel.add(p);
                    }
                }
            }
        }
    }

    /**
     * Updates the position of everything
     * //TODO make system update to actualTimeMS
     */
    public void updateState(long updateTimeMS) {
        //System.out.println("Update Rate " + updateTimeMS);

        timeElapsed += updateTimeMS;

        List<Enemy> needToRetarget = new ArrayList<>();
        List<GameComponent> toDel = map.getToDel();
        List<GameComponent> toAdd = map.getToAdd();

        for (GameComponent gc : toAdd) {
            map.addComponent(gc);
        }
        toAdd.clear();

        updateEnemies(toDel, needToRetarget, updateTimeMS);
        updateProjectiles(toDel, needToRetarget);
        updateTowers(toDel, toAdd);

        for (GameComponent gc : toAdd) {
            map.addComponent(gc);
        }

        updateRetargetEnemies(needToRetarget, toDel);

        for (GameComponent gc : toDel) {
            map.removeComponent(gc);
        }

        toDel.clear();
        toAdd.clear();

        //Update spawner time
        if (!spawner.isDoneSpawn()) {
            spawner.update((int) updateTimeMS);
        } else {
            if (map.getEnemies().isEmpty() && !map.getBase().isExploded()) {
                //Beat level!
                setLevelSuccess();
            }
        }

        notifyObservers(Constants.OBSERVER_TIME_MODIFIED);
    }

    private void setLevelSuccess() {
        Logger.getInstance().log("Game Win!", LoggerLevel.STATUS);
        setGameRunning(false);
        notifyObservers(Constants.OBSERVER_LEVEL_COMPLETE);


    }

    private void setLevelFailure() {
        Logger.getInstance().log("Game Lose!", LoggerLevel.STATUS);
        notifyObservers(Constants.OBSERVER_GAME_OVER);
        setGameRunning(false);

    }

    public boolean isTowerBuildValid(Point p, TowerType selectedTower) {
        //Draw TerrainType
        //TerrainType[][] terrainType = map.getTerrainTypes();
        try {
            Terrain[][] terrains = map.getTerrains();

            //int blockSizeX = (int) (Constants.GAME_DIMENSION.getWidth() / terrains[0].length);
            //int blockSizeY = (int) (Constants.GAME_DIMENSION.getHeight() / terrains.length);
            int blockSizeX = (int) Math.round(Constants.DEFAULT_BLOCK_SIZE);
            int blockSizeY = (int) Math.round(Constants.DEFAULT_BLOCK_SIZE);


            TerrainType ter = terrains[(p.y - selectedTower.getHeight() / 2) / blockSizeY]
                    [(p.x - selectedTower.getWidth() / 2) / blockSizeX]
                    .getType(); //Block the top left corner belongs in.


            TerrainType ter2 = terrains[(p.y + selectedTower.getHeight() / 2) / blockSizeY]
                    [(p.x + selectedTower.getWidth() / 2) / blockSizeX]
                    .getType(); //Block the bottom right corner belongs in.


            if ((ter != TerrainType.BUILDABLE) || (ter2 != TerrainType.BUILDABLE)) {
                return false;
            }

            for (Tower t : map.getTowers()) {
                if (Math.abs(p.getX() - t.getX()) <= t.getWidth() &&
                        Math.abs((p.getY() - t.getY())) <= t.getHeight()) {
                    return false;
                }
            }

            return true;
        } catch (ArrayIndexOutOfBoundsException e) {
            //TODO decide if it's worth fixing this...
            //Silently ignore this bug... for now
            return false;
        }
    }


    public void attemptToSelectTower(Point point) {
        boolean notify = false;

        if (selectedTower != null) {
            selectedTower.setSelected(false);
            selectedTower = null;
            notify = true;
        }

        for (Tower t : map.getTowers()) {
            if (Geometry.isPointInObject(point, t)) {
                t.setSelected(true);
                selectedTower = t;
                notify = true;
                break;
            }
        }

        if (notify)
            notifyObservers(Constants.OBSERVER_TOWER_SELECTED);

    }

    /**
     * Attempt to build tower at selected point and tower.
     *
     * @param point         - The point to build the tower at
     * @param selectedTower - The type of tower to build
     */
    public boolean attemptToBuildTower(Point point, TowerType selectedTower) {
        int cost = selectedTower.getCost();

        double x = point.getX();
        double y = point.getY();

        if (!isTowerBuildValid(point, selectedTower) || gold < cost) {
            return false;
            //checks for whether the terrain is buildable
            //checks for whether theres already a terrain there
            //checks for whether theres enough gold
        }
        //GameComponent construct = construction.buildTower(x-selectedTower.getWidth()/2, y-selectedTower.getHeight()/2, selectedTower);

        Tower tower = new Tower(x - selectedTower.getWidth() / 2, y - selectedTower.getHeight() / 2, selectedTower);

        if (map.addComponent(tower)) {
            useGold(cost);
            return true;
        }
        return false;
    }


    @Override
    public Iterator<GameComponent> iterator() {
        return map.getGameComponents().iterator();
    }


    //boring getters and setters
    public void gainGold(int i) {
        gold = gold + i;
        notifyObservers(Constants.OBSERVER_GOLD_CHANGED);
    }

    public void useGold(int i) {
        gold = gold - i;
        if (gold < 0) gold = 0; //prevent negative gold, if for somereason it happens
        notifyObservers(Constants.OBSERVER_GOLD_CHANGED);
    }

    public int getGold() {
        return gold;
    }

    public void gainScore(int i) {
        score = score + i;
    }

    public int getScore() {
        return score;
    }

    public void resetScore() {
        score = 0;
    }

    public Terrain[][] getTerrain() {
        return map.getTerrains();
    }

    public GameMap getMap() {
        return map;
    }


    public int getLevel() {
        return level;
    }

    public int getTime() {
        return timeElapsed;
    }

    public String getWave() {
        return spawner.getWave();
    }

    public int getTimeToNextWave() {
        return spawner.getTimeToNextWave();
    }

    public void updateWave() {
        notifyObservers(Constants.OBSERVER_WAVE_SPAWNED);
    }

    public Tower getSelectedTower() {
        return selectedTower;
    }

    public void attemptUpgradeTower(Tower selectedTower, TowerType upgradeType) {
        if (gold >= upgradeType.getUpgradeCost()) {
            useGold(upgradeType.getUpgradeCost());
            selectedTower.modifyType(upgradeType);
            notifyObservers(Constants.OBSERVER_UPGRADED_TOWER);
        }

    }

    public void attemptSellTower(Tower t) {
        //map.removeComponent(selectedTower);
        if (t == selectedTower) {
            selectedTower = null;
        }
        map.getToDel().add(t);
        gainGold((int) t.getSellPrice());
        notifyObservers(Constants.OBSERVER_TOWER_SELECTED);
    }

    public String getBaseHealth() {
        return map.getBase().getHealth();
    }

    public boolean isDoneSpawn() {
        return spawner.isDoneSpawn();
    }
}
