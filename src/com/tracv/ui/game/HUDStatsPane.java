package com.tracv.ui.game;

import com.tracv.swing.Pane;
import com.tracv.types.TowerType;

import javax.swing.*;

/**
 *  Holds stats of selected item.
 *
 */
public class HUDStatsPane extends Pane {
    private HUDPane hudPane;

    public HUDStatsPane(HUDPane hudPane) {
        this.hudPane = hudPane;
    }

    /**
     * TODO - Implementation.
     * @param towerName
     */
    public void displayStatsForTower(TowerType towerName) {


        SwingUtilities.invokeLater(()-> this.validate());

    }
}
