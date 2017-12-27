package com.tracv.swing;

import com.tracv.util.Constants;

import javax.swing.*;

public class IconButton extends JButton {

    //TODO implement class.
    public IconButton(String s){
        super(s);
        this.setContentAreaFilled(false);
        this.setPreferredSize(Constants.ICON_SIZE);
    }
}
