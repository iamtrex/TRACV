package com.tracv.ui;

import com.tracv.swing.Button;
import com.tracv.util.Comp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainPane extends JPanel
                        implements ActionListener{

    private Button start;
    private Button menu;
    private Button quit;


    private TDFrame tdf;

    public MainPane(TDFrame tdf){
        this.tdf = tdf;

        start = new Button("Start Game");
        start.addActionListener(this);

        menu = new Button("Menu");
        menu.addActionListener(this);

        quit = new Button("Quit");
        quit.addActionListener(this);

        Comp.add(start, this, 0, 0, 1, 1, 1, 0,
                GridBagConstraints.BOTH, GridBagConstraints.CENTER);
        Comp.add(menu, this, 0, 1, 1, 1, 1, 0,
                GridBagConstraints.BOTH, GridBagConstraints.CENTER);
        Comp.add(quit, this, 0, 2, 1, 1, 1, 0,
                GridBagConstraints.BOTH, GridBagConstraints.CENTER);


    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if(source == start){
            tdf.switchToGamePanel();
        }else if(source == menu){
            tdf.toggleMenu(true);
        }else if(source == quit){
            System.exit(0);
        }
    }
}
