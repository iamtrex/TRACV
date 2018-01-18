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
        newGame(); //Initialize new game.
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


    /**
     * Attempt to build tower at selected point and tower.
     * @param point - The point to build the tower at
     * @param selectedTower - The type of tower to build
     */
    public boolean attemptToBuildTower(Point point, TowerType selectedTower) {
        int COST = 0; //temp, later will take from TowerType
        if (false) {
            //checks for whether the terrain is buildable
            //checks for whether theres already a terrain there
            //checks for whether theres enough gold
            //return false;
        }
        double x = point.getX();
        double y = point.getY();
        GameComponent construct = construction.buildTower(x, y, selectedTower);
        if (map.addComponent(construct)) {
            gold = gold - COST;
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
}
