package com.tracv.ui;

import com.tracv.directional.PointToPointDistance;
import com.tracv.model.GameState;
import com.tracv.observerpattern.Observable;
import com.tracv.observerpattern.Observer;
import com.tracv.swing.IconButton;
import com.tracv.types.TowerType;
import com.tracv.ui.game.GamePane;
import com.tracv.ui.game.HUDButtonPane;
import com.tracv.ui.game.HUDPane;
import com.tracv.util.Comp;
import com.tracv.util.Constants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * The Game.
 */
public class TDGame extends JLayeredPane implements ActionListener, Observer {

    private TDFrame tdf;

    private HUDPane hudPane;
    private GamePane gamePane;
    private MenuPane menuPane;

    private IconButton pause;

    private GameState gs;




    public TDGame(TDFrame tdf) {
        this.tdf = tdf;

        gamePane = new GamePane();
        gs = gamePane.getGameState();

        hudPane = new HUDPane(gs);


        gs.addObserver(this);
        gs.addObserver(hudPane.getHUDStatsPane());
        gs.addObserver(hudPane);
        gs.addObserver(gamePane);
        gs.addObserver(hudPane.getHUDStatePane());

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

    /*
    Map determines level to load.
     */
    public void startNewGame(int level) {
        gs.newGame(level);
        gs.setGameRunning(true);

    }

    @Override
    public void update(Observable o, String msg) {
        if (o == gs) {
            if (msg.equals(Constants.OBSERVER_GAME_OVER)) {
                tdf.toggleMenu(true, Constants.OBSERVER_GAME_OVER);
            } else if (msg.equals(Constants.OBSERVER_LEVEL_COMPLETE)) {
                tdf.toggleMenu(true, Constants.OBSERVER_LEVEL_COMPLETE); //DONE - ADD ARGUMENTS TO MENU TOGGLE (FOR EXAMPLE SHOW STATS...)

            } else if (msg.equals(Constants.OBSERVER_GAME_TICK)) {
                handle();
            }
        }
    }

    public GameState getGameState() {
        return gs;
    }

    public void resumeGame() {
        gs.setGameRunning(true);
    }


    /**
     * Menu that lies on top of the game in order to pause hte game/sounds etc...
     */
    private class MenuPane extends JPanel {
        public MenuPane() {
            this.setPreferredSize(Constants.ICON_SIZE);
            //this.setBorder(new LineBorder(Color.BLUE, 3));
            pause = new IconButton("Pause"); //Show menu;
            pause.addActionListener(TDGame.this::actionPerformed);
            Comp.add(pause, this, 0, 0, 1, 1, 0, 0,
                    GridBagConstraints.NONE, GridBagConstraints.FIRST_LINE_END);

        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == pause) {
            gs.setGameRunning(false);
            tdf.toggleMenu(true);
        }

    }


    private class TowerChangeListener implements PropertyChangeListener {
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            if (evt.getPropertyName().equals(HUDButtonPane.TOWER_CHANGED)) {
                gamePane.setSelectedTower((TowerType) evt.getNewValue());
            }
        }
    }


    private void handle() {
        Point mouse = MouseInfo.getPointerInfo().getLocation();
        Point p = TDGame.this.getLocationOnScreen();
        Rectangle r = new Rectangle((int) p.getX(), (int) p.getY(),
                TDGame.this.getWidth(), TDGame.this.getHeight());

            /*
            if (PointToPointDistance.isPointInRegion(e.getLocationOnScreen(), r, Constants.MOVEMENT_PIXEL_PADDING)) {
                return;
            }*/

        //Find edge
        boolean top = PointToPointDistance.isPointInTop(mouse, r, Constants.MOVEMENT_PIXEL_PADDING);
        boolean bot = PointToPointDistance.isPointInBot(mouse, r, Constants.MOVEMENT_PIXEL_PADDING);
        boolean left = PointToPointDistance.isPointInLeft(mouse, r, Constants.MOVEMENT_PIXEL_PADDING);
        boolean right = PointToPointDistance.isPointInRight(mouse, r, Constants.MOVEMENT_PIXEL_PADDING);

        //System.out.println("Moving");
        gamePane.setMapMove(top, bot, left, right);


    }

}
