package com.tracv.ui.game;

import com.tracv.swing.HUDTowerButton;
import com.tracv.swing.Pane;

import javax.swing.*;

/**
 *
 * Holds buildable item buttons.
 */
public class HUDButtonPane extends Pane {
    private HUDPane hudPane;
    public HUDButtonPane(HUDPane hudPane) {
        this.hudPane = hudPane;
        this.add(new HUDTowerButton("XD NO IMAGES YET"));
    }
}
