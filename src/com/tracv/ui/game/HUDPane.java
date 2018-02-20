package com.tracv.ui.game;

import com.tracv.model.GameState;
import com.tracv.observerpattern.Observable;
import com.tracv.observerpattern.Observer;
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

    private GameState gs;
    private com.tracv.ui.game.HUDStatePane HUDStatePane;

    public HUDPane(GameState gs){
        this.setPreferredSize(Constants.HUD_DIMENSION);
        this.setBackground(Color.BLACK);
        //this.setOpaque(true);
        this.gs = gs;

        hudButtonPane = new HUDButtonPane(this, gs);
        hudButtonPane.addPropertyChangeListener(new TowerChangeListener());

        hudStatsPane = new HUDStatsPane(this, gs);
        hudStatePane = new HUDStatePane(this, gs);

        Comp.add(hudStatsPane, this, 0, 0, 1, 1, 1, 1,
                GridBagConstraints.NONE, GridBagConstraints.WEST, 0, 10, 0, 5);
        Comp.add(hudButtonPane, this, 1, 0, 1, 1, 1, 1,
                GridBagConstraints.NONE, GridBagConstraints.CENTER, 0, 5, 0, 5);
        Comp.add(hudStatePane, this, 2, 0, 1, 1, 1, 1,
                      GridBagConstraints.NONE, GridBagConstraints.EAST, 0, 5, 0, 10);

    }

    public TowerType getSelectedTowerType(){
        return selectedTowerType;
    }

    public void setSelectedTowerType(TowerType towerName) {
        //TODO IMPLEMENTATION -- Talk to stats pane to show the stats of the tower.
        selectedTowerType = towerName;
        hudStatsPane.displayStatsForTower(towerName);

    }

    public HUDButtonPane getHUDButtonsPane() {
        return hudButtonPane;
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
