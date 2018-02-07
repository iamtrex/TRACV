package com.tracv.model;

import com.tracv.observerpattern.Observable;
import com.tracv.types.TowerType;
import com.tracv.util.TerrainParser;
import com.tracv.util.Constants;

import java.awt.*;
import java.util.Iterator;

public class GameState extends Observable implements Iterable<GameComponent>{


    //private List<GameComponent> gameComponents;
    private GameMap map;
    private EnemyFactory mobs;
    private TowerFactory construction;

    private int gold;
    private int score;


    public GameState() {
        //map = new GameMap(); <- please make this work



        mobs = new EnemyFactory();
        construction = new TowerFactory();
        //TODO FIX TESTING PURPOSES.

        /*
        map = new GameMap(TerrainParser.parseTerrainFile(Constants.TERRAIN_FILE));
        gold = 500; // temp value, 500 cuz league
        score = 0;
        */
        //newGame(); //Initialize new game.
        
    }

    /**
     * Initiates a new game.
     * (victor) restores all the field back to basic values, basically copied the default ctor
     */
    public void newGame() {
        System.out.println("Starting new game");
        map = new GameMap(TerrainParser.parseTerrainFile(Constants.TERRAIN_FILE));
        
        gold = 500; // temp value, 500 cuz league
        score = 0;
    }

    /**
     * Updates the position of everything
     */
    public void update() {
        //insert pathfinding algorithm here
    }

    public boolean isTowerBuildValid(Point p){
        //Draw Terrain
        Terrain[][] terrain = map.getTerrains();

        int blockSizeX = (int) (Constants.GAME_DIMENSION.getWidth() / terrain[0].length);
        int blockSizeY = (int) (Constants.GAME_DIMENSION.getHeight() / terrain.length);

        Terrain ter = terrain[p.y/blockSizeY][p.x/blockSizeX];
        if(ter != Terrain.BUILDABLE){
            return false;
        }

        for(GameComponent gc : map.getGameComponents()){
            if(gc instanceof Tower){
                Tower t = (Tower) gc;
                if((p.getX()-t.getX()) <= t.getSize()){
                    return false;
                }else if((p.getY() - t.getY()) <= t.getSize()){
                    return false;
                }
            }
        }

        return true;
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

        if (!isTowerBuildValid(point) || gold < cost) {
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

    public void spawnEnemy(Point point) {
        GameComponent enemy = mobs.spawn();
        enemy.setX(point.getX());
        enemy.setY(point.getY());
        map.addComponent(enemy); // spawns at spawning point
    }
    //TODO Implement
    public boolean isGameOver() {
        return false;
    }


    // maybe pause shouldnt be here? -victor
    public void pause() {
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
