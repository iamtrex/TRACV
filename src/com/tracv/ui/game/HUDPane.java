package com.tracv.ui.game;

import com.tracv.swing.Pane;
import com.tracv.util.Comp;
import com.tracv.util.Constants;

import javax.swing.*;
import java.awt.*;

public class HUDPane extends JPanel {
    private HUDButtonPane hudButtonPane;
    private HUDStatsPane hudStatsPane;
    private HUDStatePane hudStatePane;

    private String selectedTower;


    public HUDPane(){
        this.setPreferredSize(Constants.HUD_DIMENSION);
        this.setBackground(Color.BLACK);
        //this.setOpaque(true);

        hudButtonPane = new HUDButtonPane(this);
        hudStatsPane = new HUDStatsPane(this);
        hudStatePane = new HUDStatePane(this);

        Comp.add(hudStatsPane, this, 0, 0, 1, 1, 1, 1,
                GridBagConstraints.NONE, GridBagConstraints.WEST);
        Comp.add(hudButtonPane, this, 1, 0, 1, 1, 1, 1,
                GridBagConstraints.NONE, GridBagConstraints.CENTER);
        Comp.add(hudStatePane, this, 2, 0, 1, 1, 1, 1,
                      GridBagConstraints.NONE, GridBagConstraints.EAST);

    }

    public String getSelectedTower(){
        return selectedTower;
    }

    public void setSetSelectedTower(String towerName) {
        //TODO IMPLEMENTATION -- Talk to stats pane to show the stats of the tower.
        selectedTower = towerName;

    }

}