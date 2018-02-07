package com.tracv.model;


import com.tracv.util.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a mutable Map of the game which contains terrain and
 * game components
 * Rep Invariant:
 */
public class GameMap {
    private List<GameComponent> gameComponents;
    private Terrain[][] terrains;
    private Double blockSize;
    private Base base;


    /**
     * Constructor for GameMap, makes a new GameMap with the input terrains containing
     * no GameComponents
     * @param terrains the input model of the map
     */
    public GameMap ( Terrain[][] terrains) {
        this.gameComponents = new ArrayList<>();
        this.terrains = terrains;
        this.blockSize = Constants.DEFAULT_BLOCK_SIZE;
        base = new Base(1000, 50, 0, Constants.GAME_DIMENSION.getHeight(), null);

    }

    /**
     * Getter to return a list of the GameComponents in the GameMap
     * @return a list of GameComponents within the GameMap
     */
    public List<GameComponent> getGameComponents() {
        return gameComponents;
    }

    /**
     * Adds the specific component to the GameMap
     * @param component the component to add
     * @return true if the add operation was successful
     */
    public boolean addComponent(GameComponent component) {
        return gameComponents.add(component);
    }

    /**
     * Removes teh specific component from the GameMap
     * @param component the component to remove
     * @return true if the remove operation was successful
     */
    public boolean removeComponent(GameComponent component) {
        return gameComponents.remove(component);
    }

    /**
     * Getter for the current terrains of the GameMap
     * @return a 2D array representing the terrains
     */
    public Terrain[][] getTerrains() {
        return terrains;
    }


    public Double getBlockSize(){
        return blockSize;
    }

    public void setBlockSize(Double blockSize){
        this.blockSize = blockSize;

    }

    public Base getBase() {
        return base;
    }
}
