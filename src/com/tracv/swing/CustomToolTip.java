package com.tracv.swing;

import javax.swing.*;
import java.awt.*;

public class CustomToolTip extends JFrame {
    protected Pane content;

    public CustomToolTip(){

        setAlwaysOnTop(true);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setUndecorated(true);
        content = new Pane();
        content.setOpaque(false); //TODO FIX OTHER UIS SO PANE CAN BE TRANSPARENT
        setContentPane(content);
        setBackground(Color.WHITE);
        //setSize(new Dimension(100, 100));
    }
}
