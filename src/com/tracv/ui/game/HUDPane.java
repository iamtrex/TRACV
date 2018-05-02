package com.tracv.ui.game;

import com.tracv.controller.GameProcess;
import com.tracv.observerpattern.Observable;
import com.tracv.observerpattern.Observer;
import com.tracv.swing.Pane;
import com.tracv.types.TowerType;
import com.tracv.util.Comp;
import com.tracv.util.Constants;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class HUDPane extends JPanel implements Observer{
    private HUDButtonPane hudButtonPane;
    private HUDStatsPane hudStatsPane;
    private HUDStatePane hudStatePane;

    private TowerType selectedTowerType;


    public HUDPane(GameProcess game){
        this.setPreferredSize(Constants.HUD_DIMENSION);
        this.setOpaque(false);

        hudButtonPane = new HUDButtonPane(this, game);
        hudButtonPane.addPropertyChangeListener(new TowerChangeListener());

        hudStatsPane = new HUDStatsPane(this, game);
        hudStatePane = new HUDStatePane(this, game);

        Pane statsSpaceHolderPane = new Pane();

        Comp.add(hudStatsPane, statsSpaceHolderPane, 0, 0, 1, 1, 1, 1, GridBagConstraints.BOTH, GridBagConstraints.CENTER);


        Comp.add(hudButtonPane, this, 0, 0, 1, 1, 1, 1,
                GridBagConstraints.BOTH, GridBagConstraints.CENTER);

        Comp.add(statsSpaceHolderPane, this, 1, 0, 1, 1, 1, 1,
                GridBagConstraints.BOTH, GridBagConstraints.WEST);

        Comp.add(hudStatePane, this, 2, 0, 1, 1, 1, 1,
                GridBagConstraints.BOTH, GridBagConstraints.EAST);

        statsSpaceHolderPane.setPreferredSize(Constants.HUD_STATS_SIZE);
        statsSpaceHolderPane.setMaximumSize(Constants.HUD_STATS_SIZE);
        statsSpaceHolderPane.setMinimumSize(Constants.HUD_STATS_SIZE);
        statsSpaceHolderPane.setSize(Constants.HUD_STATS_SIZE);

    }

    public TowerType getSelectedTowerType(){
        return selectedTowerType;
    }


    public void setSelectedTowerType(TowerType towerName) {

        //TODO IMPLEMENTATION -- Talk to stats pane to show the stats of the tower.
        selectedTowerType = towerName;
        //hudStatsPane.displayStatsForTower(towerName);

    }

    public HUDButtonPane getHUDButtonsPane() {
        return hudButtonPane;
    }
    public HUDStatsPane getHUDStatsPane() {
        return hudStatsPane;
    }

    //TODO = Implement
    @Override
    public void update(Observable o, String msg) {

    }

    public HUDStatePane getHUDStatePane() {
        return hudStatePane;
    }


    private class TowerChangeListener implements PropertyChangeListener {
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            if(evt.getPropertyName().equals(HUDButtonPane.TOWER_CHANGED)){
                HUDPane.this.setSelectedTowerType((TowerType)evt.getNewValue());
            }
        }
    }
}
