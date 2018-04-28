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

import java.awt.*;
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
        spawner = new EnemySpawner();
        state = new GameState();
        evolver = new Evolver(spawner, state);
        //spawner = evolver.getSpawner();
        //state = evolver.getGameState();
        map = state.getGameMap();

        evolver.addObserver(this);
        state.addObserver(this);
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
        if(selected != null) {
            if (Geometry.isPointInObject(p, selected)) {
                return; //Ignore.
            }
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
        //System.out.println("Checking if tower valid");
        if(state.getBuildTowerType() == null){
            return false;
        }

        //Check if building on buildable terrain
        List<Terrain> terrs = getTerrainInRange(p);
        for(Terrain t : terrs){
            if(t.getType() != TerrainType.BUILDABLE){
                return false;
            }
        }
        TowerType type = state.getBuildTowerType();
        Tower temp = new Tower(p.x-type.getWidth()/2, p.y-type.getHeight()/2, type); //Mouse is center of tower, but the tower takes the top left coord

        synchronized(map.getTowers()){
            for(Tower t : map.getTowers()){
                //Point is on top of tower.
                if(Geometry.hasIntersection(temp, t)){
                    //If they intersect in any way...
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
        double blockW = Constants.DEFAULT_BLOCK_SIZE; //TODO CAN APPLY ZOOM SCALING IN THE FUTURE
        double blockH = Constants.DEFAULT_BLOCK_SIZE;
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
            endI = (int)Math.ceil(xMax/blockW);

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
            notifyObservers(Constants.OBSERVER_UPGRADED_TOWER);
            Logger.getInstance().log("Successfully upgraded tower", LoggerLevel.STATUS);
        }else{
            Logger.getInstance().log("Cannot upgrade tower, insufficient gold", LoggerLevel.WARNING);
        }
    }

    public void attemptSellTower() {
        state.gainGold((int)Math.round(getSelectedTower().getSellPrice()));
        map.removeComponent(getSelectedTower());
        setSelectedTower(null);
        notifyObservers(Constants.OBSERVER_SOLD_TOWER);

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
        System.out.println("Starting new game!!");
        state.reset();
        state.setLevel(level);
        levelParser.readLevel(level);
        map.reset();
        map.loadLevel(levelParser.getFile());
        spawner.loadLevel(levelParser.getSpawnTime(), levelParser.getSpawnQueue(),
                map.getStart().getPixelX(), map.getStart().getPixelY());

        //Evolver will start ticking the gamestate :)
        evolver.changeState(State.PLAYING);
        notifyObservers(Constants.OBSERVER_NEW_GAME);
    }
    public void pauseGame(){
        evolver.changeState(State.PAUSED);
        notifyObservers(Constants.OBSERVER_GAME_PAUSED);
    }
    public void resumeGame() {
        evolver.changeState(State.PLAYING);
    }

    public void restartGame() {
        startNewGame(state.getLevel());
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
    public TowerType getBuildTowerType() {
        return state.getBuildTowerType();
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

    public String getWave() {
        return spawner.getWave();
    }

    public Dimension getMapSize() {
        return map.getMapSize();
    }
}

