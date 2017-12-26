package com.tracv.ui.game;

import com.tracv.swing.Pane;

import javax.swing.*;

/**
 * Holds details fo the game state ie. Gold/Time
 *
 */
public class HUDStatePane extends Pane {
    private HUDPane hudPane;

    public HUDStatePane(HUDPane hudPane) {
        this.hudPane = hudPane;
    }
}
