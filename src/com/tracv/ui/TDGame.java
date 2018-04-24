package com.tracv.ui;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import com.tracv.directional.Geometry;
import com.tracv.model.Evolver;
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
import com.tracv.util.Logger;
import com.tracv.util.LoggerLevel;

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
    private GameState gs;

    private HUDPane hudPane;
    private GamePane gamePane;
    private MenuPane menuPane;

    private IconButton pause;

    private MapThread mapThread;

    public TDGame(TDFrame tdf) {
        this.tdf = tdf;
        mapThread = new MapThread();

        gamePane = new GamePane();

        gs = gamePane.getGameState();

        hudPane = new HUDPane(gs);

        gs.addObserver(this);
        gs.addObserver(hudPane.getHUDStatsPane());
        gs.addObserver(hudPane.getHUDStatePane());
        gs.addObserver(hudPane);
        gs.addObserver(gamePane);

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
        gs.loadNewGame(level);
        gs.setGameRunning(true);
    }

    public void resumeGame() {
        gs.setGameRunning(true);
    }

    public void restartGame() {
        gs.restartLevel();
    }

    @Override
    public void update(Observable o, String msg) {
        //System.out.println("O msg " + o + " " + msg);
        if (o == gs) {
            if (msg.equals(Constants.OBSERVER_GAME_OVER)) {
                tdf.toggleMenu(true, Constants.OBSERVER_GAME_OVER);
            } else if (msg.equals(Constants.OBSERVER_LEVEL_COMPLETE)) {
                tdf.toggleMenu(true, Constants.OBSERVER_LEVEL_COMPLETE); //DONE - ADD ARGUMENTS TO MENU TOGGLE (FOR EXAMPLE SHOW STATS...)
            } else if(msg.equals(Constants.OBSERVER_GAME_RESUMED)){
                startMapThread();
            } else if(msg.equals(Constants.OBSERVER_GAME_PAUSED)){

            }
        }
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

    private void startMapThread(){
        if(mapThread == null){
            mapThread = new MapThread();
        }
        if(mapThread.isAlive()){
            Logger.getInstance().log("MapThread not properly terminated", LoggerLevel.ERROR);
        }else {
            mapThread = new MapThread();
            mapThread.start();
        }
    }

    /**
     * Listens for mouse position allowing movement of the screen around the map if the mouse is close to edge.
     */
    private class MapThread extends Thread{
        public void run(){
            while(gs.isRunning()){
                try{
                    long start = System.nanoTime();
                    handle();
                    long end = System.nanoTime();

                    Thread.sleep(Constants.MAP_MOVE_REFRESH - (end-start)/1000);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
            System.out.println("Exiting MapThread");
        }

        private void handle() {
            if(!TDGame.this.isVisible() || !tdf.isVisible())
                return;

            Point mouse = MouseInfo.getPointerInfo().getLocation();
            Point p = TDGame.this.getLocationOnScreen();
            Rectangle r = new Rectangle((int) p.getX(), (int) p.getY(),
                    TDGame.this.getWidth(), TDGame.this.getHeight());

            //Find edge
            boolean top = Geometry.isPointInTop(mouse, r, Constants.MOVEMENT_PIXEL_PADDING);
            boolean bot = Geometry.isPointInBot(mouse, r, Constants.MOVEMENT_PIXEL_PADDING);
            boolean left = Geometry.isPointInLeft(mouse, r, Constants.MOVEMENT_PIXEL_PADDING);
            boolean right = Geometry.isPointInRight(mouse, r, Constants.MOVEMENT_PIXEL_PADDING);
            gamePane.updateMapMove(top, bot, left, right);
            //gamePane.handleMapMove(top, bot, left, right);
        }
    }


}
