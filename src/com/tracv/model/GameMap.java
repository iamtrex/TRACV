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
    //private Base base;

    //sublists of gameComponents.
    private List<Tower> towers;
    private List<Enemy> enemies;
    private List<Projectile> projectiles;
    private List<Base> bases;


    /**
     * Constructor for GameMap, makes a new GameMap with the input terrains containing
     * no GameComponents
     * @param terrains the input model of the map
     */
    public GameMap (Terrain[][] terrains) {
        this.gameComponents = new ArrayList<>();
        this.terrains = terrains;
        this.blockSize = Constants.DEFAULT_BLOCK_SIZE;

        towers = new ArrayList<>();
        enemies = new ArrayList<>();
        projectiles = new ArrayList<>();
        bases = new ArrayList<>();



        //TODO do not use default base...
        Base base = new Base(1000, 50, 0, Constants.GAME_DIMENSION.getHeight(), null);
        bases.add(base);
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
     * @param gc the component to add
     * @return true if the add operation was successful
     */
    public boolean addComponent(GameComponent gc) {
        if(gc instanceof Projectile){
            projectiles.add((Projectile)gc);
        }else if(gc instanceof Enemy){
            enemies.add((Enemy)gc);
        }else if(gc instanceof Tower){
            towers.add((Tower)gc);
        }else if(gc instanceof Base){
            bases.add((Base)gc);
        }else{
            System.out.println("Wrong component type");
        }
        return gameComponents.add(gc);
    }

    /**
     * Removes teh specific component from the GameMap
     * @param gc the component to remove
     * @return true if the remove operation was successful
     */
    public boolean removeComponent(GameComponent gc) {
        if(gc instanceof Projectile){
            projectiles.remove(gc);
        }else if(gc instanceof Enemy){
            enemies.remove(gc);
        }else if(gc instanceof Tower){
            towers.remove(gc);
        }else if(gc instanceof Base){
            bases.remove(gc);
        }else{
            System.out.println("Wrong component type");
        }
        return gameComponents.remove(gc);
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


    //TODO Fix this implementation
    public Base getBase() {
        return bases.get(0);
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }

    public List<Projectile> getProjectiles() {
        return projectiles;
    }

    public List<Tower> getTowers() {
        return towers;
    }
}
