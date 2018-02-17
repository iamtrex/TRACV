package com.tracv.model;

import com.tracv.directional.PointToPointDistance;
import com.tracv.observerpattern.Observable;
import com.tracv.types.TerrainType;
import com.tracv.types.TowerType;
import com.tracv.util.Constants;
import com.tracv.util.TerrainParser;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class GameState extends Observable implements Iterable<GameComponent>{


    //private List<GameComponent> gameComponents;
    private GameMap map;
    private EnemyFactory mobs;
    private TowerFactory construction;


    private int gold;
    private int score;


    public GameState() {


        mobs = new EnemyFactory();
        construction = new TowerFactory();

        //TODO FIX TESTING PURPOSES.
        /*
        map = new GameMap(TerrainParser.parseTerrainFile(Constants.TERRAIN_FILE));
        gold = 500; // temp value, 500 cuz league
        score = 0;
        */
        
    }

    /**
     * Initiates a new game.
     * (victor) restores all the field back to basic values
     */
    public void newGame() {
        System.out.println("Starting new game");
        //TODO temporarily loads a default map.. In future, can load different types of maps
        map = new GameMap(TerrainParser.parseTerrainFile(Constants.TERRAIN_FILE));
        
        gold = 500; // temp value, 500 cuz league
        score = 0;
    }

    /**
     * Updates the position of everything
     */
    public void update() {
        List<GameComponent> toAdd = new ArrayList<>();
        List<GameComponent> toDel = new ArrayList<>();
        List<Enemy> needToRetarget = new ArrayList<>();

        for(Enemy e : map.getEnemies()){
            boolean reachedBase = EnemyMotion.updateEnemy(e);

            //Delete e if it reaches base
            if(reachedBase){
                //TODO update Health of base since it crashed.
                System.out.println("CRASHED!");
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
        int cost = 0; //temp, later will take from TowerType //TODO FIX.


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
            gold = gold - cost;
            return true;
        }
        return false;
    }

    public void spawnEnemy(int x, int y) {
        System.out.println("Spawning Enemy");
        GameComponent enemy = mobs.spawn();
        enemy.setX(x);
        enemy.setY(y);
        map.addComponent(enemy); // spawns at spawning point
    }


    //TODO Implement
    public boolean isGameOver() {
        return false;
    }


    @Override
    public Iterator<GameComponent> iterator() {
        return map.getGameComponents().iterator();
    }


    //boring getters and setters
    public void gainGold(int i) {
        gold = gold + i;
    }

    public void useGold(int i) {
        gold = gold - i;
        if (gold < 0) gold = 0; //prevent negative gold, if for somereason it happens
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

    //Load Map with name
    public void loadMap(String map) {

    }
}
