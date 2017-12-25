package com.tracv.ui;

import com.tracv.swing.Button;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainPane extends JPanel
                        implements ActionListener{

    private Button start;

    private TDFrame tdf;

    public MainPane(TDFrame tdf){
        this.tdf = tdf;

        start = new Button("Start Game");
        start.addActionListener(this);

        this.add(start);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if(source == start){
            tdf.switchToGamePanel();
        }
    }
}
