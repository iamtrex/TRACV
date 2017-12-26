package com.tracv.ui;

import com.tracv.swing.Button;
import com.tracv.ui.game.GamePane;
import com.tracv.ui.game.HUDPane;
import com.tracv.util.Comp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The Game.
 */
public class TDGame extends JLayeredPane implements ActionListener{

    private TDFrame tdf;

    private HUDPane hudPane;
    private GamePane gamePane;
    private MenuPane menuPane;

    public TDGame(TDFrame tdf){
        this.tdf = tdf;

        hudPane = new HUDPane();
        gamePane = new GamePane();
        menuPane = new MenuPane();

        Comp.add(gamePane, this, 0, 0, 0, 1, 1, 1, 1, GridBagConstraints.BOTH, GridBagConstraints.CENTER);
        Comp.add(hudPane, this, 0, 0, 0, 1, 1, 1, 0, GridBagConstraints.HORIZONTAL, GridBagConstraints.CENTER);
        Comp.add(menuPane, this, 1, 0, 0, 1, 1, 0, 0, GridBagConstraints.BOTH, GridBagConstraints.CENTER);


    }

    private Button pause;

    private class MenuPane extends JPanel{
        public MenuPane(){
            pause = new Button("Pause"); //Show menu;
            pause.addActionListener(TDGame.this::actionPerformed);
            this.add(pause);
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if(source == pause){
            //TODO Pause state of game and show menu.

            tdf.toggleMenu(true);
        }

    }
}
