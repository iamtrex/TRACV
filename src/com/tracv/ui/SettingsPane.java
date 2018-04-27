package com.tracv.ui;

import com.tracv.settings.Settings;
import com.tracv.settings.SettingsIO;
import com.tracv.swing.Button;
import com.tracv.swing.Pane;
import com.tracv.util.Comp;
import com.tracv.util.Logger;

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

    private Button back, showLogger;

    public SettingsPane(TDFrame tdf){
        this.tdf = tdf;

        sio = new SettingsIO(); //Inits settings reader/writer
        settings = sio.getSettings();

        back = new Button("Back");
        showLogger = new Button("Show Log Window");

        Comp.add(back, this, 0, 0, 1, 1, 1, 1, GridBagConstraints.BOTH, GridBagConstraints.CENTER);
        Comp.add(showLogger, this, 0, 1, 1, 1, 1, 1, GridBagConstraints.BOTH, GridBagConstraints.CENTER);

        back.addActionListener(this);
        showLogger.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == back){
            tdf.returnToLast();
        }else if(source == showLogger){
            Logger.getInstance().showLogWindow();
        }
    }
}
