package com.tracv.ui;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import com.tracv.directional.Geometry;
import com.tracv.model.Evolver;
import com.tracv.model.GameProcess;
import com.tracv.model.GameState;
import com.tracv.model.State;
import com.tracv.observerpattern.Observable;
import com.tracv.observerpattern.Observer;
import com.tracv.swing.IconButton;
import com.tracv.types.TowerType;
import com.tracv.ui.game.GamePane;
import com.tracv.ui.game.HUDButtonPane;
import com.tracv.ui.game.HUDPane;
import com.tracv.util.Comp;
import com.tracv.util.Constants;
import com.tracv.util.Logger;
import com.tracv.util.LoggerLevel;
import javafx.concurrent.Worker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Holds the UI of the actual game. Including the map/components as well as the HUD.
 */
public class TDGame extends JLayeredPane implements ActionListener, Observer {

    private TDFrame tdf;
    private GameProcess game;


    private HUDPane hudPane;
    private GamePane gamePane;
    private MenuPane menuPane;

    private IconButton pause;



    public TDGame(TDFrame tdf) {
        this.tdf = tdf;

        gamePane = new GamePane();
        game = gamePane.getGameProcess();

        hudPane = new HUDPane(game);

        game.addObserver(this);
        game.addObserver(hudPane.getHUDStatsPane());
        game.addObserver(hudPane.getHUDStatePane());
        game.addObserver(hudPane);
        game.addObserver(gamePane);

        menuPane = new MenuPane();

        Comp.add(gamePane, this, 0, 0, 0, 1, 1, 1, 1,
                GridBagConstraints.BOTH, GridBagConstraints.CENTER);

        //Align to bottom edge.
        Comp.add(hudPane, this, 0, 0, 1, 1, 1, 1, 1,
                GridBagConstraints.NONE, GridBagConstraints.BASELINE);

        //Align to top right corner with default sizes.
        Comp.add(menuPane, this, 1, 0, 0, 1, 1, 1, 1,
                GridBagConstraints.NONE, GridBagConstraints.FIRST_LINE_END);

        hudPane.getHUDButtonsPane().addPropertyChangeListener(new TowerChangeListener());
    }

    //Pass through to GameProcess.
    public void startNewGame(int level) {
        game.startNewGame(level);
    }
    public void resumeGame() {
        game.resumeGame();
    }
    public void restartGame() {
        game.restartGame();
    }

    @Override
    public void update(Observable o, String msg) {
        //Show menu if the level is over.
        if (msg.equals(Constants.OBSERVER_LEVEL_FAILED) || msg.equals(Constants.OBSERVER_LEVEL_COMPLETE)) {
            tdf.toggleMenu(true, msg);
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == pause) {
            game.pauseGame();
            tdf.toggleMenu(true);
        }

    }

    private class TowerChangeListener implements PropertyChangeListener {
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            if (evt.getPropertyName().equals(HUDButtonPane.TOWER_CHANGED)) {
                game.setBuildTowerType((TowerType) evt.getNewValue());
            }
        }
    }

    /**
     * Menu that lies on top of the game in order to pause hte game/sounds etc...
     */
    private class MenuPane extends JPanel {
        public MenuPane() {
            this.setPreferredSize(Constants.ICON_SIZE); // TEMP SIZE.
            pause = new IconButton("Pause"); //Show menu;
            pause.addActionListener(TDGame.this::actionPerformed);
            Comp.add(pause, this, 0, 0, 1, 1, 0, 0,
                    GridBagConstraints.NONE, GridBagConstraints.FIRST_LINE_END);
        }
    }

}
