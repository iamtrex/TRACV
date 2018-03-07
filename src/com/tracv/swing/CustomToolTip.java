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
        setContentPane(content);
        setSize(new Dimension(100, 100));
    }
}
