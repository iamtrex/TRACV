package com.tracv.model;

import com.tracv.observerpattern.Observable;
import com.tracv.types.TowerType;

import java.awt.*;

public class GameState extends Observable{
    public void newGame() {

    }

    private void evolve() {

    }


    public void attemptToBuildTower(Point point, TowerType selectedTower) {

    }

    //TODO Implement
    public boolean isGameOver() {
        return false;
    }

    public void pause() {
    }
}
