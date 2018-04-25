package com.tracv.model;

import com.tracv.gamecomponents.*;
import com.tracv.observerpattern.Observable;
import com.tracv.types.TowerType;
import com.tracv.util.Constants;

import java.util.*;


public class GameState extends Observable{

    private GameMap map;

    private LevelJsonParser levelParser;



    //GameState Elements.
    private int gold;
    private int score;
    private int timeElapsed; //Time in milliseconds.
    private int level;
    private TowerType buildTowerType; //Build tower type.
    private Tower selectedTower; //Selected tower on the map


    public GameState(){
        map = new GameMap();
        levelParser = new LevelJsonParser();
    }

    public void reset(){
        map.reset();
        gold = Constants.DEFAULT_GOLD;
        score = 0;
        timeElapsed = 0;
        buildTowerType = null;
        selectedTower = null;
    }


    public void setSelectedTower(Tower t){
        if(t == selectedTower)
            return;

        if(selectedTower != null)
            selectedTower.setSelected(false);

        selectedTower = t;

        if(selectedTower != null)
            selectedTower.setSelected(true);

        notifyObservers(Constants.OBSERVER_TOWER_SELECTED);
    }

    public Tower getSelectedTower(){
        return selectedTower;
    }
    public void setBuildTowerType(TowerType tt){
        buildTowerType = tt;
    }
    public TowerType getBuildTowerType(){
        return buildTowerType;
    }
    public int getGold(){return gold;}
    public String getBaseHealth(){return map.getBase().getHealth();}


    /**
     * Game Termination - User completed level successfully
     */
    public void levelCompleted() {
        reset();
        notifyObservers(Constants.OBSERVER_LEVEL_COMPLETE);
    }

    /**
     * Game Termination - User failed to complete the level.
     */
    public void levelFailed() {
        reset();
        notifyObservers(Constants.OBSERVER_LEVEL_FAILED);
    }


    public void increaseTime(int timeMS) {
        timeElapsed += timeMS;
    }

    public GameMap getGameMap() {
        return map;
    }

    public void useGold(int gold){
        this.gold -= gold;
    }
    public void gainGold(int gold) {
        this.gold += gold;
    }

    public int getTimeMS() {
        return timeElapsed;
    }

    public void setLevel(int level) {
        this.level = level;
    }
    public int getLevel(){
        return level;
    }
}
