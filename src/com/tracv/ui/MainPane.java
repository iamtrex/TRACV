package com.tracv.ui;

import com.tracv.swing.Button;
import com.tracv.swing.Label;
import com.tracv.swing.Spacer;
import com.tracv.util.Comp;
import com.tracv.util.Constants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * JPanel representing the main landing page of the program.
 */
public class MainPane extends JPanel
                        implements ActionListener{


    private Label title;
    private Label version;
    private Button start;
    private Button menu;
    private Button quit;
    private Button settings;



    private TDFrame tdf;

    public MainPane(TDFrame tdf){
        this.tdf = tdf;


        title = new Label(Constants.GAME_NAME, Label.LARGE);
        version = new Label("Version - " + Constants.VERSION_NUMBER, Label.MEDIUM);

        start = new Button("Start Game");
        start.addActionListener(this);

        menu = new Button("Menu");
        menu.addActionListener(this);

        settings = new Button("Settings");
        settings.addActionListener(this);

        quit = new Button("Quit");
        quit.addActionListener(this);

        Comp.add(new Spacer(), this, 0, 0, 1, 1, 1, 0.5,
                GridBagConstraints.BOTH, GridBagConstraints.PAGE_START);

        Comp.add(title, this, 0, 1, 1, 1, 1, 0,
                GridBagConstraints.BOTH, GridBagConstraints.PAGE_START);
        Comp.add(version, this, 0, 2, 1, 1, 1, 0,
                GridBagConstraints.BOTH, GridBagConstraints.PAGE_START);

        Comp.add(new Spacer(), this, 0, 3, 1, 1, 1, 1,
                GridBagConstraints.BOTH, GridBagConstraints.PAGE_START);

        Comp.add(start, this, 0, 4, 1, 1, 1, 0,
                GridBagConstraints.BOTH, GridBagConstraints.CENTER);
        Comp.add(menu, this, 0, 5, 1, 1, 1, 0,
                GridBagConstraints.BOTH, GridBagConstraints.CENTER);
        Comp.add(settings, this, 0, 6, 1, 1, 1, 0,
                GridBagConstraints.BOTH, GridBagConstraints.CENTER);

        Comp.add(quit, this, 0, 7, 1, 1, 1, 0,
                GridBagConstraints.BOTH, GridBagConstraints.CENTER);

        Comp.add(new Spacer(), this, 0, 8, 1, 1, 1, 2,
                GridBagConstraints.BOTH, GridBagConstraints.PAGE_END);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if(source == start){
            //tdf.newGame();
            tdf.switchToLevelSelectPanel();
        }else if(source == menu){
            tdf.toggleMenu(true, Constants.OBSERVER_GAME_OVER);
        }else if(source == settings){
            tdf.switchToSettingsPanel();
        }else if(source == quit){
            System.exit(0);
        }
    }
}
