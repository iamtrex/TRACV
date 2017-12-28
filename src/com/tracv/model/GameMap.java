package com.tracv.model;


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

    public GameMap ( Terrain[][] terrains) {
        this.gameComponents = new ArrayList<GameComponent>();
        this.terrains = terrains;
    }
    public List<GameComponent> getGameComponents() {
        return gameComponents;
    }

    public void addComponent(GameComponent component) {
        gameComponents.add(component);
    }

    public void removeComponent(GameComponent component) {
        gameComponents.remove(component);
    }

    public Terrain[][] getTerrains() {
        return terrains;
    }

}
