package com.tracv.ui;

import com.tracv.swing.Button;

import javax.swing.*;
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

        this.add(start);
        this.add(menu);
        this.add(quit);

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
