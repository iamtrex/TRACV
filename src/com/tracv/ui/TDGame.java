package com.tracv.ui;

import com.tracv.swing.Button;
import com.tracv.ui.game.GamePanel;
import com.tracv.ui.game.HUDPanel;

import javax.swing.*;

/**
 * The Game.
 */
public class TDGame extends JPanel {

    HUDPanel hudPane;
    GamePanel gamePane;

    public TDGame(){
        //TODO remove placeholder.
        this.add(new Button("This will be the game lol"));


        hudPane = new HUDPanel();
        gamePane = new GamePanel();


    }
}
