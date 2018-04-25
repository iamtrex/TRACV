package com.tracv.model;

import com.tracv.directional.Geometry;
import com.tracv.gamecomponents.GameComponent;
import com.tracv.gamecomponents.Terrain;
import com.tracv.gamecomponents.Tower;
import com.tracv.observerpattern.Observable;
import com.tracv.observerpattern.Observer;
import com.tracv.types.TerrainType;
import com.tracv.types.TowerType;
import com.tracv.util.Constants;
import com.tracv.util.Logger;
import com.tracv.util.LoggerLevel;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


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
        if(isTowerLocationValid(p)){
            buildTower(p);
        }else{
            adjustTowerSelection(p);
        }
    }

    /**
     * Tries to select tower at location or deselect tower if clicking on blank space.
     *
     */
    private void adjustTowerSelection(Point p){
        Tower selected = state.getSelectedTower();
        if(Geometry.isPointInObject(p, selected)){
            return; //Ignore.
        }

        //Otherwise, clear currently selected and potentially select new building.
        if (selected != null) {
            state.setSelectedTower(null);
        }

        synchronized(map.getTowers()){
            for (Tower t : map.getTowers()) {
                if (Geometry.isPointInObject(p, t)) {
                    state.setSelectedTower(t);
                    break;
                }
            }
        }
    }

    //TODO
    public Point adjustPointToGrid(Point p){
        return p;
    }

    /**
     *
     * @param p - location on map. (NOT ON SCREEN)
     * @return
     */
    public boolean isTowerLocationValid(Point p){
        if(state.getSelectedTower() == null){
            return false;
        }

        //Check if building on buildable terrain
        List<Terrain> terrs = getTerrainInRange(p);
        for(Terrain t : terrs){
            if(t.getType() != TerrainType.BUILDABLE){
                return false;
            }
        }

        synchronized(map.getTowers()){
            for(Tower t : map.getTowers()){
                //Point is on top of tower.
                if (p.getX() >= t.getX() && p.getY () >= t.getY() &&
                        Math.abs(p.getX() - t.getX()) <= t.getWidth() &&
                        Math.abs((p.getY() - t.getY())) <= t.getHeight()) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * returns all terrains that the block currenly bulids on.
     * @param p - point on map to build on.
     * @return
     */
    private List<Terrain> getTerrainInRange(Point p) {
        int blockW = (int)Math.round(Constants.DEFAULT_BLOCK_SIZE);
        int blockH = (int)Math.round(Constants.DEFAULT_BLOCK_SIZE);
        List<Terrain> terrs = new ArrayList<>();

        TowerType tt = state.getBuildTowerType();
        double xMin = p.getX() - tt.getWidth()/2;
        double yMin = p.getY() - tt.getHeight()/2;
        double xMax = xMin + tt.getWidth();
        double yMax = yMin + tt.getHeight();
        Terrain[][] terrains = map.getTerrains();

        //Indexes of Terrains that might be covered by the tower.
        int startI, startJ, endI, endJ;

        if(xMin <= 0)
            startI = 0;
        else
            startI = (int)Math.floor(xMin/blockW);

        if(xMax >= terrains[0].length*blockW)
            endI = terrains[0].length;
        else
            endI = (int)Math.ceil(xMin/blockW);

        if(yMin <= 0)
            startJ = 0;
        else
            startJ = (int)Math.floor(yMin/blockH);

        if(yMax >= terrains.length * blockH)
            endJ = terrains.length;
        else
            endJ = (int)Math.ceil(yMax/blockH);


        for(int j = startJ; j < endJ; j++){
            for(int i=startI; i<endI; i++){
                terrs.add(terrains[j][i]);
            }
        }


        /*
        for(int j=0; j<terrains.length; j++){
            for(int i=0; i<terrains[j].length; i++){
                int xPos = blockW * i;
                int yPos = blockH * j;
                if(xPos >= xMin && xPos <= xMax
                    && yPos >= yMin && yPos <= yMax){
                    terrs.add(terrains[j][i]);
                }
            }
        }*/ //Slow O(n) speed.
        return terrs;
    }

    /**
     * Invarient - Has checked if tower location is valid
     * @param p
     * @return
     */
    private boolean buildTower(Point p){
        TowerType type = state.getBuildTowerType();
        int cost = type.getCost();
        if(state.getGold() >= cost){
            state.useGold(cost);
            Tower tower = new Tower(p.getX() - type.getWidth() / 2, p.getY() - type.getHeight() / 2, type);
            map.addComponent(tower);
            return true;
        }
        return false;
    }

    public void attemptUpgradeTower(TowerType upgradeType) {
        if(state.getGold() >= upgradeType.getUpgradeCost()){
            state.useGold(upgradeType.getUpgradeCost());
            getSelectedTower().modifyType(upgradeType);
            Logger.getInstance().log("Successfully upgraded tower", LoggerLevel.STATUS);
        }else{
            Logger.getInstance().log("Cannot upgrade tower, insufficient gold", LoggerLevel.WARNING);
        }
    }

    public void attemptSellTower() {
        state.gainGold((int)Math.round(getSelectedTower().getSellPrice()));
        map.removeComponent(getSelectedTower());
        setSelectedTower(null);

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
        //Load the level.
        state.setLevel(level);
        levelParser.readLevel(level);
        spawner.loadLevel(levelParser.getSpawnTime(), levelParser.getSpawnQueue());
        map.loadLevel(levelParser.getFile());

        //Evolver will start ticking the gamestate :)
        evolver.changeState(State.PLAYING);

        notifyObservers(Constants.OBSERVER_NEW_GAME);

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
