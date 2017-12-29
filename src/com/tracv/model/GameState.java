package com.tracv.model;

import com.tracv.observerpattern.Observable;
import com.tracv.types.TowerType;
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
        //map = new GameMap(); <- please make this work
        mobs = new EnemyFactory();
        construction = new TowerFactory();
        gold = 500; // temp value, 500 cuz league
        score = 0;
    }

    /**
     * Initiates a new game.
     * (victor) restores all the field back to basic values, basically copied the default ctor
     */
    public void newGame() {
        //map = new GameMap();
        gold = 500; // temp value, 500 cuz league
        score = 0;
    }

    /**
     * Updates the position of everything
     */
    public void update() {

    }


    /**
     * Attempt to build tower at selected point and tower.
     * @param point - The point to build the tower at
     * @param selectedTower - The type of tower to build
     */
    public void attemptToBuildTower(Point point, TowerType selectedTower) {

    }

    //TODO Implement
    public boolean isGameOver() {
        return false;
    }

    public void pause() {
    }


    @Override
    public Iterator<GameComponent> iterator() {
        return map.getGameComponents().iterator();
    }
}
