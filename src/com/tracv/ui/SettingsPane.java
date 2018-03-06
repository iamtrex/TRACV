package com.tracv.ui;

import com.tracv.settings.Settings;
import com.tracv.settings.SettingsIO;
import com.tracv.swing.Button;
import com.tracv.swing.Pane;
import com.tracv.util.Comp;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * Headquarters for adjusting settings in the program.
 */
public class SettingsPane extends Pane implements ActionListener {
    private SettingsIO sio;
    private Settings settings;

    private TDFrame tdf;

    private Button back;

    public SettingsPane(TDFrame tdf){
        this.tdf = tdf;

        sio = new SettingsIO(); //Inits settings reader/writer
        settings = sio.getSettings();

        back = new Button("Back");
        Comp.add(back, this, 0, 0, 1, 1, 1, 1, GridBagConstraints.BOTH, GridBagConstraints.CENTER);


    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == back){
            tdf.returnToLast();
        }
    }
}
