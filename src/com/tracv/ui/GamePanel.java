package com.tracv.ui;

import javax.swing.*;
import java.awt.*;

/*
    The visual representation of the game.
 */
public class GamePanel extends JPanel {
    private HUDPanel HUD; //Represents the HUD interface of the game;
    private MapPanel map; //Represents the ingame map.

    public GamePanel(){
        HUD = new HUDPanel();
        map = new MapPanel();

    }


    /** Overriding the redrawing method to draw on our components.
     */
    @Override
    public void paint(Graphics g){

    }
}
