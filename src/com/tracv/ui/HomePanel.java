package com.tracv.ui;

import com.tracv.swing.Button;
import com.tracv.util.Comp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomePanel extends JPanel {

    private Button bSettings;
    private Button bNewGame;
    private Button bQuit;

    private GameFrame gf;

    public HomePanel(GameFrame gameFrame){
        super();

        this.gf = gameFrame;

        bNewGame = new Button("New Game");
        bSettings = new Button("Settings");
        bQuit = new Button("Quit");

        Comp.add(bNewGame, this, 0, 0, 1, 1, 1, 0, GridBagConstraints.BOTH, GridBagConstraints.CENTER);
        Comp.add(bSettings, this, 0, 1, 1, 1, 1, 0, GridBagConstraints.BOTH, GridBagConstraints.CENTER); Comp.add(bSettings, this, 0, 1, 1, 1, 1, 0, GridBagConstraints.BOTH, GridBagConstraints.CENTER);
        Comp.add(bQuit, this, 0, 2, 1, 1, 1, 0, GridBagConstraints.BOTH, GridBagConstraints.CENTER);

        bNewGame.addActionListener(new ActionHandler());
        bSettings.addActionListener(new ActionHandler());
        bQuit.addActionListener(new ActionHandler());
    }

    private class ActionHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Object source = e.getSource();
            if(source == bNewGame){
                gf.startNewGame();
            }else if(source == bSettings){
                //TODO implement Settings Panel
            }else if(source == bQuit){
                System.exit(0); //Exit the program with status 0;
                //TODO implement save features, and perhaps ask to confirm exit?
            }
        }
    }
}
