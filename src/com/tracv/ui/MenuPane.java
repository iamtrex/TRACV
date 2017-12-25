package com.tracv.ui;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

/*
 * Represents a Menu Overlay. 
 */
public class MenuPane extends JPanel {


    public MenuPane(){
        this.setBackground(new Color(0, 0, 0, 0.5f));
        this.setOpaque(false);

        this.setBorder(new LineBorder(Color.RED, 3));


    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);

    }
}
