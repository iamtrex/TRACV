package com.tracv.model;

import com.tracv.gamecomponents.GameComponent;
import com.tracv.gamecomponents.Terrain;
import com.tracv.gamecomponents.Tower;
import com.tracv.observerpattern.Observable;
import com.tracv.observerpattern.Observer;
import com.tracv.types.TowerType;

import java.awt.Point;
import java.util.Calendar;
import java.util.Iterator;


/**
 * Handles user input in modification of gamestate.
 */
public class GameProcess extends Observable implements Iterable<GameComponent>, Observer{

    private GameState state;
    private Evolver evolver;
    private GameMap map;
    private EnemySpawner spawner;
    private LevelJsonParser levelParser;

    public GameProcess(){
        levelParser = new LevelJsonParser();
        evolver = new Evolver();
        spawner = new EnemySpawner(evolver);
        state = evolver.getGameState();
        map = state.getGameMap();
    }


    /**
     * Do something with clicks.
     */
    /**
     * @param p - Point on map.
     * Will build tower or select tower or do nothing
     */
    public void mouseClick(Point p) {

    }

    public Point adjustPointToGrid(Point p){
        return p;
    }
    public boolean isTowerLocationValid(Point p){
        return false;
    }
    public boolean buildTower(Point p){
        return false;
    }
    public void attemptUpgradeTower(TowerType upgradeType) {
    }

    public void attemptSellTower() {
    }

    @Override
    public void update(Observable o, String msg) {
        notifyObservers(msg);
    }

    /**
     * Modifying Game State
     * @param level
     */
    public void startNewGame(int level) {
        state.setLevel(level);
        levelParser.readLevel(level);
        //TODO
    }
    public void pauseGame(){
        evolver.changeState(State.PAUSED);
    }
    public void resumeGame() {
        evolver.changeState(State.PLAYING);
    }

    public void restartGame() {
        evolver.changeState(State.PAUSED);
        //TODO
        evolver.changeState(State.PLAYING);
    }


    /**
     * Setters and getters
     */
    public Tower getSelectedTower() {
        return state.getSelectedTower();
    }
    public void setSelectedTower(Tower t){
        state.setSelectedTower(t);
    }
    public void setBuildTowerType(TowerType tt){
        state.setBuildTowerType(tt);
    }

    public GameMap getMap() {
        return map;
    }

    public Terrain[][] getTerrain() {
        return map.getTerrains();
    }

    @Override
    public Iterator iterator() {
        return map.getGameComponents().iterator();
    }

    public void gainGold(int i) {
        state.gainGold(i);
    }

    public State getState() {
        return evolver.getState();
    }

    public GameState getGameState() {
        return state;
    }

    public boolean isDoneSpawn() {
        return spawner.isDoneSpawn();
    }

    public int getTimeToNextWave() {
        return spawner.getTimeToNextWave();
    }


}

