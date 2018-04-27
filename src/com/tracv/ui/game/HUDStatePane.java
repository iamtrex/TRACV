package com.tracv.ui.game;

import com.tracv.model.GameProcess;
import com.tracv.model.GameState;
import com.tracv.observerpattern.Observable;
import com.tracv.observerpattern.Observer;
import com.tracv.swing.Label;
import com.tracv.swing.Pane;
import com.tracv.util.Comp;
import com.tracv.util.Constants;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

/**
 * Holds details fo the game state ie. Gold/Time
 *
 */
public class HUDStatePane extends Pane implements Observer {

    private HUDPane hudPane;
    private GameProcess game;

    private Label lGold;
    private Label lTime;
    private Label lLevel;
    private Label lWave;
    private Label lNextWave;
    private Label lHealth;



    public HUDStatePane(HUDPane hudPane, GameProcess game) {
        this.hudPane = hudPane;
        this.game = game;
        resetText();

        Comp.add(lGold, this, 0, 0, 2, 1, 1, 1, GridBagConstraints.BOTH, GridBagConstraints.CENTER,
                Constants.INSETS_BETWEEN, Constants.INSETS_OUTSIDE, Constants.INSETS_BETWEEN, Constants.INSETS_OUTSIDE);
        Comp.add(lTime, this, 0, 1, 2, 1, 1, 1, GridBagConstraints.BOTH, GridBagConstraints.CENTER,
                Constants.INSETS_BETWEEN, Constants.INSETS_OUTSIDE, Constants.INSETS_BETWEEN, Constants.INSETS_OUTSIDE);
        Comp.add(lLevel, this, 0, 2, 2, 1, 1, 1, GridBagConstraints.BOTH, GridBagConstraints.CENTER,
                Constants.INSETS_BETWEEN, Constants.INSETS_OUTSIDE, Constants.INSETS_BETWEEN, Constants.INSETS_OUTSIDE);
        Comp.add(lWave, this, 0, 3, 1, 1, 1, 1, GridBagConstraints.BOTH, GridBagConstraints.CENTER,
                Constants.INSETS_BETWEEN, Constants.INSETS_OUTSIDE, Constants.INSETS_BETWEEN, Constants.INSETS_OUTSIDE);
        Comp.add(lNextWave, this, 1, 3, 1, 1, 1, 1, GridBagConstraints.BOTH, GridBagConstraints.CENTER,
                Constants.INSETS_BETWEEN, Constants.INSETS_OUTSIDE, Constants.INSETS_BETWEEN, Constants.INSETS_OUTSIDE);
        Comp.add(lHealth, this, 0, 4, 2, 1, 1, 1, GridBagConstraints.BOTH, GridBagConstraints.CENTER,
                Constants.INSETS_BETWEEN, Constants.INSETS_OUTSIDE, Constants.INSETS_BETWEEN, Constants.INSETS_OUTSIDE);

        this.setBackground(Color.WHITE);

        this.setMinimumSize(Constants.HUD_STATE_SIZE);
        this.setPreferredSize(Constants.HUD_STATE_SIZE);
    }

    @Override
    public void update(Observable o, String msg) {
        if(msg.equals(Constants.OBSERVER_GOLD_CHANGED)){
            updateGold();
        }else if(msg.equals(Constants.OBSERVER_NEW_GAME)){
            updateGold();
            updateTime();
            updateWave();
            updateLevel();
            updateHealth();
        }else if(msg.equals(Constants.OBSERVER_TIME_MODIFIED)){
            updateTime();
        }else if(msg.equals(Constants.OBSERVER_WAVE_SPAWNED)){
            updateWave();
        }else if(msg.equals(Constants.OBSERVER_BASE_HEALTH_CHANGED)){
            updateHealth();
        }else if(msg.equals(Constants.OBSERVER_STATE_TERMINATED)){
            //Reset HUD.
            resetText();
        }
    }

    private void resetText() {
        lGold = new Label("Gold ", Label.MEDIUM, Label.LEFT, Label.INVISIBLE);
        lTime = new Label("Time ", Label.MEDIUM, Label.LEFT, Label.INVISIBLE);
        lLevel = new Label("Level ", Label.MEDIUM, Label.LEFT, Label.INVISIBLE);
        lWave = new Label("Wave ", Label.MEDIUM, Label.LEFT, Label.INVISIBLE);
        lNextWave = new Label("Next Wave In ", Label.MEDIUM, Label.LEFT, Label.INVISIBLE);
        lHealth = new Label("Health ", Label.MEDIUM, Label.LEFT, Label.INVISIBLE);
    }

    private void updateTime(){
        int time = (int)Math.round(game.getGameState().getTimeMS()/1000.0); //gs time is in milliseconds.
        int minutes = time/60;
        int seconds = time % 60;
        String sMin, sSec;
        if(minutes < 10){
            sMin = "0" + String.valueOf(minutes);
        }else{
            sMin = String.valueOf(minutes);
        }
        if(seconds < 10){
            sSec = "0" + String.valueOf(seconds);
        }else{
            sSec = String.valueOf(seconds);
        }

        SwingUtilities.invokeLater(()->{
            lTime.setText("Time " + sMin + ":" + sSec);
            if(!game.isDoneSpawn()){
                lNextWave.setText("Next Wave " + game.getTimeToNextWave());
            }else{
                lNextWave.setText("");
            }
        });

    }
    private void updateGold(){
        SwingUtilities.invokeLater(()->lGold.setText("Gold " + game.getGameState().getGold()));
    }
    private void updateWave(){
        SwingUtilities.invokeLater(()->lWave.setText("Wave " + game.getWave()));
    }
    private void updateLevel(){
        SwingUtilities.invokeLater(()->lLevel.setText("Level " + game.getGameState().getLevel()));
    }
    private void updateHealth(){SwingUtilities.invokeLater(()->lHealth.setText("Health " + game.getGameState().getBaseHealth()));}
}
