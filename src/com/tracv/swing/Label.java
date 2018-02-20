package com.tracv.swing;

import com.tracv.util.Constants;

import javax.swing.*;
import java.awt.GridBagConstraints;

public class Label extends JLabel {

    public static final int SMALL = 1;
    public static final int MEDIUM = 2;
    public static final int LARGE = 3;

    public static final int LEFT = 11;
    public static final int MID = 12;

    public static final int INVISIBLE = 21;

    public Label(String name, int size, int align, int style) {
        super(name);
        setup();
        setSize(size);

        setAlignment(align);
        if(style == INVISIBLE){
            this.setOpaque(false);
        }
    }

    public Label(String name, int size, int align) {
        super(name);
        setup();
        setSize(size);
        setAlignment(align);
    }

    public Label(String name, int size){
        super(name);
        setup();
        setSize(size);
    }

    private void setup() {
        this.setHorizontalAlignment(SwingConstants.CENTER);
        this.setHorizontalTextPosition(SwingConstants.CENTER);
        this.setOpaque(true);
    }

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

    public void setAlignment(int alignment) {
        switch(alignment){
            case LEFT:
                this.setHorizontalAlignment(SwingConstants.LEFT);
                break;
            case MID:
                this.setHorizontalAlignment(SwingConstants.CENTER);
                break;
            default:
                break;
        }
    }
}
