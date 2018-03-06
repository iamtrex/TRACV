package com.tracv.ui;

import com.tracv.settings.Settings;
import com.tracv.settings.SettingsIO;
import com.tracv.swing.Pane;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * Headquarters for adjusting settings in the program.
 */
public class SettingsPane extends Pane implements ActionListener {
    private SettingsIO sio;
    private Settings settings;

    private TDFrame tdf;

    public SettingsPane(TDFrame tdf){
        this.tdf = tdf;

        sio = new SettingsIO(); //Inits settings reader/writer
        settings = sio.getSettings();



    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
