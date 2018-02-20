package com.tracv.model;

import com.tracv.directional.PointToPointDistance;
import com.tracv.observerpattern.Observable;
import com.tracv.types.TerrainType;
import com.tracv.types.TowerType;
import com.tracv.util.Constants;
import com.tracv.util.TerrainParser;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class GameState extends Observable implements Iterable<GameComponent>{


    //private List<GameComponent> gameComponents;
    private GameMap map;
    private TowerFactory construction;

    private LevelJsonParser parser;
    private EnemySpawner spawner;


    private Timer gameTimer;


    private int gold;
    private int score;
    private int timeElapsed;
    private int level;

    private boolean doneSpawn; //Keeps track of if there are still anymore enemy waves left to spawn (used to check if level complete)

    //Keeps track of game running state/refresh
    private long lastTimeNano;
    private boolean running = false;

    public GameState() {
        parser = new LevelJsonParser();
        construction = new TowerFactory();
        map = new GameMap();
        spawner = new EnemySpawner(parser, this);

    }

    /**
     * Initiates a new game.
     * (victor) restores all the field back to basic values
     */
    public void newGame(int level) {
        gameTimer = null; //reset the timer (tbh won't really change much since refresh rate is so fast, but why not.. :P)

        System.out.println("Starting new game");
        //TODO temporarily loads a default map.. In future, can load different types of maps
        
        gold = 500; // temp value, 500 cuz league
        score = 0;
        timeElapsed = 0;

        //Load level based on inputted string.
        this.level = level;
        parser.readLevel(level);
        spawner.reset();
        map.reset();

        doneSpawn = false;
        notifyObservers(Constants.OBSERVER_NEW_GAME);
    }



    public void setGameRunning(boolean b){
        if(gameTimer == null){
            createGameTimer();
        }

        if(running != b){
            if(b){
                lastTimeNano = System.nanoTime();
                gameTimer.start();
                System.out.println("Start/resume Game");
            }else{
                gameTimer.stop();
                System.out.println("Pausing/Stopping game");
            }

            running = b;
        }
    }
    public void createGameTimer(){
        gameTimer = new Timer(Constants.REFRESH_DELAY, (e)->{
            long nowTime = System.nanoTime();
            update((int)Math.round((nowTime-lastTimeNano)/1000000.0));
            notifyObservers(Constants.OBSERVER_GAME_TICK); //Call UI to redraw
            lastTimeNano = nowTime;
        });
    }




    /**
     * Updates the position of everything
     * //TODO make system update to actualTimeMS
     */
    public void update(int actualTimeMS) {
        //timeElapsed += Constants.REFRESH_DELAY;
        timeElapsed += actualTimeMS;
        //System.out.println(actualTimeMS);

        List<GameComponent> toAdd = new ArrayList<>();
        List<GameComponent> toDel = new ArrayList<>();
        List<Enemy> needToRetarget = new ArrayList<>();

        for(Enemy e : map.getEnemies()){
            boolean reachedBase = EnemyMotion.updateEnemy(e);

            //Delete e if it reaches base
            if(reachedBase){
                //TODO update Health of base since it crashed.
                System.out.println("CRASHED!");
                if(map.getBase().takeDmg(e.getDmg())){
                    //Base exploded.
                    setLevelFailure();
                }
                toDel.add(e);
            }
        }

        for(Projectile p : map.getProjectiles()){
            boolean crashed = ProjectileMotion.updateProjectile(p);
            if(crashed){
                Enemy e = p.getTarget();
                boolean dead = e.takeDmg(p.getDmg());
                toDel.add(p);
                if(dead){
                    toDel.add(e);
                    needToRetarget.add(e);

                    gainGold(e.getKillGold());

                }
            }
        }


        for(Tower t : map.getTowers()){
            t.decrementCooldown(1000.0/Constants.REFRESH_RATE);
            boolean fire = t.canFire();
            if(fire){
                //Search enemies in range.
                double range = t.getRange();
                Point towerPt = new Point((int)t.getX(), (int)t.getY());

                for(Enemy e : map.getEnemies()){
                    if(!toDel.contains(e)) {
                        Point enemyPt = new Point((int) e.getX(), (int) e.getY());
                        if (PointToPointDistance.getDistance(towerPt, enemyPt) < range) {
                            //Create new projectile with this Enemy as targe
                            // TODO FIX TEMP LINE
                            Projectile proj = new Projectile(e, 10, 5, t.getX(), t.getY(), null);
                            toAdd.add(proj);
                            t.setFired();
                            break;
                        }
                    }

                }
            }
        }

        for(GameComponent gc : toAdd){
            map.addComponent(gc);
        }

        for(GameComponent gc : toDel){
            map.removeComponent(gc);
        }
        toDel.clear();

        for(Enemy e : needToRetarget){
            for(Projectile p : map.getProjectiles()){
                if(p.getTarget().equals(e)){
                    System.out.println("Retargetting for dead enemy");
                    if(map.getEnemies().size() > 0) {
                        p.setTarget(map.getEnemies().get(0)); //Retarget first enemy.
                    }else{
                        toDel.add(p);
                    }
                }
            }
        }

        //Delete the projectiles since they no longer have targets.
        for(GameComponent gc : toDel){
            map.removeComponent(gc);
        }

        if(!doneSpawn){
            if(spawner.update(actualTimeMS)){
                doneSpawn = true; //End spawns
            }
        }else{
            if(map.getEnemies().isEmpty()){
                //Beat level!
                setLevelSuccess();
            }
        }

        notifyObservers(Constants.OBSERVER_TIME_MODIFIED);

    }

    private void setLevelSuccess(){
        notifyObservers(Constants.OBSERVER_LEVEL_COMPLETE);
        setGameRunning(false);
    }
    private void setLevelFailure() {
        notifyObservers(Constants.OBSERVER_GAME_OVER);
        setGameRunning(false);

    }

    public boolean isTowerBuildValid(Point p, TowerType selectedTower){
        //Draw TerrainType
        //TerrainType[][] terrainType = map.getTerrainTypes();
        try {
            Terrain[][] terrains = map.getTerrains();

            int blockSizeX = (int) (Constants.GAME_DIMENSION.getWidth() / terrains[0].length);
            int blockSizeY = (int) (Constants.GAME_DIMENSION.getHeight() / terrains.length);


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
        }catch(ArrayIndexOutOfBoundsException e){
            //TODO decide if it's worth fixing this...
            //Silently ignore this bug... for now
            return false;
        }
    }

    /**
     * Attempt to build tower at selected point and tower.
     * @param point - The point to build the tower at
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
        GameComponent construct = construction.buildTower(x, y, selectedTower);
        if (map.addComponent(construct)) {
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

    public GameMap getMap(){
        return map;
    }


    public int getLevel() {
        return level;
    }

    public int getTime(){
        return timeElapsed;
    }

    public String getWave(){
        return spawner.getWave();
    }

    public void updateWave() {
        notifyObservers(Constants.OBSERVER_WAVE_SPAWNED);
    }
}
