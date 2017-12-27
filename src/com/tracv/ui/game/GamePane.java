package com.tracv.ui.game;

import com.tracv.model.GameState;
import com.tracv.observerpattern.Observable;
import com.tracv.observerpattern.Observer;
import com.tracv.types.TowerType;
import com.tracv.util.Constants;

import javax.swing.*;
import java.awt.*;

public class GamePane extends JPanel implements Observer{

    private GameState gs;

    public GamePane(){
        gs = new GameState();

        this.setPreferredSize(Constants.GAME_DIMENSION);
        this.setBackground(Color.BLUE);

    }

    public GameState getGameState(){
        return gs;
    }

    public void attemptToBuildTower(Point point, TowerType selectedTower) {
        gs.attemptToBuildTower(point, selectedTower);

    }


    @Override
    public void update(Observable o) {

    }
}
