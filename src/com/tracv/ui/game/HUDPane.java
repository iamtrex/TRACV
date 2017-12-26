package com.tracv.ui.game;

import com.tracv.util.Constants;

import javax.swing.*;
import java.awt.*;

public class HUDPane extends JPanel{
    public HUDPane(){
        this.setPreferredSize(Constants.HUD_DIMENSION);
        this.setBackground(Color.BLACK);
        //this.setOpaque(true);
    }
}
