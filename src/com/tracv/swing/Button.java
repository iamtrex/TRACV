package com.tracv.swing;

import com.tracv.util.Constants;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

/*
    Customized version of JButton to keep with design.
 */
public class Button extends JButton {

    public Button() {
        super();
        setLook();
    }

    public Button(String s) {
        super(s);
        setLook();
    }

    private void setLook() {
        this.setContentAreaFilled(false);
        //this.setBorderPainted(false);
        this.setFont(Constants.DEFAULT_FONT);

        //TODO remove testing:
        //this.setBorder(new LineBorder(Color.BLACK, 1));

    }
}

