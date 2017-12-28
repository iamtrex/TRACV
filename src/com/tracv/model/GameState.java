package com.tracv.model;

import com.tracv.observerpattern.Observable;
import com.tracv.types.TowerType;
import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GameState extends Observable implements Iterable<GameComponent>{


    private List<GameComponent> gameComponents;

    public GameState() {
        gameComponents = new ArrayList<>();

    }

    /**
     * Initiates a new game.
     */
    public void newGame() {

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
        return gameComponents.iterator();
    }
}
