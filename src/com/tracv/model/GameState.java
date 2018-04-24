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
import java.util.*;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;


public class GameState extends Observable implements Iterable<GameComponent> {

    private GameMap map;

    private LevelJsonParser levelParser;



    //GameState Elements.
    private int gold;
    private int score;
    private int timeElapsed;
    private int level;
    private TowerType selectedTowerType; //Build tower type.
    private Tower selectedTower; //Selected tower on the map


    public GameState(){
        map = new GameMap();
        levelParser = new LevelJsonParser();
    }

    public void loadNewGame(int level){
        this.level = level;
        levelParser.readLevel(level);
        map.loadLevel(levelParser.getFile());
        notifyObservers(Constants.OBSERVER_NEW_GAME);

    }

    public void reset(){
        map.reset();
        gold = Constants.DEFAULT_GOLD;
        score = 0;
        timeElapsed = 0;
        selectedTowerType = null;
        selectedTower = null;
    }


    public void setSelectedTower(Tower t){
        selectedTower = t;
    }
    public Tower getSelectedTower(){
        return selectedTower;
    }
    public void setSelectedTowerType(TowerType tt){
        selectedTowerType = tt;
    }
    public TowerType getSelectedTowerType(){
        return selectedTowerType;
    }

    @Override
    public Iterator<GameComponent> iterator() {
        return null;
    }

    public void levelCompleted() {
    }

    public void increaseTime(int refreshDelay) {
    }

    public void levelFailed() {
    }

    public GameMap getGameMap() {
        return map;
    }

    public void gainGold(int killGold) {
    }
}
