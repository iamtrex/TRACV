package com.tracv.swing;

import com.tracv.util.Constants;

import javax.swing.*;
import java.awt.GridBagConstraints;

public class Label extends JLabel {

    public static final int SMALL = 1;
    public static final int MEDIUM = 2;
    public static final int LARGE = 3;

    public void setSize(int size){
        switch(size){
            case LARGE:
                this.setFont(Constants.LARGE_LABEL_FONT);
                break;
            case MEDIUM:
                this.setFont(Constants.MEDIUM_LABEL_FONT);
                break;
            case SMALL:
                this.setFont(Constants.SMALL_LABEL_FONT);
                break;
            default:
                break;
        }
    }

    public Label(String name, int size){
        super(name);
        this.setHorizontalAlignment(SwingConstants.CENTER);
        this.setHorizontalTextPosition(SwingConstants.CENTER);
        setSize(size);
    }

}
