package com.tracv.ui;

import com.tracv.util.Constants;

import javax.swing.*;

public class TDFrame extends JFrame {

    public TDFrame(){
        this.setSize(Constants.FRAME_DEFAULT_SIZE);
        
        this.setVisible(true);
    }

    public static void main(String args[]){
        new TDFrame(); //Starts the program by calling TDFrame's constructor
    }
}
