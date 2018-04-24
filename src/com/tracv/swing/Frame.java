package com.tracv.swing;

import com.tracv.util.Comp;
import com.tracv.util.Constants;

import javax.swing.*;

public class Frame extends JFrame {
    public Frame(){
        //Frame setup.
        this.setUndecorated(true);
        //this.setBackground(new Color(1.0f, 1.0f, 1.0f, 0.8f));
        this.setSize(Constants.FRAME_DEFAULT_SIZE);
        Comp.center(this);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
}
